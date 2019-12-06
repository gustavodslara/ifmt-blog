package br.gus.blog.ifmt.ifmtblog.repository;

import br.gus.blog.ifmt.ifmtblog.model.Post;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

import java.util.ArrayList;
import java.util.List;

public class MatchNodes {
    public List<Post> getPosts() {
        Session session = GraphCon.getSession();
        StatementResult result = session.run("MATCH (n:Post) RETURN ID(n) as id, n.title as title, n.content as content");
        List<Post> listaPosts = new ArrayList<>();
        while (result.hasNext()) {
            Record record = result.next();
            Post p = new Post();
            p.setId(record.get("id").asLong());
            p.setTitle(record.get("title").asString());
            p.setContent(record.get("content").asString());
            listaPosts.add(p);
        }
        session.close();
        return listaPosts;
    }
}
