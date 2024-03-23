# Microserviço de Relatórios para Fiap Hackathon

Este é um microserviço de relatórios para FIAP Hackathon, construído com Micronaut, Gradle e Groovy.

## Construção e Execução

### Requisitos

- Docker

### Passos

1. **Construir a Aplicação:**

   Execute o seguinte comando para construir a aplicação e criar a imagem Docker:

   ```bash
   make build-docker

2. **Iniciar a Aplicação:**

    Após construir a imagem, inicie o contêiner com o seguinte comando:

    ```bash
   docker run \
      --name fiapi-hackaton-relatorios \
      -p 8080:8080 \
      fiapi-hackaton-relatorios

3. **Acessar a Aplicação:**

    Acesse a documentação da API no Swagger em http://localhost:8080


4. **Banco de Dados**

   A aplicação por padrão utiliza o Banco de Dados em memória **H2**. Para usar outros BDs, configure as variáveis de ambientes definidas em `application.yml`. Ex:

   ```bash
   export JDBC_URL=jdbc:postgresql://localhost:5432/fiapihackatonrelatorios
   export JDBC_USER=fiapihackatonrelatorios
   export JDBC_PASSWORD=fiapihackatonrelatorios
   export JDBC_DRIVER=org.postgresql.Driver
