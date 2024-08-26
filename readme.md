<p align="center" width="100%">
    <img width="50%" src="https://github.com/Franccysco/petize/blob/main/images/img.png"> 
</p>

<h3 align="center">
  Teste Backend Java Petize
</h3>

<h1 align="center"> Gerenciamento de Pedidos </h1> 
<p align="center"> 
    <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-%2304D361">
    <img src="https://img.shields.io/static/v1?label=Java&message=Spring%20Boot&color=green&labelColor=black" alt="Java Spring Boot" /> 
    <img src="https://img.shields.io/static/v1?label=Mensageria&message=RabbitMQ&color=blue&labelColor=black" alt="RabbitMQ" />
    <img alt="Version: 1.0" src="https://img.shields.io/badge/version-1.0-yellowgreen">
</p>
API para gerenciamento de pedidos que processa as mudanças de status de forma assíncrona usando RabbitMQ.

## Tecnologias

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [RabbitMQ](https://www.rabbitmq.com/)
- [Mysql](https://dev.mysql.com/downloads/)


### Pré-requisitos

- Docker para executar RabbitMQ e MySQL, necessário Docker instalado

### Executar RabbitMQ e MySQL com Docker

```yaml
services:
  mysql:
    container_name: 'mysql-petize'
    image: 'mysql:latest'
    environment:
      - 'MYSQL_DATABASE=petizedb'
      - 'MYSQL_PASSWORD=123'
      - 'MYSQL_ROOT_PASSWORD=123'
      - 'MYSQL_USER=admin'
    ports:
      - '3306:3306'

  rabbitmq:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"
```

## Como Executar

- Clonar repositório git
```bash
# Entre na pasta
$ cd docker
# Rode o comando
$ docker compose up 
```
- Construir o projeto:
```bash
$ ./mvnw clean package
```
- Executar a aplicação:
``` bash
$ java -jar target/todolist-0.0.1-SNAPSHOT.jar
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080).
## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [Insomnia](https://insomnia.rest/):

### Endpoints Produtos
- Criar produto
```
$ http POST :8080/products name="Notebook" description="Notebook Dell Inspiron 15" price=3499.90

  {
    "name": "Notebook",
    "description": "Notebook Dell Inspiron 15",
    "price": 3499.90
  }
```

- Listar Produtos
```
$ http GET :8080/products

[
 {
   "id": 1,
   "name": "Notebook",
   "description": "Notebook Dell Inspiron 15",
   "price": 3499.90
 },
 {
   "id": 2,
   "name": "Samsung Book",
   "description": "Notebook Samsung book 4",
   "price": 4599.90
 },
 {
   "id": 3,
   "name": "Televisão",
   "description": "Smart TV Samsung 50\" 4K",
   "price": 2999.00
 }
]
```

- Listar Produtos por ID
```
$ http GET :8080/products/1

{
   "id": 1,
   "name": "Notebook",
   "description": "Notebook Dell Inspiron 15",
   "price": 3499.90
}
```

- Atualizar Produtos
```
$ http PUT :8080/products/1 name="Samsung Book" description="Notebook Samsung book 4" price=4599.90

{
   "name": "Samsung Book",
   "description": "Notebook Samsung book 4",
   "price": 4599.90
}
```

- Remover Produto
```
http DELETE :8080/products/1
[ ]
```

### Endpoints Pedidos
- Criar Pedido
```
$ http POST :8080/orders "orderDate": "2023-08-25" "products": [ { "productId": 1, "quantity": 2 },{ "productId": 2, "quantity": 1 }, { "productId": 4, "quantity": 5 }], "status": "PENDING"

{
   "orderDate": "2023-08-25",
   "products": [
      { "productId": 1, "quantity": 2 },
      { "productId": 2, "quantity": 1 },
      { "productId": 4, "quantity": 5 }
    ],
   "status": "PENDING"
}
```

- Listar Pedidos
```
$ http GET :8080/orders
[
  {
    "id": 1,
    "orderDate": "2023-08-25",
    "productQuantities": {
      "Product(id=1, name=Notebook, description=Notebook Dell Inspiron 15, price=3499.90)": 2,
      "Product(id=2, name=Samsung Book, description=Notebook Samsung book 4, price=4599.90)": 1,
      "Product(id=4, name=Televisão, description=Smart TV Samsung 50\" 4K, price=2999.00)": 5
    },
    "status": "PENDING"
  },
  {
    "id": 2,
    "orderDate": "2023-08-20",
    "productQuantities": {
      "Product(id=1, name=Notebook, description=Notebook Dell Inspiron 15, price=3499.90)": 1,
      "Product(id=2, name=Samsung Book, description=Notebook Samsung book 4, price=4599.90)": 3,
      "Product(id=4, name=Televisão, description=Smart TV Samsung 50\" 4K, price=2999.00)": 2
    },
    "status": "COMPLETED"
  }
]
```

- Listar Produtos por ID
```
$ http GET :8080/orders/2

{
  "id": 2,
  "orderDate": "2023-08-20",
  "productQuantities": {
    "Product(id=1, name=Notebook, description=Notebook Dell Inspiron 15, price=3499.90)": 1,
    "Product(id=2, name=Samsung Book, description=Notebook Samsung book 4, price=4599.90)": 3,
    "Product(id=4, name=Televisão, description=Smart TV Samsung 50\" 4K, price=2999.00)": 2
  },
  "status": "COMPLETED"
}
```

- Atualizar Pedidos
```
$ http PUT :8080/orders/2 "orderDate": "2023-08-20" "products": [ { "productId": 1, "quantity": 1 },{ "productId": 2, "quantity": 3 }, { "productId": 4, "quantity": 2}], "status": "PENDING"

{
    "orderDate": "2023-08-20",
    "products": [
        { "productId": 1, "quantity": 1 },
        { "productId": 2, "quantity": 3 },
        { "productId": 4, "quantity": 2 }
    ],
    "status": "PENDING"
}
```

- Atualizar Status Pedido
```
$ http PATCH :8080/orders/2
PENDING
```

## Dockerização da Aplicação
###  Containerizar a aplicação e suas dependências (MySQL e RabbitMQ).
- Criação do Dockerfile para Spring Boot:
``` bash
# Usar a imagem do JDK 21 como base
FROM eclipse-temurin:21-jdk-alpine

# Definir o diretório de trabalho
WORKDIR /app

# Copiar o arquivo JAR gerado para o container
COPY target/gerenciapedidos-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta 8080 para acesso à aplicação
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
```

- Dockerfile:

```yaml
version: '3'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    depends_on:
      - rabbitmq
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/petizedb
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: 123

  rabbitmq:
    image: rabbitmq:3-management
    ports:
      - "5672:5672"
      - "15672:15672"

  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: 123
      MYSQL_DATABASE: petizedb
    ports:
      - "3306:3306"
```
- Passos
Criar o JAR:
```bash
./mvnw clean package
```
- Executar Docker Compose:

```bash
docker-compose up --build
```
# Implantação na AWS (Feito através de pesquisas)
## Implantar a aplicação e suas dependências na AWS.
### MySQL no AWS RDS:
- Crie uma instância RDS MySQL e configure as credenciais.
### RabbitMQ no ECS ou outra solução:
- Utilize o Amazon ECS para gerenciar o RabbitMQ como um container.
### Implantação da aplicação no ECS:
- Crie um cluster no ECS para rodar a aplicação Spring Boot como um container.
### Configuração de rede e segurança:
- Configure grupos de segurança no RDS e ECS para permitir comunicação entre os serviços.


## :memo: Licença ##

Este projeto está sob licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.


Feito com :heart: por <a href="https://github.com/Franccysco" target="_blank">Francisco Sousa</a>

&#xa0;

<a href="#top">Voltar para o topo</a>