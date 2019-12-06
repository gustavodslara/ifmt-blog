package br.gus.blog.ifmt.ifmtblog.repository;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class GraphCon {
    public static String graphenedbURL = "bolt://hobby-bmdjdnhaiekcgbkempalaedl.dbs.graphenedb.com:24787";
    public static String graphenedbUser = "app154984279-lkzUiS";
    public static String graphenedbPass = "b.bwBAzyQ5DDZN.0e78xgtXR0iFdFc2";

    public static Session getSession() {
        Driver driver = GraphDatabase.driver(graphenedbURL, AuthTokens.basic(graphenedbUser, graphenedbPass));
        return driver.session();
    }
}
