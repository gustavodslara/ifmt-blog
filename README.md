ifmt-blog
==========================

## Este é o trabalho do Projeto Integrador do 4º Semestre do curso superior de Sistemas para Internet, IFMT 2019

Sistema de Blog com GrapheneDB, Spring, Thymeleaf, Bootstrap 4 e jQuery

![tecnologias](/ghimgs/tecnologias.png)

1. **GrapheneDB**
    * É um serviço de hospedagem de Neo4j, neste caso está sendo usando como um add-on no Heroku
        * Neo4j é um sgbd de banco de dados orientado a grafos (GraphQL)
2. **Spring**
    * É um framework open source para a Java não intrusivo, baseado nos padrões de projeto inversão de controle e injeção de dependência.
3. **Thymeleaft**
    * O Thymeleaf é um mecanismo de modelo Java XML / XHTML / HTML5 que pode funcionar em ambientes web e não web.
4. **Bootstrap**
    * É um framework web com código-fonte aberto para desenvolvimento de componentes de interface e front-end para sites e aplicações web usando HTML, CSS e JavaScript, baseado em modelos de design para a tipografia, melhorando a experiência do usuário em um site amigável e responsivo.
5. **jQuery**
    * É uma biblioteca de funções JavaScript que interage com o HTML, desenvolvida para simplificar os scripts interpretados no navegador do cliente.

## Dependências

![maven](/ghimgs/maven.png)

>Todas as dependências foram selecionadas a partir do [Spring Initializr](https://start.spring.io/)

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-neo4j</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-rest</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
		<dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.neo4j</groupId>
            <artifactId>neo4j</artifactId>
            <version>3.4.9</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.neo4j</groupId>
            <artifactId>neo4j-ogm-embedded-driver</artifactId>
            <version>${neo4j-ogm.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.neo4j</groupId>
            <artifactId>neo4j-ogm-bolt-driver</artifactId>
            <version>${neo4j-ogm.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <dependency>
            <groupId>javax.cache</groupId>
            <artifactId>cache-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.ehcache</groupId>
            <artifactId>ehcache</artifactId>
            <version>3.7.1</version>
        </dependency>
    </dependencies>
```

### Estrutura do projeto

O Spring usa a Inversão de Controle ou Injeção de dependências, e também usao Factory Design Pattern para criação dos objetos dos beans e o MVC na web

![estrutura](/ghimgs/strut.png)

> A estrutura ficou assim, lembrando que não há implementação correta do security por conta do prazo, também DTOs e a separação entre o core e o rest.

## O projeto

Temos o POJO Post, que é a nossa postagem do blog, nela contém seu título, conteúdo e imagem opcional.

>*O projeto usa Spring Data Neo4j.*

Logo, o repositório extende da classe Neo4jRepository que por sua vez em algum momento extende da classe CrudRepository do Spring Data, que já traz consigo implementado diversos métodos de crud.

O serviço traz consigo o repositório de posts para que então o controlador disponibiliza os endpoints, utilizando o serviço para trazer os dados do banco

## Autenticação

Como dito antes, só deu tempo de fazer algo básico do basico
>Literalmente

>Foi utilizada a Basic Auth e não há usuário e senha no banco, estão fixos no código

Então, através de jQuery AJAX, geramos um base64 a partir do usuário inserido **+** a separação ":" e a senha inserida

Por fim, é enviado "Basic " **+** o base64 gerado para o servidor no cabeçalho da requisição "Authorization", e lá antes de criar uma nova postagem, validamos se o login é válido

**Cliente**
```javascript
        $(document).ready(function () {
            $("#formPost").submit(function (e) {
                e.preventDefault();
                var json = ConvertFormToJSON("#formPost");
                var user = $("input#user").val();
                var pwd = $("input#pwd").val();
                function basic_auth(user, pwd) {
                    var token = user + ':' + pwd;
                    var hash = btoa(token);
                    return "Basic " + hash;
                }
                $.ajax({
                    url: '/postar/inserir',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': basic_auth(user, pwd)
                    },
                    type: 'POST',
                    dataType: 'json',
                    data: JSON.stringify(json),
                    success: window.location.href = "/"
                });
            });

            function ConvertFormToJSON(form) {
                var array = jQuery(form).serializeArray();
                var json = {};
                jQuery.each(array, function () {
                    json[this.name] = this.value || '';
                });
                return json;
            }
        });
```

**Servidor**
```java
public class Login {
    public static final String USER = "adminifmt123";
    public static final String PASS = "adminifmt123";

    private Login() {}

    public static boolean isAuth(@NotNull String basicAuth) {
        return basicAuth.equals(authStatic());
    }

    public static String authStatic(){
        String token = USER + ":" + PASS;
        return "Basic " + new String(Base64.getEncoder().encode(token.getBytes()));
    }
}
```

## Desempenho

Ao utilizar o GrapheneDB e a hospedagem a aplicação no Heroku, ambos gratuitos, o desempenho consegue ser bem ruim, principalmente no momento de retornar para a página inicial, onde todos os posts são carregados

Para evitar isso, utilizei EhCache, para criar um cache tanto de todos os posts na página inicial, como de cada post especifíco que o usuário desejar visualizar
>O cache dura 2 minutos, então é um tempo para o usuário navegar, ler um post, retornar para a página principal com **0** lentidão.
>Após esse tempo, a página irá buscar no banco novamente.
>Isso também implica nos novos posts, então ao criar um novo post, somente quando o cache acabar, que ele será exibido na página inicial.

__XML configuração do EhCache__
```xml
<cache-template name="default">
        <expiry>
            <ttl unit="seconds">120</ttl>
        </expiry>
        <resources>
            <heap>1000</heap>
            <offheap unit="MB">10</offheap>
            <disk persistent="true" unit="MB">20</disk>
        </resources>
    </cache-template>
    <cache alias="postsCache" uses-template="default">
        <value-type>java.util.List</value-type>
    </cache>
    <cache alias="postCache" uses-template="default">
        <value-type>br.gus.ifmt.blog.domain.Post</value-type>
    </cache>
```

