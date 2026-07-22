## Sistema de Gerenciamento de Pedidos

Projeto desenvolvido em Java com foco em ser um projeto de estudos, não um projeto pronto para produção, nele foi aplicado os principais conceitos de Programação Orientada a Objetos, Collections, Streams, Persistência em CSV e organização em camadas.

O sistema simula o gerenciamento de pedidos de uma pequena loja, permitindo cadastrar clientes, produtos, criar pedidos, controlar estoque e gerar relatórios.

### Funcionalidades
#### Clientes
- Cadastro de clientes
- Busca por ID
- Listagem de clientes
- Remoção de clientes
#### Produtos
- Cadastro de produtos
- Busca por ID
- Busca por nome
- Listagem de produtos
- Entrada de estoque
- Remoção de produtos
#### Pedidos
- Criação de pedidos
- Adição de itens
- Confirmação do pedido
- Cancelamento do pedido
- Alteração de status
- Busca por ID
- Listagem de pedidos

###  Controle de Estoque

O estoque é atualizado automaticamente durante as operações do sistema.

- Ao adicionar um item ao pedido, a quantidade é reservada no estoque.
- Ao cancelar um pedido, os produtos retornam ao estoque.
- Não é permitido adicionar quantidades superiores ao estoque disponível.

### Relatórios

O sistema possui relatórios utilizando Java Streams.

- Produtos mais vendidos
- Pedidos de um cliente
- Cliente com maior número de compras
- Faturamento total
- Ticket médio

### Persistência

Os dados são armazenados em arquivos CSV utilizando Java NIO.

Arquivos utilizados:
````
data/

clientes.csv
produtos.csv
pedidos.csv
itens_pedido.csv
````

Ao iniciar o sistema, todos os arquivos são carregados automaticamente para memória.


### Tecnologias
- Java 21
- Git



### Estrutura do projeto
````
data
│
├── produtos.csv
│
├── pedidos.csv
│
├── clientes.csv
│
├── itens_pedido.csv

src
│
├── application
│
├── entities
│
├── repository
│
├── services
│
├── enums
│
├── exceptions

````

### Conceitos aplicados

- Programação Orientada a Objetos
- Encapsulamento
- Associação
- Composição
- Collections
- HashSet
- HashMap
- Streams
- FlatMap
- GroupingBy
- Comparator
- Persistência em CSV
- Java NIO
- Tratamento de Exceções
- Separação em Camadas

### Aprendizados

Durante o desenvolvimento deste projeto foram estudados:

- Modelagem orientada a objetos
- Separação de responsabilidades (Repository e Service)
- Manipulação de arquivos CSV utilizando Java NIO
- Streams API para geração de relatórios
- Persistência de dados
- Tratamento de exceções personalizadas

### Autor
Junior

Estudante de Engenharia de Software.

Projeto desenvolvido como prática de Java antes do estudo de JPA/Hibernate e Spring Boot.
