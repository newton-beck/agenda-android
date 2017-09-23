package br.com.alura.agenda.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.alura.agenda.FormularioActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioHelper {

    private String caminhoFoto;

    private EditText campoNome;

    private EditText campoEndereco;

    private EditText campoTelefone;

    private EditText campoSite;

    private RatingBar campoNota;

    private Aluno aluno;

    private PhotoHelper photoHelper;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        ImageView campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
        photoHelper = new PhotoHelper(campoFoto);
        aluno = new Aluno();
    }

    public Aluno getAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getRating()));
        aluno.setCaminhoFoto(caminhoFoto);
        return aluno;
    }

    public void preencheCom(Aluno aluno) {
        this.aluno = aluno;
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setRating(aluno.getNota().floatValue());
        mostraFoto(aluno.getCaminhoFoto());
    }

    public void mostraFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
        photoHelper.show(caminhoFoto);
    }
}
