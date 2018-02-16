package br.com.darisson.agenda;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.darisson.agenda.dao.AlunoDAO;
import br.com.darisson.agenda.modelo.Aluno;


public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        getMapAsync(this);//preparar uma instancia do google maps para poder manipular e colocar algumas funcoes

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {//metodo para quando o mapa passado pelo mapAsync estiver pronto e vai passar o mapa no parametro como instancia

        LatLng posicaoDaEscola = pegaCoordenandaDoEndereco("Av Washington Soares 1321, Edson queiroz, Fortaleza");//vai guardar em posicaoDaEscola
        if (posicaoDaEscola !=null){
            CameraUpdate updade = CameraUpdateFactory.newLatLngZoom(posicaoDaEscola,17); //metodo para especificar latitude e longitude no mapa movendo a camera e apontando o lcoal
            googleMap.moveCamera(updade);
        }
        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for (Aluno aluno : alunoDAO.buscaAlunos()){//para cada aluno que estiver no nosso banco, vamos pegar a coordenada desse aluno
            LatLng coordenada = pegaCoordenandaDoEndereco(aluno.getEndereco());//com isso pegamos a coordenada do seu endereco
            if (coordenada!=null){//se encontramos o aluno, colocamos o pingo nem cima do endereco
                MarkerOptions marcador = new MarkerOptions();//criamos o marcador
                marcador.position(coordenada);
                marcador.title(aluno.getNome());//colocando o nome do aluno na marcacao
                marcador.snippet(String.valueOf(aluno.getNota()));//colocando a nota no subtexto
                googleMap.addMarker(marcador);//passando para o mapa o marcador

            }
        }
        alunoDAO.close();

        //new Localizador(getContext(), googleMap);//instanciando o localizador estamos rodando assim o contrutor do localizador onde configuramos a api e pedimos pra conectar

    }

    private LatLng pegaCoordenandaDoEndereco(String endereco){
        try {//tratar a exception de IO
            Geocoder geocoder = new Geocoder(getContext());//para fazer a conversao da string em lat e long
            List<Address> resultados = geocoder.getFromLocationName(endereco, 1);
            if (!resultados.isEmpty()) {//para ter certeza que encontrou um resultado de endereco, caso nao seja vazio, vamos extrair as coordenadas
                LatLng posicao = new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());//construindo as coordenadas
                return posicao;
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return null;//caso nao consiga encontrar nada
    }
}
