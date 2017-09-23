package br.com.alura.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.adapter.AlunoAdapter;
import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.http.HttpClient;
import br.com.alura.agenda.http.SalvaAlunosTask;
import br.com.alura.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;
    private AlunoDAO dao;
    private Aluno selecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        dao = new AlunoDAO(this);

        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Aluno aluno = (Aluno) parent.getItemAtPosition(position);
                Intent vaiParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                vaiParaFormulario.putExtra("aluno", aluno);
                startActivity(vaiParaFormulario);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    private void carregaLista() {
        List<Aluno> alunos = dao.lista();

        AlunoAdapter adapter = new AlunoAdapter(this, alunos);

        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo adapterMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        ListView view = (ListView) v; // Lista de alunos
        final Aluno aluno = (Aluno) view.getItemAtPosition(adapterMenuInfo.position);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                dao.deletar(aluno);
                carregaLista();
                Toast.makeText(ListaAlunosActivity.this, "Deletando aluno " + aluno.getNome(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        MenuItem visitarSite = menu.add("Visitar site do aluno");
        visitarSite.setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse(aluno.getSite())));
        MenuItem enviarSMS = menu.add("Enviar SMS");
        enviarSMS.setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + aluno.getTelefone())));
        MenuItem mostrarNoMapa = menu.add("Mostrar no mapa");
        mostrarNoMapa.setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + aluno.getEndereco())));
        MenuItem ligarParaAluno = menu.add("Ligar para o aluno");
        ligarParaAluno.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    selecionado = aluno;
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }
                return true;
            }
        });
        ligarParaAluno.setIntent(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + aluno.getTelefone())));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Intent intentLigar = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + selecionado.getTelefone()));
            startActivity(intentLigar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_notas:
                SalvaAlunosTask task = new SalvaAlunosTask(this);
                task.execute();
                break;
            case R.id.menu_provas:
                Intent intent = new Intent(this, ProvasActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
