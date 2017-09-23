package br.com.alura.agenda;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import br.com.alura.agenda.fragment.DetalhesProvaFragment;
import br.com.alura.agenda.fragment.ListaProvasFragment;
import br.com.alura.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity implements ListaProvasFragment.SelecionaProvaDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();

        tx.replace(R.id.frame_principal, new ListaProvasFragment());

        if (isModoPaisagem()) {
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        tx.commit();
    }

    private boolean isModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    @Override
    public void selecionaProva(Prova prova) {
        FragmentManager manager = getSupportFragmentManager();
        if (!isModoPaisagem()) {
            FragmentTransaction tx = manager.beginTransaction();
            DetalhesProvaFragment fragment = new DetalhesProvaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("prova", prova);
            fragment.setArguments(bundle);
            tx.replace(R.id.frame_principal, fragment);
            tx.addToBackStack(null);
            tx.commit();
        } else {
            DetalhesProvaFragment fragment = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            fragment.populaCampos(prova);
        }

    }
}
