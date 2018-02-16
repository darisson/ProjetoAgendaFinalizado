package br.com.darisson.agenda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.darisson.agenda.modelo.Aluno;


public class FormularioHelper { //pega todos os dados do formulario e joga para o banco, resposnavel tbm por todos findview

    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoNota;
    private final ImageView campoFoto;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){ //construtor....tem que passar a referencia da activity pois o metodo findview nao pertence a essa classe
        campoNome = activity.findViewById(R.id.formulario_nome);
        campoEndereco = activity.findViewById(R.id.formulario_endereco);
        campoTelefone = activity.findViewById(R.id.formulario_telefone);
        campoSite = activity.findViewById(R.id.formulario_site);
        campoNota = activity.findViewById(R.id.formulario_nota);
        campoFoto = activity.findViewById(R.id.formulario_foto);
        aluno = new Aluno();//esse objeto "Aluno" é apenas um objeto para guardar os dados do aluno

    }

    public Aluno getAluno() {
        aluno.setNome(campoNome.getText().toString()); //vai la em campo nome, pega o texto dele e converte pra string do objeto aluno
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));//vai converter um inteiro para dentro do double e jogar no campo nota
        aluno.setCaminhoFoto((String) campoFoto.getTag());
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());
        this.aluno = aluno; //vai guardar esse aluno inteiro nesse atributo

    }

    public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);//reduziu o bitmap pq o imageview nao suporta uma resolucao muito alta
            campoFoto.setImageBitmap(bitmapReduzido);//coloca o conteudo na imagview
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);//comando pra preencher tanto a altara como largura da imageview
            campoFoto.setTag(caminhoFoto);//vc pode associar qualquer objeto com qualquer view do android
        }
    }
}
