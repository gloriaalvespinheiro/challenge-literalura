# Challenge LiterAlura
## Descrição do Projeto
O Challenge LiterAlura é uma aplicação de console desenvolvida em Java com Spring Boot que permite aos usuários buscar livros através da API Gutendex, um índice do Projeto Gutenberg (que disponibiliza livros em domínio público). A aplicação permite persistir os dados dos livros e seus autores em um banco de dados relacional (PostgreSQL, H2, etc.) e oferece funcionalidades para listar livros, autores, autores vivos em um determinado ano e livros por idioma.

Este projeto foi desenvolvido como parte de um desafio de programação proposto pela Alura, focando na integração com APIs externas, persistência de dados com Spring Data JPA e Hibernate, e construção de uma interface de console interativa.

## Funcionalidades
* Buscar Livro por Título: Permite ao usuário pesquisar um livro na API Gutendex pelo título. A aplicação tenta encontrar a correspondência mais exata e, se o livro não estiver no banco de dados local, ele é salvo junto com seus autores.

* Listar Livros Registrados: Exibe todos os livros que foram salvos no banco de dados local.

* Listar Autores Registrados: Exibe todos os autores associados aos livros salvos no banco de dados local.

* Listar Autores Vivos em Determinado Ano: Permite filtrar e listar autores que estavam vivos em um ano específico, com base nos dados de nascimento e falecimento.

* Listar Livros por Idioma: Permite filtrar e listar livros salvos no banco de dados por um idioma específico (ex: "en" para Inglês, "pt" para Português).

* Persistência de Dados: Utiliza Spring Data JPA para salvar e recuperar informações de livros e autores em um banco de dados relacional.

* Tratamento de Entidades: Lida com a complexidade de entidades detached e managed em relações ManyToMany para garantir a integridade dos dados.

## Tecnologias Utilizadas
* Java 17+

* Spring Boot 3.x: Framework para desenvolvimento rápido de aplicações Java.

* Spring Data JPA: Para abstração e simplificação da camada de persistência de dados.

* Hibernate: Implementação de JPA para ORM (Object-Relational Mapping).

* API Gutendex: Para busca de livros em domínio público.

* PostgreSQL (ou H2 Database para desenvolvimento): Banco de dados relacional para persistência dos dados.

* Maven: Ferramenta de automação de build e gerenciamento de dependências.

## Como Rodar o Projeto
### Pré-requisitos
* JDK (Java Development Development Kit) 17 ou superior instalado.

* Maven instalado.

* Um banco de dados PostgreSQL configurado (ou o H2 Database em memória/arquivo para desenvolvimento).

### Configuração do Banco de Dados
1. PostgreSQL (Recomendado para Produção/Desenvolvimento Local):

* Crie um banco de dados PostgreSQL (ex: literalura_db).

* No arquivo src/main/resources/application.properties, configure as credenciais do seu banco de dados:

```spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update # Ou create para criar as tabelas do zero
```



2. H2 Database (Para Desenvolvimento Rápido/Testes):

* Para usar o H2 em memória (os dados são perdidos ao reiniciar a aplicação):

```spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```



* Para acessar o console do H2: Após iniciar a aplicação, vá para http://localhost:8080/h2-console no seu navegador.

### Executando a Aplicação
1. Clone o repositório (se aplicável).

2. Navegue até a pasta raiz do projeto no terminal.

3. Compile e empacote o projeto (isso também baixará as dependências):

```mvn clean install```




4. Execute a aplicação:

```java -jar target/literalura.jar # O nome do seu .jar pode variar```




Ou, se estiver usando uma IDE como IntelliJ IDEA, execute a classe ```Principal``` (ou ```LiteraluraApplication``` se for o nome da sua classe principal ```@SpringBootApplication```).

## Uso da Aplicação (Console)
Após iniciar a aplicação, um menu interativo será exibido no console:

```----- MENU LITERALURA -----
Escolha o número da sua opção:

1 - Buscar livro pelo título
2 - Listar livros registrados
3 - Listar autores registrados
4 - Listar autores vivos em determinado ano
5 - Listar livros em um determinado idioma
0 - Sair
```



* Opção 1 (Buscar livro pelo título): Digite 1 e pressione Enter. Em seguida, digite o título do livro que deseja buscar (ex: Dom Casmurro, Pride and Prejudice, The Adventures of Sherlock Holmes).

* Opção 2 (Listar livros registrados): Digite 2 e pressione Enter para ver todos os livros que você já salvou.

* Opção 3 (Listar autores registrados): Digite 3 e pressione Enter para ver todos os autores que foram salvos.

* Opção 4 (Listar autores vivos em determinado ano): Digite 4 e pressione Enter, e então insira o ano desejado.

* Opção 5 (Listar livros em um determinado idioma): Digite 5 e pressione Enter, e então insira o código do idioma (ex: en, pt, fr).

* Opção 0 (Sair): Digite 0 e pressione Enter para encerrar a aplicação.

## Considerações Finais
Este projeto representou uma experiência de aprendizado e aplicação de conceitos fundamentais no desenvolvimento Java com Spring Boot. Agradeço a oportunidade de ter trabalhado neste desafio.
