package br.com.alura.agenda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.helper.PhotoHelper;
import br.com.alura.agenda.modelo.Aluno;

public class AlunoAdapter extends BaseAdapter {

    private Context context;

    private List<Aluno> alunos;

    public AlunoAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = convertView;
        if (itemView == null) {
            itemView = layoutInflater.inflate(R.layout.list_item_aluno, parent, false);
        }
        Aluno aluno = alunos.get(position);
        ImageView foto = (ImageView) itemView.findViewById(R.id.item_foto);
        PhotoHelper photoHelper = new PhotoHelper(foto);
        photoHelper.show(aluno.getCaminhoFoto());
        TextView nome = (TextView) itemView.findViewById(R.id.item_nome);
        nome.setText(aluno.getNome());
        TextView telefone = (TextView) itemView.findViewById(R.id.item_telefone);
        telefone.setText(aluno.getTelefone());

        TextView endereco = (TextView) itemView.findViewById(R.id.item_endereco);
        if (endereco != null) {
            endereco.setText(aluno.getEndereco());
        }

        TextView site = (TextView) itemView.findViewById(R.id.item_site);
        if (site != null) {
            site.setText(aluno.getSite());
        }

        return itemView;
    }
}
