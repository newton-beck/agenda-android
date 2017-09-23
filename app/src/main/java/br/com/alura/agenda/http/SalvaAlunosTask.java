package br.com.alura.agenda.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;

public class SalvaAlunosTask extends AsyncTask<String, Void, String> {

    private Context context;

    private AlunoDAO dao;

    private ProgressDialog progressDialog;

    public SalvaAlunosTask(Context context) {
        this.context = context;
        this.dao = new AlunoDAO(context);
    }

    @Override
    protected String doInBackground(String... params) {
        AlunoConverter converter = new AlunoConverter();
        String json = converter.toJson(dao.lista());
        HttpClient httpClient = new HttpClient();
        String result = httpClient.post(json);
        return result;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context, "Aguarde", "Enviando para o servidor", true, true);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}
