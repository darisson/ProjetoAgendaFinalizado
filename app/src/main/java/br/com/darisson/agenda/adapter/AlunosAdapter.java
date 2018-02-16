package br.com.darisson.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.darisson.agenda.ListaAlunosActivity;
import br.com.darisson.agenda.R;
import br.com.darisson.agenda.modelo.Aluno;

public class AlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;//pega a lista de alunos e guarda nesse atributo alunos
    }

    @Override
    public int getCount() {
        return alunos.size();//trouxe da lista de alunos a quantidade necessaria de itens para implementar no adapter
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//nesse metodo a lista pede cada item que tem la dentro do adapter
        Aluno aluno = alunos.get(position);//pegando o aluno da lista

        View view = convertView;

        LayoutInflater inflater = LayoutInflater.from(context);//pega o xml e transforma em uma view

        if (view == null){//se nao tem nenhuma view, deixando ela nula, precisa-se instanciar ela do zero inflando ela
            view = inflater.inflate(R.layout.list_item, null);//vamos inflar o xml com nome de list_item
        }

        TextView campoNome = view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        TextView campoTelefone = view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());

        TextView campoEndereco = view.findViewById(R.id.item_endereco);
        if (campoEndereco != null){//se for diferente de null, é pq existe o text pois esta no modo paisagem
            campoEndereco.setText(aluno.getEndereco());
        }
        TextView campoSite = view.findViewById(R.id.item_site);
        if (campoSite != null){
            campoSite.setText(aluno.getSite());
        }
        ImageView campoFoto = view.findViewById(R.id.item_foto);

        String caminhoFoto = aluno.getCaminhoFoto();

        if (caminhoFoto!=null) {//codigo para colocar a foto no espaco de foto
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 100, 100, true);//reduziu o bitmap pq o imageview nao suporta uma resolucao muito alta
            campoFoto.setImageBitmap(bitmapReduzido);//coloca o conteudo na imagview
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);//comando pra preencher tanto a altara como largura da imageview

        }
        return view;//coloca tudo dentro de textview
    }
}
