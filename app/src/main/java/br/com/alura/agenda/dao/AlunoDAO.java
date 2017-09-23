package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

    private static final String DB_NAME = "agenda";

    private static final int DB_VERSION = 2;

    public AlunoDAO(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("Criando o banco de dados...");
        db.execSQL("CREATE TABLE Alunos(" +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL("ALTER TABLE Alunos ADD COLUMN caminhoFoto TEXT;");
        }
    }

    public void insere(Aluno aluno) {
        ContentValues content = new ContentValues();
        content.put("nome", aluno.getNome());
        content.put("endereco", aluno.getEndereco());
        content.put("telefone", aluno.getTelefone());
        content.put("site", aluno.getSite());
        content.put("nota", aluno.getNota());
        content.put("caminhoFoto", aluno.getCaminhoFoto());

        SQLiteDatabase db = getWritableDatabase();
        db.insert("Alunos", null, content);
    }

    public boolean isAluno(String telefone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Alunos WHERE telefone = ?", new String[] { telefone });
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public List<Aluno> lista() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Alunos", null);
        List<Aluno> alunos = new ArrayList<>();
        while (cursor.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));
            alunos.add(aluno);
        }
        return alunos;
    }

    public void deletar(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Alunos", "id = ?", new String[] {aluno.getId().toString()});
    }

    public void atualiza(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("nome", aluno.getNome());
        content.put("endereco", aluno.getEndereco());
        content.put("telefone", aluno.getTelefone());
        content.put("site", aluno.getSite());
        content.put("nota", aluno.getNota());
        content.put("caminhoFoto", aluno.getCaminhoFoto());
        db.update("Alunos", content, "id = ?", new String[] { aluno.getId().toString() });
    }
}
