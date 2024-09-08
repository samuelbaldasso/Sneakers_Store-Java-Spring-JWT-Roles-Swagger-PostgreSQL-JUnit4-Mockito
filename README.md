# Backend para Loja de Tênis com Autenticação JWT e Autorização por Roles - Java / Spring Boot

Este projeto é um backend para um sistema de ecommerce de tênis. Ele oferece funcionalidades como autenticação e autorização de usuários, utilizando tokens JWT (JSON Web Tokens) e um sistema de roles. O backend é construído em Java / Spring Boot com o banco de dados PostgreSQL.

## Exemplos de Recursos

- **Autenticação de Usuário**: Sistema de login e registro de usuários.
- **Autorização via JWT**: Após o login, os usuários recebem um token JWT para acessos subsequentes.
- **Gerenciamento de Biblioteca**: Funcionalidades para adicionar, visualizar, editar e deletar itens de produtos, assim como gerenciamento de seus detalhes.
- **Testes unitários**: Testes unitários de todos os controllers, repositories e serviços da aplicação voltados para o usuário.

## Tecnologias Utilizadas

- **Java / Spring Boot**: Ambiente de execução do servidor.
- **PostgreSQL**: Banco de dados eficiente para armazenar os dados dos usuários e informações do ecommerce.
- **JWT (JSON Web Tokens)**: Utilizado para a autenticação e autorização de usuários com base em roles.
- **JUnit4 / Mockito**: Utilizados para os testes unitários dos usuários.

## Documentação da API

A documentação completa das APIs está disponível no endpoint `/swagger-ui/index.html`. A documentação é interativa e permite testar os endpoints diretamente pela interface do Swagger.

## Instruções de Instalação e Uso

1. Clone o repositório: `git clone [URL_DO_REPOSITORIO]`

2. Navegue até a pasta do projeto e instale as dependências: `cd [NOME_DA_PASTA_DO_PROJETO]` e depois execute `mvn install` para gerar a atualização das dependências e o JAR da aplicação (caso desejar).

3. **Instalação do PostgreSQL 14** (Opcional):
   - Baixe o PostgreSQL 14 do site oficial: [Download PostgreSQL](https://www.postgresql.org/download/).
   - Siga as instruções de instalação para o seu sistema operacional.
   - Durante a instalação, defina `postgres` como o nome de usuário e `admin` como a senha para o superusuário do PostgreSQL.
   - Crie um novo banco de dados chamado `sneakers_store`.

4. Inicie o servidor: execute a aplicação pela classe main da mesma na sua IDE preferida (recomendo Intellij IDEA Community ou Ultimate).

5. Acesse `http://localhost:8080/swagger-ui/index.html` em seu navegador para visualizar a documentação da API.

---

Desenvolvido com ❤️ por Samuel Baldasso
