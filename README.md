ifmt-blog
==========================

[![Quality Gate Status](http://a666e71a.ngrok.io/api/project_badges/measure?project=ifmt-blog&metric=alert_status)](http://a666e71a.ngrok.io/dashboard?id=ifmt-blog)
[![Reliability Rating](http://a666e71a.ngrok.io/api/project_badges/measure?project=ifmt-blog&metric=reliability_rating)](http://a666e71a.ngrok.io/dashboard?id=ifmt-blog)
[![Security Rating](http://a666e71a.ngrok.io/api/project_badges/measure?project=ifmt-blog&metric=security_rating)](http://a666e71a.ngrok.io/dashboard?id=ifmt-blog)
[![Maintainability Rating](http://a666e71a.ngrok.io/api/project_badges/measure?project=ifmt-blog&metric=sqale_rating)](http://a666e71a.ngrok.io/dashboard?id=ifmt-blog)

## Este é o trabalho do Projeto Integrador do 4º Semestre do curso superior de Sistemas para Internet, IFMT 2019

Sistema de Blog com GrapheneDB, Spring, Thymeleaf, Bootstrap 4 e jQuery

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

## O projeto

Temos o POJO Post, que é a nossa postagem do blog, nela contém seu título, conteúdo e imagem opcional.

>*O projeto usa Spring Data Neo4j.*

Logo, o repositório extende da classe Neo4jRepository que por sua vez em algum momento extende da classe CrudRepository do Spring Data, que já traz consigo implementado diversos métodos de crud.

O serviço traz consigo o repositório de posts para que então o controlador disponibiliza os endpoints, utilizando o serviço para trazer os dados do banco

## Autenticação

Spring Security em memória

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

## Imagens

No momento de fazer uma nova publicação, usando javascript, a imagem selecionada é convertida em base64, e gravada dessa forma no banco.

Na hora de exibir, se houver imagem, o src da tag <img> é trocado pelo base64 que veio do banco, o html suporta e gera a imagem automáticamente

```javascript
function readFile() {
    if (this.files && this.files[0]) {
        var FR = new FileReader();
        FR.addEventListener("load", function (e) {
            document.getElementById("imgPreview").src = e.target.result;
            document.getElementById("imgBase64").value = e.target.result;
        });
        FR.readAsDataURL(this.files[0]);
    }
}
document.getElementById("inputImg").addEventListener("change", readFile);
```