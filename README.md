# Wishlist API
API para controle de uma Wishlist de e-commerce. 
<br>Desenvolvida com Java 11, Spring Boot, Maven e MongoDB.

## Requisitos
Para fazer o build e deploy da aplicação será necessário:
- JDK 11
- IDE (IntelliJ IDEA, por exemplo)
- Maven
- MongoDB

## Banco de Dados e carga inicial
Antes de fazer o deploy do projeto, instale o MongoDB. O host e o nome do banco de dados serão passados via variáveis de ambiente.
<br>Quando a aplicação é executada, uma carga inicial de dados é feita da seguinte forma:
- Caso a collection de clientes esteja vazia: carga com 4 clientes
- Caso a collection de produtos esteja vazia: carga com 21 produtos
- Caso a collection de wishlists esteja vazia: carga com 1 wishlist contendo 20 produtos

## Execução
### Deploy local via IDE
- Importe o projeto maven
- Configure para a JDK 11
- Por default, a aplicação considerará os valores de "localhost" e "wishlistDB" para as variáveis de ambiente MONGO_HOST e MONGO_DBNAME, respectivamente. Porém, você pode configurá-las com outros valores se preferir
- Clique com o botão direito na classe WishlistApplication -> Run ou Debug

### Deploy local via terminal
Com a JDK 11 e o Maven instalados, abra o terminal na raiz do projeto e execute um dos seguintes comandos.
<br><br>
Assumindo os valores padrão citados acima para as variáveis de ambiente:
```java
mvn spring-boot:run
```
Alterando as os valores das variáveis de ambiente:
```java
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-DMONGO_HOST=hostName -DMONGO_DBNAME=databaseName"
```

## Utilização e documentação dos endpoints
Com a aplicação em execução, é possível acessar a documentação dos endpoints e utilizá-los acessando ```localhost:8080/swagger-ui.html```.

