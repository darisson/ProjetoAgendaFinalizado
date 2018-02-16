package br.com.darisson.agenda.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.darisson.agenda.R;
import br.com.darisson.agenda.dao.AlunoDAO;

public class SMSReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");//pegou o pdu dentro da intent e jogou em um array de object
        byte[] pdu = (byte[]) pdus[0];//converteu o pdu para um array de bytes
        String formato  = (String) intent.getSerializableExtra("format");//definir o formato do sms recebido

        SmsMessage sms = SmsMessage.createFromPdu(pdu,formato);//objeto do tipo SmsMessage e ele consegue criar esse objeto a partir do from pdu

        String telefone = sms.getDisplayOriginatingAddress();//com o objeto pronto, foi possivel pegar o telefone a partir desse metodo

        AlunoDAO dao = new AlunoDAO(context);//verificar se o telefone é ou nao de um aluno
        if (dao.ehAluno(telefone)){
            Toast.makeText(context, "Chegou um SMS de Aluno!", Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();

        }
        dao.close();

    }
}
