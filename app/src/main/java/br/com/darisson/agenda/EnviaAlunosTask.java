package br.com.darisson.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.darisson.agenda.converter.AlunoConverter;
import br.com.darisson.agenda.dao.AlunoDAO;
import br.com.darisson.agenda.modelo.Aluno;

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {//classe para uma thread secundaria. O primeiro object vai alterar os
    //parametros que recebemos no metodo inBackground vindos do "execute()" da listaAlunos

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {//construtor
        this.context = context;
    }

    @Override
    protected void onPreExecute() {//metodo para executar antes do background. metodo a ser executado na thread principal
        dialog = ProgressDialog.show(context, "Aguarde", "Enviando alunos...", true, true);
    }

    @Override
    protected String doInBackground(Void... params) {//metodo para realizarmos aquilo que queremos em uma thread secundaria, uma tarefa em back
        AlunoDAO dao = new AlunoDAO(context);//buscar os alunos
        List<Aluno> alunos  = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();//conversão para json
        String json = conversor.converteParaJASON(alunos);

        WebClient client = new WebClient();//requisicao para o servidor
        String resposta = client.post(json);//o post é uma requisicao para o servidor, ele envia dados para la //pega a resposta do servidor e joga para o toast

        return resposta;//o android pega essa resposta, tira da thread secundaria e executa na thread principal jogando no metodo seguinte
    }

    @Override
    protected void onPostExecute(String resposta) {//metodo que recebe como parametro é um objeto, que é a "resposta" do metodo acima
        //metodo que é executado em uma thread primaria para poder executar o toast
        dialog.dismiss();
        Toast.makeText(context, resposta , Toast.LENGTH_LONG).show();
    }
}
