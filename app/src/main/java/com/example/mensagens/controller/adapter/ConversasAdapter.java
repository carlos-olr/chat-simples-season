package com.example.mensagens.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mensagens.R;
import com.example.mensagens.controller.activity.conversa.MensagensActivity;
import com.example.mensagens.controller.holder.ConversaViewHolder;
import com.example.mensagens.model.Conversa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.view.LayoutInflater.from;

public class ConversasAdapter extends RecyclerView.Adapter<ConversaViewHolder> {

    protected Context context;
    protected List<Conversa> conversas;

    public ConversasAdapter(Context ctx, List<Conversa> p) {
        this.conversas = p;
        this.context = ctx;
    }

    public void atualizar(List<Conversa> p) {
        this.conversas.clear();
        this.conversas.addAll(p);
    }

    @NonNull
    @Override
    public ConversaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = from(parent.getContext()).inflate(R.layout.holder_conversa, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View txtLogin = v.findViewById(R.id.holder_conversa_txt_login);
                Integer posicao = (Integer) txtLogin.getTag();

                Conversa conversa = conversas.get(posicao);

                Intent intent = new Intent(context, MensagensActivity.class);
                intent.putExtra("conversa", conversa);
                context.startActivity(intent);
            }
        });

        return new ConversaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversaViewHolder holder, int position) {
        Conversa conversa = this.conversas.get(position);

        holder.login.setText(conversa.loginContato);
        holder.login.setTag(position);
        if (conversa.dataUltimaMensagem != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            holder.dataUltimaMensagem.setText(sdf.format(new Date(conversa.dataUltimaMensagem)));
        } else {
            holder.dataUltimaMensagem.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return this.conversas.size();
    }
}
