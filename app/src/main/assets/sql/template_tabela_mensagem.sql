create table __NOME_TABELA__ (
    ID BIGINT primary key,
    ID_EXTERNO text,
    CONTEUDO clob not null,
    AUTOR text not null,
    AUTOR_ID text not null,
    DESTINATARIO text not null,
    DESTINATARIO_ID text not null,
    DATA_RECEBIDA BIGINT
);