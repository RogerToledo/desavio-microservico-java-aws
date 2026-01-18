# DIO - PostgreSQL Application

Este projeto é uma API REST desenvolvida em **Java** com **Spring Boot**, que utiliza **PostgreSQL** como banco de dados. O projeto inclui pipelines de CI/CD configurados com **GitHub Actions** para deploy automatizado na **AWS (EC2 e ECR)**.

## 🛠 Tecnologias Utilizadas

*   **Java 21**
*   **Spring Boot 3.x**
*   **Spring Data JPA / Hibernate**
*   **PostgreSQL** (Banco de dados)
*   **Maven** (Gerenciamento de dependências)
*   **Docker** (Containerização)
*   **AWS ECR** (Registry de Imagens)
*   **GitHub Actions** (CI/CD)

## 📋 Pré-requisitos

Para rodar o projeto localmente, você precisará de:

*   JDK 21 instalado
*   Maven instalado
*   Docker e Docker Compose (opcional, para rodar o banco)
*   Um servidor PostgreSQL rodando localmente ou em container

## ⚙️ Configuração

A aplicação espera as seguintes variáveis de ambiente. Se não forem fornecidas, ela tentará usar os valores padrão definidos no `application.properties`.

| Variável | Descrição | Exemplo                                     |
| :--- | :--- |:--------------------------------------------|
| `SPRING_DATASOURCE_URL` | String de conexão JDBC completa | `jdbc:postgresql://localhost:5432/postgres` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do Banco | `postgres`                                  |
| `SPRING_DATASOURCE_PASSWORD` | Senha do Banco | `minha_senha_segura`                        |

## 🚀 Executando Localmente

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/dio-postgres-app-repo.git
    cd dio-postgres-app-repo
    ```

2.  **Suba o Banco de Dados (Docker):**
    Se não tiver um Postgres local, rode:
    ```bash
    docker run --name postgres-db -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres -p 5432:5432 -d postgres
    ```

3.  **Compile e execute a aplicação:**
    ```bash
    ./mvnw clean package
    ./mvnw spring-boot:run
    ```
    Ou passando as credenciais explicitamente:
    ```bash
    export SPRING_DATASOURCE_PASSWORD=sua_senha
    ./mvnw spring-boot:run
    ```

## 🐳 Executando com Docker

Para rodar a aplicação em container (similar ao ambiente de produção):

1.  **Build da Imagem:**
    ```bash
    docker build -t app-market .
    ```

2.  **Run do Container:**
    ```bash
    docker run -d \
      --name app-container \
      -p 8080:8080 \
      -e SPRING_DATASOURCE_URL="jdbc:postgresql://host.docker.internal:5432/postgres" \
      -e SPRING_DATASOURCE_USERNAME="postgres" \
      -e SPRING_DATASOURCE_PASSWORD="sua_senha" \
      app-market
    ```
    *Nota: `host.docker.internal` permite que o container acesse o localhost da sua máquina.*

## 📦 Pipeline de CI/CD (Deploy)

O projeto possui um workflow no GitHub Actions (`deploy.yml`) que realiza:
1.  Build da aplicação Java.
2.  Criação da imagem Docker.
3.  Push da imagem para o **AWS ECR**.
4.  Conexão via SSH na instância **EC2**.
5.  Deploy do container atualizado passando as variáveis de ambiente necessárias.

### Como disparar o Deploy
Vá na aba **Actions** do GitHub, selecione o workflow **Deploy** e clique em **Run workflow**. É necessário informar:
*   **Server Host:** IP público da instância EC2 da Aplicação.
*   **Database Host:** IP privado ou Endpoint do Banco de Dados (a pipeline monta a URL JDBC automaticamente).

## 🛡️ Troubleshooting de Conexão

Se encontrar erros como `Connection refused` ao conectar no banco remoto:
1.  Verifique se o `postgresql.conf` do servidor de banco tem `listen_addresses = '*'`.
2.  Verifique se o `pg_hba.conf` permite conexões do IP da aplicação.
3.  Verifique se o **Security Group** da AWS na porta `5432` está liberado para o servidor da aplicação.

## Infraestrutura como Código (IaC)
A infraestrutura da aplicação e do banco de dados pode ser provisionada utilizando o Terraform. O projeto [iac-app-postgres](https://github.com/RogerToledo/iac-app-postgres) tem a infra necessária para rodar a aplicação na AWS.
