CREATE TABLE "tb_emprestimos"(
    "id" BIGINT NOT NULL AUTO_INCREMENT,
    "membro_id" BIGINT NOT NULL,
    "livro_id" BIGINT NOT NULL,
    "data_devolucao" DATE NOT NULL,
    "valor_multa_dia" DECIMAL(8, 2) NOT NULL
);
ALTER TABLE
    "tb_emprestimos" ADD PRIMARY KEY("id");


CREATE TABLE "tb_membros"(
    "id" BIGINT NOT NULL AUTO_INCREMENT,
    "nome" VARCHAR(255) NOT NULL,
    "sobrenome" VARCHAR(255) NOT NULL,
    "endereco" VARCHAR(255) NOT NULL,
    "contato" VARCHAR(255) NOT NULL,
    "multa_acumulada" DECIMAL(8, 2) NOT NULL DEFAULT '0'
);
ALTER TABLE
    "tb_membros" ADD PRIMARY KEY("id");


CREATE TABLE "tb_livros"(
    "id" BIGINT NOT NULL AUTO_INCREMENT,
    "titulo" VARCHAR(255) NOT NULL,
    "isbn" CHAR(50) NOT NULL,
    "autor_nome" VARCHAR(255) NOT NULL,
    "autor_sobrenome" VARCHAR(255) NOT NULL,
    "ano_publicacao" INTEGER NOT NULL,
    "editora_nome" VARCHAR(255) NOT NULL,
    "emprestado" BOOLEAN NOT NULL DEFAULT '0'
);
ALTER TABLE
    "tb_livros" ADD PRIMARY KEY("id");
ALTER TABLE
    "tb_emprestimos" ADD CONSTRAINT "tb_emprestimos_membro_id_foreign" FOREIGN KEY("membro_id") REFERENCES "tb_membros"("id");
ALTER TABLE
    "tb_emprestimos" ADD CONSTRAINT "tb_emprestimos_livro_id_foreign" FOREIGN KEY("livro_id") REFERENCES "tb_livros"("id");
