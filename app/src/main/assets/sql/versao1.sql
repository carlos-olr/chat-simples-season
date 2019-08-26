create table CONVERSA (
    ID BIGINT primary key,
    TABELA_CONVERSA text not null,
    LOGIN_CONTATO text not null,
    ID_CONTATO text not null,
    DATA_ULTIMA_MENSAGEM BIGINT
);