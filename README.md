# Projeto: SISTEMA DE BIBLIOTECA  (java-sistemabibliotecas)

Projeto disciplina Programação Web II com o fim de implementar um sistema de bibliotecas numa arquitetura REST usando Spring Boot e persistência H2/PostgreSQL.

- Programa: [Ada](https://ada.tech/) Ser + Tech Programa 50+ (Turma #1076)
- Curso: Programação Web II
- Prof.:  Alex Facincani (https://github.com/facincani)
- Exercício: implementar CRUD em API REST


## DESCRIÇÃO DO PROJETO:
```
Sistema de biblioteca:
Gestão de Livros
Must have
Criar, visualizar, atualizar e deletar (CRUD) livros.
Informações de um livro podem incluir mas não se limitam à: título, autor, ano de publicação, ISBN... 

Gestão de Membros
Criar, visualizar, atualizar e deletar (CRUD) membros.
Informações de um membro podem incluir mas não se limitam à nome, endereço, contato...

Nice to have
Gestão de Empréstimos
Registrar empréstimos de livros aos membros.
Definir prazo para devolução e gerenciar multas por atraso.

Considere apenas relacionamentos 1:1
```

# PROJETO SISTEMA PARA BIBLIOTECAS

Este é um projeto que tem a finalidade de usar a linguagem Java para implementar um serviço RESTful de controle para um sistema de bibliotecas. Entidades básicas: livros, membros e empréstimos. A interface de acesso será via métodos HTTP acessando endpoints e provendo operações [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) (operações de Create, Read, Update e Delete). Originalmente, operações CRUD foram relacionadas com persistência de dados e há comandos SQL para cada operação. Em arquitetura de APIs RESTfull, métodos HTTP são usados com este objetivo.

### Disclaimer
*As informações neste Readme.md contém informações sobre o projeto (construção e execução) mas também apresenta informações com o fim de documentar questões, problemas e algumas soluções que surgiram durante o desenvolvimento e aperfeiçoamento.*


## Frameworks de suporte

[Spring Boot](https://spring.io/projects/spring-boot)
Plataforma para criar aplicações auto-contidas (servidor de aplicações Tomcat/Jetty/Undertow incluído e configurado) e no qual o Spring configura dependências e inicializa os respectivos objetos através do padrão de Injeção de Dependências (DI - *Dependency Injection*).

### Persistência de dados

[Spring Data (JPA)](https://spring.io/projects/spring-data) Framework Spring para persistência. Através de anotações permite usar o framework do Hibernate para persistir dados em Banco de Dados (relacional).

[H2](https://h2database.com) Banco de Dados relacional de código aberto (open source) em memória (ou em arquivo) que facilita o processo de arquitetura de uma aplicação para fins de teste.

[PostgreSQL](https://www.postgresql.org/) Banco de Dados relacional de código aberto (open source) para projetos que aliam performance, robustez e confiança. Deve ser instalado localmente, mas pode ser usado em imagens Docker.


### Outros recursos
A arquitetura API RESTful apresentada em aula será base para este projeto de sistema para bibliotecas. O que deverá ser implementado/incorporado:
- Resolução completa do exercício
- Docker para implantação do banco de dados PostgreSQL
- [Docker Swarm](https://docs.docker.com/engine/swarm/) para implementar de forma segura os valores de segredos (principalmente senhas).
- [Jasypt](http://www.jasypt.org/) Recurso para cifrar/decifrar dados sensíveis em arquivos de recursos do projeto. Implementação mais recente com integração Spring devido a [Ulises Bocchio](https://github.com/ulisesbocchio/jasypt-spring-boot).
- Autenticação para acesso a áreas da aplicação (***esperança***).


# Modelagem do projeto de Sistema de Bibliotecas

A imagem da figura a seguir apresenta o diagrama Entidade-Relacionamento relativos à persistência dos dados que são criados e geridos pela aplicação.

## Diagrama de classes básico do projeto
<img src="./imagens/drawSQL-diagrama-ER-biblioteca-ONE-to-ONE.png" alt="Diagrama ER do modelo de bibliotecas">

Algumas considerações sobre as estruturas de dados:
- Considerando a restrição de relacionamentos 1:1, cada livro só terá um autor e cada usuário só pode emprestar um único livro.
- Tabela **Membros** (`tb_membros`): o campo endereco é composto e irá conter todos os dados relativos a endereço (logradouro, número, complemento, CEP, cidade, estado, país); objetivo: diminuir complexidade. O campo contato pode conter telefone ou email, logo, VARCHAR (255). Há um campo contendo o valor da multa acumulada de empréstimos de livros entregues em atraso (`multa_acumulada`). A aplicação deverá prover um endpoint (função/método) para pagamento da mesma e para zerar o respectivo valor. * Em princípio, esta funcionalidade não será incluída neste projeto*.
- Tabela **Livros** (`tb_livros`): há os campos descritivos do objeto do empréstimo acrescido do campo de `emprestado` que é Boolean e será setado em **True** para livro emprestado (o registro de um livro terá este campo inicializado em **False**).
- Tabela **Emprestimos** (`tb_emprestimos`): Cada empréstimo associa um membro (`membro_id`) com um livro (`livro_id`). Ao realizar o empréstimo, deve ser especificada a data de devolução (`data_devolucao`) e o valor da multa por dia. O usuário membro saberá na data de empréstimo quanto será a multa devida por dia de atraso. Aqui, esse dado por vir de uma tabela que associa tipos de materiais com valores de multa (o modelo requer, neste caso, um campo de tipo de livro/material). Essa parte da modelagem não foi incluída visando minimizar complexidade.

# Configuração básicas das classes e respectivas anotações Spring

Estrutura em pacotes para acomodar as respectivas classes que definem a estrutura básica de separação de responsabilidades:
```
src/main/java/tech.ada
package tech.ada.sistemabiblioteca.model;
package tech.ada.sistemabiblioteca.controller;
package tech.ada.sistemabiblioteca.service;
package tech.ada.sistemabiblioteca.repository;
```

- `Controller` (anotação Spring `@Controller`) gerência da interface entre o usuário e a lógica da aplicação (Service); exposição da funcionalidade (no caso, APIs REST)
- `Service` (anotação Spring `@Service`) implementação da lógica de negócio
- `Repository` (anotação Spring `@Repository`) gerência do armazenamento

Todas as três anotações são derivadas de `@Component`.


Referência para o [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html):

Pacote base: package tech.ada.biblioteca;

Classe principal (base) do projeto anotada com `@SpringBootApplication`: meta-anotação que combina `@SpringBootConfiguration`, `@EnableAutoConfiguration` e `@ComponentScan`. Elas tratam da configuração automática, suporte a propriedades e gerenciamento das dependências (varredura de componentes no diretório do projeto) para prover um ambiente de execução das aplicações.


Para serviços REST, o [Spring MVC](https://www.baeldung.com/spring-mvc-tutorial) (Model-View-Controller) fornece anotações `@RestController` e `@RequestMapping`. A @RestController é anotação tipo *stereotype* que: facilita leitura e indica o que uma classe deve fazer (neste caso, tratar requisições Web). A @RequestMapping é base para todas as especializações de requisições HTTP. Exemplo: @PostMapping é equivalente a @RequestMapping(method=POST). @RestController é uma especialização e inclui as anotações `@Controller` e `@ResponseBody`.

