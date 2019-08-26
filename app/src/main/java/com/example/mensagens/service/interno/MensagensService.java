package com.example.mensagens.service.interno;

import android.content.Context;

import com.example.mensagens.model.Conversa;
import com.example.mensagens.model.Mensagem;
import com.example.mensagens.repository.ConversaRepository;
import com.example.mensagens.repository.MensagemRepository;

import java.util.Calendar;

public class MensagensService {

    private Context context;
    private ConversaRepository conversaRepository;
    private MensagemRepository mensagemRepository;

    public MensagensService(Context ctx) {
        this.context = ctx;
        this.conversaRepository = new ConversaRepository(ctx);
        this.mensagemRepository = new MensagemRepository(ctx);
    }

    public Long salvarMensagemRecebida(Mensagem mensagem) {
        mensagem.dataRecebida = Calendar.getInstance().getTimeInMillis();

        // 1 - verificar se já existe uma Convera para a mensagem, e caso não exista, criar uma
        Conversa conversa = this.conversaRepository.buscarPorIdContato(mensagem.autorId);
        if (conversa == null) {
            conversa = criarConversa(mensagem.autorId, mensagem.autor);
        }

        // 2 - salvar a mensagem na tabela da conversa
        Long idMensagem = this.mensagemRepository.salvarMensagem(mensagem, conversa.tabelaConversa);

        // 3 - atualizar a data da últimaMensagem
        conversa.dataUltimaMensagem = Calendar.getInstance().getTimeInMillis();
        this.conversaRepository.salvar(conversa);

        return idMensagem;
    }

    public void salvarMensagemEnviada(Mensagem mensagem) {
        Conversa conversa = this.conversaRepository.buscarPorIdContato(mensagem.destinatarioId);
        this.mensagemRepository.salvarMensagem(mensagem, conversa.tabelaConversa);

        conversa.dataUltimaMensagem = Calendar.getInstance().getTimeInMillis();
        this.conversaRepository.salvar(conversa);
    }

    public Conversa criarConversa(String alvoConversaId, String alvoConversaLogin) {
        Conversa conversa = new Conversa();
        conversa.idContato = alvoConversaId;
        conversa.loginContato = alvoConversaLogin;
        conversa.tabelaConversa = "TABELA_" +alvoConversaId;
        return this.conversaRepository.salvar(conversa);
    }

}
