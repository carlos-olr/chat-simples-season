package com.example.mensagens.controller.activity.conversa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mensagens.R;
import com.example.mensagens.controller.adapter.ConversasAdapter;
import com.example.mensagens.model.Conversa;
import com.example.mensagens.model.Usuario;
import com.example.mensagens.repository.ConversaRepository;
import com.example.mensagens.service.interno.MensagensService;
import com.example.mensagens.util.Constantes;
import com.example.mensagens.util.asynctask.AsyncTaskOnFinishListener;
import com.example.mensagens.util.asynctask.AsyncTaskParams;
import com.example.mensagens.util.asynctask.AsyncTaskResult;
import com.example.mensagens.util.json.JsonModel;
import com.example.mensagens.util.web.HttpUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ConversasActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected MensagensService mensagensService;
    protected ConversaRepository conversaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);

        mensagensService = new MensagensService(this);
        conversaRepository = new ConversaRepository(this);

        this.listarConversasExistentes();
        this.configurarBotaoNovaConversa();
    }

    private void listarConversasExistentes() {
        ConversasAdapter adapter = new ConversasAdapter(this, conversaRepository.buscarTodas());

        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.conversas_recycle_view_conversas);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    private void configurarBotaoNovaConversa() {
        FloatingActionButton fab = findViewById(R.id.conversas_fab_nova_conversa);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View conteudoAlert = getLayoutInflater().inflate(R.layout.alert_nova_conversa, null);
                final EditText editLoginNovaConversa = conteudoAlert.
                        findViewById(R.id.alert_nova_conversa_edit_login_alvo);

                AlertDialog alertNovaConversa = new AlertDialog.Builder(ConversasActivity.this)
                        .setView(conteudoAlert)
                        .setPositiveButton("Conversar!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String login = editLoginNovaConversa.getText().toString();
                                tratarOnClickAlertNovaConversa(login);
                            }
                        }).create();
                alertNovaConversa.show();
            }
        });
    }

    private void tratarOnClickAlertNovaConversa(String login) {
        AsyncTaskParams params = new AsyncTaskParams(this);
        params.put("url", Constantes.HOST + "/buscarUsuarioPorLogin/" + login);

        new HttpUtils.HttpGetHelper().setOnFinishListener(new LoginValidoCallback()).execute(params);
    }

    private static class LoginValidoCallback implements AsyncTaskOnFinishListener {

        @Override
        public void onFinish(AsyncTaskResult resultado) {
            ConversasActivity ca = resultado.getContext();

            if (resultado.getSucesso()) {
                String usurioEncontradoJSON = resultado.getParams().getParam("resposta");
                Usuario usuario = JsonModel.fromJson(usurioEncontradoJSON, Usuario.class);
                Conversa conversa = ca.mensagensService.criarConversa(usuario.id, usuario.login);

                Intent intent = new Intent(ca, MensagensActivity.class);
                intent.putExtra("conversa", conversa);
                ca.startActivity(intent);
            } else {
                String msgRetornada = resultado.getParams().getParam("resposta");
                new AlertDialog.Builder(ca).setMessage(msgRetornada).create().show();
            }
        }
    }

}
