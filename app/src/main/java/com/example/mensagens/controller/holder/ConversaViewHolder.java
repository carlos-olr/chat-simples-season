package com.example.mensagens.controller.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mensagens.R;

public class ConversaViewHolder extends RecyclerView.ViewHolder {

    public TextView login;
    public TextView dataUltimaMensagem;

    public ConversaViewHolder(@NonNull View itemView) {
        super(itemView);
        login = itemView.findViewById(R.id.holder_conversa_txt_login);
        dataUltimaMensagem = itemView.findViewById(R.id.holder_conversa_txt_ultimaMensagem);
    }
}
