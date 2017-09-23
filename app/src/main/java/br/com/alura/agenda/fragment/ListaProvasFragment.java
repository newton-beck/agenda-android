package br.com.alura.agenda.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Prova;

public class ListaProvasFragment extends Fragment {

    private static final List<Prova> PROVAS = Arrays.asList(
            new Prova("Matemática", "13/08/2017", "Progressão aritmética", "Progressão geométrica"),
            new Prova("Portugês", "15/08/2017", "Verbos", "Substantivos")
    );

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lista_provas_fragment, container, false);

        ListView listaProvas = (ListView) view.findViewById(R.id.lista_provas);
        ArrayAdapter<Prova> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, PROVAS);
        listaProvas.setAdapter(adapter);

        listaProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);

                SelecionaProvaDelegate delegate = (SelecionaProvaDelegate) getActivity();
                delegate.selecionaProva(prova);
            }
        });

        return view;
    }

    public interface SelecionaProvaDelegate {
        void selecionaProva(Prova prova);
    }

}
