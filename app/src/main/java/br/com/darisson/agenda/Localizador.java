package br.com.darisson.agenda;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public class Localizador {
// implements GoogleApiClient.ConnectionCallbacks, LocationListener {
//
//    private final GoogleApiClient client;
//    private final GoogleMap mapa;
//
//    public Localizador(Context context, GoogleMap mapa){//classe para utilizar algum servico do android
//        client = new GoogleApiClient.Builder(context)//para se conectar com qalquer servico do android, usa o apiclient
//                .addApi(LocationServices.API)
//                .addConnectionCallbacks(this)//chamar quando fizer uma conexao
//                .build();
//
//        client.connect();
//        this.mapa = mapa;//atributo para guardar esse "mapa
//
//    }
//
////    @Override
////    public void onConnected(@Nullable Bundle bundle) {//quando conseguir conectar com a API
////        LocationRequest request = new LocationRequest();
////        request.setSmallestDisplacement(50);//receber posicoes apenas quando se deslocar passando o espaco minimo para atualizar a locomocao
////        request.setInterval(1000);//Intervalo entre 2 atualizacoes de gps
////        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//atraves da precisao, informar a localizacao naquele momento utilizando que no caso sera o gps
////
////        //LocationServices.FusedLocationApi.requestLocationUpdates(client,request,this);//mandar esses requests para o location servicer
////    }
//
//    @Override
//    public void onConnectionSuspended(int i) {//quando nao conseguir conectar
//
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {//implementar a localizacao que foi mudada de acordo com as saidas que pedi
//        //entao quando a posicao for alterada, mudamos a posicao no nosso mapa
//        LatLng coordenada = new LatLng(location.getLatitude(),location.getLongitude());
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(coordenada);
//        mapa.moveCamera(cameraUpdate);
//
//
//    }
}
