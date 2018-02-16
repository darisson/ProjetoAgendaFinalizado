package br.com.darisson.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.darisson.agenda.adapter.AlunosAdapter;
import br.com.darisson.agenda.converter.AlunoConverter;
import br.com.darisson.agenda.dao.AlunoDAO;
import br.com.darisson.agenda.modelo.Aluno;


public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos); //Aqui é a view. Representa a lista......."listaAlunos" é uma referencia para lista
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                    Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);//devolver o aluno da posicao desejada

                    Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                    intentVaiProFormulario.putExtra("aluno", aluno);//para carregar os dados do aluno no formulario indo para a outra tela "pendurando" os dados de "aluno"
                    startActivity(intentVaiProFormulario);
            }
        });

        Button novoAluno = findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });
        registerForContextMenu(listaAlunos);//tem que dizer pro android, qual o componente que vai ativar o menu de contexto, vai registrar
        //a lista de alunos como alguem que tem um menu de contexto


    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);//tudo isso busca do banco de dados
        List<Aluno> alunos = dao.buscaAlunos(); //Traz todos os alunos do banco de dados para a lista de alunos
        dao.close();

        AlunosAdapter adapter = new AlunosAdapter(this, alunos); //converte os alunos em view para jogar na lista
        listaAlunos.setAdapter(adapter); //pedindo para a lista utilizar o adapter
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//onde criamos o menu da nossa aplicacao, a barra la em cima
        getMenuInflater().inflate(R.menu.menu_lista_alunos,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//metodo para quando sempre clicarmos em um item de menu
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                new EnviaAlunosTask(this).execute();//instanciar a task
                break;
            case R.id.menu_baixar_provas:
                Intent vaiParaProvas = new Intent(this, ProvasActivity.class);
                startActivity(vaiParaProvas);
                break;
            case R.id.menu_mapa:
                Intent vaiParaMapa = new Intent(this, MapaActivity.class);
                startActivity(vaiParaMapa);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;//esta dizendo ao android que o ContextMenuInfo se refere a um adapterview
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);//vai mostrar o aluno que esta na posicao que foi clicada
        // codigo acima para pegar o aluno e usar nos menus
        // o final declara que essa variavel aluno é uma variavel costante podendo ser usada tbm na classe anonima

        MenuItem itemLigar = menu.add("Ligar");
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        !=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,new String[]{Manifest.permission.CALL_PHONE},123);//aparece a janela de permissao no app
                }
                else{
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }
                return false;
            }
        });


        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);

        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));//pega o endereco e transforma em coordenada
        itemMapa.setIntent(intentMapa);

        MenuItem itemSite = menu.add("Visitar Site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);//intent implicita para aproveitar algo que ja esta pronto no celular como o navegador

        String site = aluno.getSite();
        if (!site.startsWith("http://")){
            site = "http://" + site; //contatena para o aluno nao precisar do http
        }
        intentSite.setData(Uri.parse(site));//para converter o site para uma string, chama a Uri
        itemSite.setIntent(intentSite);


        MenuItem deletar = menu.add("Deletar");//passa uma referencia para o menu
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {//classe anonima
                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);
                dao.close();
                carregaLista();
                return false;
            }
        });//interessanto em ouvir o evento no intem de clique de menu

    }
}

