package br.gus.blog.ifmt.ifmtblog.repository;

import br.gus.blog.ifmt.ifmtblog.model.Post;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

public class CreateNode {

    public void createPost(Post post){
        Session session = GraphCon.getSession();
        StringBuilder query = new StringBuilder();
        query.append("CREATE (n:Post {title: '");
        query.append(post.getTitle());
        query.append("', content: '");
        query.append(post.getContent());
        query.append("'})");
        session.run(query.toString());
        session.close();
    }

}
