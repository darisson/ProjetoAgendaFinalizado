package br.com.darisson.agenda.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.darisson.agenda.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper{


    public AlunoDAO(Context context) {
        super(context,"Agenda" , null, 2);
    }//nossa versao do banco de dados agora é a versao 2
    @Override
    public void onCreate(SQLiteDatabase db) { //cria o banco de dados
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, telefone TEXT, " +
                "site TEXT, " +
                "nota REAL, " +
                "caminhoFoto TEXT);";//nova coluna na tabela
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = ""; //como as variaveis nao podem ter o mesmo nome, declaramos como uma string com uma variavel vazia
        switch (oldVersion) {
            case 1:
                sql = "ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT";//comando para inserir uma coluna a mais
                db.execSQL(sql);
                //onCreate(db);//como a tablea ja ta criada, nao precisa mais do onCreate(db)
        }
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();//pedindo ao SQLiteOpenhelper um BANCO para pode escrever nele

        ContentValues dados = pegaDadosDoAluno(aluno);//criou o metodos pegaDados onde coloca todos os dados do aluno gerando um contentvalues

        db.insert("Alunos", null, dados);

    }
    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota",aluno.getNota());
        dados.put("caminhoFoto",aluno.getCaminhoFoto());
        return dados;
    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null); //o raw da o resultado da busca e o cursor é um ponteiro apontando para os resultados da busca

        List<Aluno> alunos = new ArrayList<Aluno>();//contrucao da lista
        while (c.moveToNext()){//move para a proxima linha onde cada linha do resultado representa um aluno enquanto houver linhas
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("nome")));//nome do aluno = o nome de dentro do cursor na coluna nome
            aluno.setEndereco(c.getString(c.getColumnIndex("endereco")));
            aluno.setTelefone(c.getString(c.getColumnIndex("telefone")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setNota(c.getDouble(c.getColumnIndex("nota")));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno); //colocando esse aluno dentro da lista de alunos
        }
        c.close(); //avisar que terminou de usar o cursor
        return alunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {aluno.getId().toString()};//array do ID do aluno para entrar como parametro no comando abaixo
        db.delete("Alunos", "Id = ?",params); //a ? é pra marcar onde vao entrar os parametros
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();//para poder alterar o banco de dados

        ContentValues dados = pegaDadosDoAluno(aluno);

        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);//altera um aluno com um id especifico cujo o id vai ser o do aluno ai em cima do array de strings
    }

    public boolean ehAluno(String telefone){
        SQLiteDatabase db = getReadableDatabase();//fazendo a busca para ver se o aluno existe ou nao lendo o banco de dados
        Cursor c = db.rawQuery("SELECT * FROM Alunos WHERE telefone =?", new String[]{telefone});//metodo quando se esta interessado em um resultado para ver se o telefone passado é igual ao telefone do aluno
        int resultados = c.getCount();//se tiver pelo menos 1 resultado
        c.close();
        return resultados > 0;//se for maior que zero, é aluno.
    }
}
