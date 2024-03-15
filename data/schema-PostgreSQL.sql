--
-- Para SGBD PostgreSQL
-- Database: biblioteca
-- Owner:  biblioadmin | Create DB
-- 

-- Name: tb_emprestimos; Type: TABLE; Schema: public
CREATE TABLE "biblioteca.tb_emprestimos"(
    "id" BIGINT NOT NULL AUTO_INCREMENT,
    "membro_id" BIGINT NOT NULL,
    "livro_id" BIGINT NOT NULL,
    "data_devolucao" DATE NOT NULL,
    "valor_multa_dia" DECIMAL(8, 2) NOT NULL
);
ALTER TABLE "biblioteca.tb_emprestimos" ADD PRIMARY KEY("id");
ALTER TABLE "biblioteca.tb_emprestimos" OWNER TO "biblioadmin";

-- Name: tb_membros; Type: TABLE; Schema: public
CREATE TABLE "biblioteca.tb_membros"(
    "id" BIGINT NOT NULL AUTO_INCREMENT,
    "nome" VARCHAR(255) NOT NULL,
    "sobrenome" VARCHAR(255) NOT NULL,
    "endereco" VARCHAR(255) NOT NULL,
    "contato" VARCHAR(255) NOT NULL,
    "multa_acumulada" DECIMAL(8, 2) NOT NULL DEFAULT '0'
);
ALTER TABLE "biblioteca.tb_membros" ADD PRIMARY KEY("id");
ALTER TABLE "biblioteca.tb_membros" OWNER TO "biblioadmin";

-- Name: tb_livros; Type: TABLE; Schema: public
CREATE TABLE "biblioteca.tb_livros"(
    "id" BIGINT NOT NULL AUTO_INCREMENT,
    "titulo" VARCHAR(255) NOT NULL,
    "isbn" CHAR(50) NOT NULL,
    "autor_nome" VARCHAR(255) NOT NULL,
    "autor_sobrenome" VARCHAR(255) NOT NULL,
    "ano_publicacao" INTEGER NOT NULL,
    "editora_nome" VARCHAR(255) NOT NULL,
    "emprestado" BOOLEAN NOT NULL DEFAULT '0'
);
ALTER TABLE "biblioteca.tb_livros" ADD PRIMARY KEY("id");
ALTER TABLE "biblioteca.tb_livros" OWNER TO "biblioadmin";

ALTER TABLE
    "biblioteca.tb_emprestimos" ADD CONSTRAINT "tb_emprestimos_membro_id_foreign" FOREIGN KEY("membro_id") REFERENCES "biblioteca.tb_membros"("id");
ALTER TABLE
    "biblioteca.tb_emprestimos" ADD CONSTRAINT "tb_emprestimos_livro_id_foreign" FOREIGN KEY("livro_id") REFERENCES "biblioteca.tb_livros"("id");
