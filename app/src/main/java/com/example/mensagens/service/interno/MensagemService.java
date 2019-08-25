package com.example.mensagens.service.interno;

import android.content.Context;

import com.example.mensagens.model.Conversa;
import com.example.mensagens.model.Mensagem;
import com.example.mensagens.repository.ConversaRepository;
import com.example.mensagens.repository.MensagemRepository;

public class MensagemService {

    private Context context;
    private ConversaRepository conversaRepository;
    private MensagemRepository mensagemRepository;

    public MensagemService(Context ctx) {
        this.context = ctx;
        this.conversaRepository = new ConversaRepository(ctx);
        this.mensagemRepository = new MensagemRepository(ctx);
    }

    public Long salvarMensagemRecebida(Mensagem mensagem) {
        // 1 - verificar se já existe uma Convera para a mensagem, e caso não exista, criar uma
        Conversa conversa = this.conversaRepository.busarPorIdContato(mensagem.autorId);
        if (conversa == null) {
            conversa = new Conversa();
            conversa.idContato = mensagem.autorId;
            conversa.nomeContato = mensagem.autor;
            conversa.tabelaConversa = "TABELA_" + mensagem.autorId;
            conversa = this.conversaRepository.criarConversa(conversa);
        }

        // 2 - salvar a mensagem na tabela da conversa
        return this.mensagemRepository.salvarMensagem(mensagem, conversa.tabelaConversa);
    }

}
