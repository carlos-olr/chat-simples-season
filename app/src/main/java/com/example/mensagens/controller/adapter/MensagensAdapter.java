package com.example.mensagens.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mensagens.R;
import com.example.mensagens.controller.holder.MensagemViewHolder;
import com.example.mensagens.model.Mensagem;
import com.example.mensagens.service.interno.LoginService;

import java.util.List;

import static android.view.LayoutInflater.from;

public class MensagensAdapter extends RecyclerView.Adapter<MensagemViewHolder> {

    protected Context context;
    protected List<Mensagem> mensagens;
    protected LoginService loginService;

    public MensagensAdapter(Context ctx, List<Mensagem> p) {
        this.mensagens = p;
        this.context = ctx;
        this.loginService = new LoginService(ctx);
    }

    public void atualizar(List<Mensagem> p) {
        this.mensagens.clear();
        this.mensagens.addAll(p);
    }

    @NonNull
    @Override
    public MensagemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = from(parent.getContext()).inflate(R.layout.holder_mensagem, parent, false);
        return new MensagemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MensagemViewHolder holder, int position) {
        Mensagem mensagem = this.mensagens.get(position);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mensagem.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        if (this.loginService.getUsuaio().login.equals(mensagem.autor)) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        }

        holder.mensagem.setText(mensagem.conteudo);
    }

    @Override
    public int getItemCount() {
        return this.mensagens.size();
    }
}
