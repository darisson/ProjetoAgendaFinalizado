package br.com.darisson.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.darisson.agenda.dao.AlunoDAO;
import br.com.darisson.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper; //criou o atributo helper
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this); //instanciando a classe

        final Intent intent = getIntent();//recupera essa intent através desse metodo
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");//passa a etiqueta do item que quer recuperar no caso "aluno"

        if (aluno!=null){//caso estiver alterando um aluno faz isso, caso não, apenas ignora
            helper.preencheFormulario(aluno);
        }
        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//o action é para capturar uma imagem...
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + "foto.jpg";//aqui é o caminho, onde pega a pasta da nossa aplicacao, faz-se o system para poder criar fotos diferente e nao subescrever
                File arquivoFoto = new File(caminhoFoto);//cria um arquivo do tipo objeto para esse caminho
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(FormularioActivity.this,"br.com.darisson.agenda.fileprovider",arquivoFoto));//a partir do objeto, gera uma Uri que ai o app da camera vai saber criar esse arquivo e salvar no caminho pedido
                startActivityForResult(intentCamera, CODIGO_CAMERA);//identifica o resultado da acao
            }
        });
    }

    //metodo para quando a activity acima terminar de executar, metodo para quando ela devolver um resultado para a gente
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == Activity.RESULT_OK){
        if (requestCode == CODIGO_CAMERA){
            helper.carregaImagem(caminhoFoto);
        }
    }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //cria o menu e popula esse menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok: //quando clicar no menu OK e precisar de um objeto do tipo aluno, a gente pede no helper o metodo "get aluno"

                Aluno aluno = helper.getAluno();//pega o aluno no formulario e inseria esse aluno

                AlunoDAO dao = new AlunoDAO(this);
                if(aluno.getId() != null){//se for um aluno novo, ele náo tera o id e nao esatara armazenado la no atributo this.aluno = aluno;
                    dao.altera(aluno);
                }
                else{
                    dao.insere(aluno);
                }
                dao.close();
                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo", Toast.LENGTH_SHORT).show();

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
