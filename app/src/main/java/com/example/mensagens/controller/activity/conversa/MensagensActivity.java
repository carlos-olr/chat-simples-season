package com.example.mensagens.controller.activity.conversa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mensagens.R;
import com.example.mensagens.controller.adapter.MensagensAdapter;
import com.example.mensagens.model.Conversa;
import com.example.mensagens.model.Mensagem;
import com.example.mensagens.model.Usuario;
import com.example.mensagens.repository.ConversaRepository;
import com.example.mensagens.repository.MensagemRepository;
import com.example.mensagens.service.interno.LoginService;
import com.example.mensagens.service.interno.MensagensService;
import com.example.mensagens.util.Constantes;
import com.example.mensagens.util.asynctask.AsyncTaskOnFinishListener;
import com.example.mensagens.util.asynctask.AsyncTaskParams;
import com.example.mensagens.util.asynctask.AsyncTaskResult;
import com.example.mensagens.util.web.HttpUtils;

import java.util.List;

public class MensagensActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected Conversa conversaAtual;

    protected LoginService loginService;
    protected MensagensService mensagensService;
    protected MensagemRepository mensagemRepository;
    protected ConversaRepository conversaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagens);

        mensagemRepository = new MensagemRepository(this);
        conversaRepository = new ConversaRepository(this);
        loginService = new LoginService(this);
        mensagensService = new MensagensService(this);

        conversaAtual = (Conversa) getIntent().getSerializableExtra("conversa");
        if (conversaAtual != null) {
            this.listarMensagens();
        }
        String idContato = getIntent().getStringExtra("idContato");
        if (idContato != null) {
            conversaAtual = conversaRepository.buscarPorIdContato(idContato);
            if (conversaAtual == null) {
                throw new RuntimeException("deveria ter uma conversa com esse contato id");
            }
            this.listarMensagens();
        }

        if (activityReceiver != null) {
            IntentFilter intentFilter = new IntentFilter("MENSAGEM_RECEBIDA");
            registerReceiver(activityReceiver, intentFilter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (activityReceiver != null) {
            IntentFilter intentFilter = new IntentFilter("MENSAGEM_RECEBIDA");
            registerReceiver(activityReceiver, intentFilter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(activityReceiver);
    }

    private BroadcastReceiver activityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Mensagem> mensagens = mensagemRepository.buscarMensagensPorConversa(conversaAtual);

            MensagensAdapter adapter = (MensagensAdapter) recyclerView.getAdapter();
            adapter.atualizar(mensagens);
            adapter.notifyDataSetChanged();
        }
    };

    private void listarMensagens() {
        List<Mensagem> mensagens = mensagemRepository.buscarMensagensPorConversa(conversaAtual);
        MensagensAdapter adapter = new MensagensAdapter(this, mensagens);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.mensagens_recycle_view_mensagens);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    public void enviarMensagem(View view) {
        EditText editTextMensagem = findViewById(R.id.mensagens_edit_input_mensagem);

        String conteudoMsg = editTextMensagem.getText().toString();

        Usuario usuaio = loginService.getUsuaio();

        Mensagem novaMensagem = new Mensagem();
        novaMensagem.conteudo = conteudoMsg;
        novaMensagem.autorId = usuaio.id;
        novaMensagem.autor = usuaio.login;
        novaMensagem.destinatarioId = conversaAtual.idContato;
        novaMensagem.destinatario = conversaAtual.loginContato;

        AsyncTaskParams params = new AsyncTaskParams(this);
        params.put("url", Constantes.HOST + "/enviarMensagem");
        params.put("json", novaMensagem.toJson());
        new HttpUtils.HttpPostHelper().setOnFinishListener(new MensagemEnviadaCallback())
                .execute(params);

        mensagensService.salvarMensagemEnviada(novaMensagem);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ConversasActivity.class));
    }

    private static class MensagemEnviadaCallback implements AsyncTaskOnFinishListener {

        @Override
        public void onFinish(AsyncTaskResult resultado) {
            MensagensActivity ma = resultado.getContext();
            if (resultado.getSucesso()) {
                String resposta = resultado.getParams().getParam("resposta");
                Toast.makeText(ma, resposta, Toast.LENGTH_SHORT).show();
            } else {
                String msgRetornada = resultado.getParams().getParam("resposta");
                new AlertDialog.Builder(ma).setMessage(msgRetornada).create().show();
            }
        }
    }
}
