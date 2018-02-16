package br.com.darisson.agenda;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.darisson.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);//pegou o layout que especificamos para essa tela

        FragmentManager fragmentManager = getSupportFragmentManager();//manipulador de fragment
        //temos que pegar aquele pedaco de tela que sobrou e substituir pela fragment
        FragmentTransaction tx = fragmentManager.beginTransaction();//abrindo uma transacao vazia
        tx.replace(R.id.frame_principal, new ListaProvasFragment());//substituir nosso frame vazio pela fragment
        if (estaNoModoPaisagem()) {
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }
        tx.commit();//efetuando a transacao
    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        if (!estaNoModoPaisagem()) {//verifica se esta em modo paisagem ou não
            FragmentTransaction tx = manager.beginTransaction();

            DetalhesProvaFragment detalhesFragment = new DetalhesProvaFragment();//popularizando o fragment
            Bundle parametros = new Bundle();//local para trafegar dados
            parametros.putSerializable("prova",prova);//populamos o bundle
            detalhesFragment.setArguments(parametros);//passando os parametros como argumento
            tx.replace(R.id.frame_principal,detalhesFragment);

            tx.addToBackStack(null);//adicionou nossa transacao na pilha, como se fosse uma outra activity. o null é para marcar essa transacao na pilha

            tx.commit();//executar transacao
        }else{//se esta no modo paisagem ele popula
            DetalhesProvaFragment detalhesFragment = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            detalhesFragment.populaCamposCom(prova);
        }
    }
}