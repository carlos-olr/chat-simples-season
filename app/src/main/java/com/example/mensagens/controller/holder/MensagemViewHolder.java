package com.example.mensagens.controller.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mensagens.R;

public class MensagemViewHolder extends RecyclerView.ViewHolder {

    public TextView mensagem;

    public MensagemViewHolder(@NonNull View itemView) {
        super(itemView);
        mensagem = itemView.findViewById(R.id.holder_mensagem_txt_mensagem);
    }
}
