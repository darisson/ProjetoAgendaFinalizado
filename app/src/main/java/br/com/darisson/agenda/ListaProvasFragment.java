package br.com.darisson.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.darisson.agenda.modelo.Prova;

public class ListaProvasFragment extends Fragment{

    @Nullable
    @Override//metodo chamado quando o fragment entrar na tela
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {//monta a view que representa o fragment
        View view = inflater.inflate(R.layout.fragment_lista_provas,container,false);//inflando aquele layout e devolveu a tela montada

        List<String> topicosPort = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");//populando a view
        Prova provaPortugues = new Prova("Portugues", "02/01/2018", topicosPort);

        List<String> topicosMat = Arrays.asList("Equacoes de segundo grau", "Trigonometria");
        Prova provaMatematica = new Prova("Matematica", "04/01/2018", topicosMat);

        List<Prova> provas = Arrays.asList(provaPortugues,provaMatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,provas);

        ListView lista = view.findViewById(R.id.provas_lista);//estamos procurando a lista na view monstada para esse fragment
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {//metodo para falar qual o item selecionado
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);//para saber em qual prova foi clicado e recebemos a posicao como parametro
                Toast.makeText(getContext(), "Clicou na prova de " + prova, Toast.LENGTH_SHORT).show();

                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(prova);
            }
        });

        return view;//retornando a view que acabou de popular
    }


}
