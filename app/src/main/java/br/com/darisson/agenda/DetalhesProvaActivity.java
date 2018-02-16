package br.com.darisson.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.darisson.agenda.modelo.Prova;

public class DetalhesProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_prova);

        Intent intent = getIntent();//desempacotando a intent
        Prova prova = (Prova) intent.getSerializableExtra("prova");//dados da prova para popular a tela

        TextView materia = findViewById(R.id.detalhes_prova_materia);//recuperamos todos os topicos da tela monstada com a materia, data, topicos
        TextView data = findViewById(R.id.detalhes_prova_data);
        ListView listaTopicos = findViewById(R.id.detalhes_prova_topicos);

        materia.setText(prova.getMateria());//no textview materia, coloca la dentro o valor que é a materia da prova
        data.setText(prova.getData());//no textview da data, coloca o valor, a data da prova

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prova.getTopicos());
        listaTopicos.setAdapter(adapter);//cria um adapter para mostrar esses topicos

    }
}
