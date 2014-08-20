package com.sillycat.easyhbase.app;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

import com.sillycat.easyhbase.util.GraphUtil;

public class IndexSearchExample {

	private static String DB_PATH = "/tmp/neo4j";

	public static void main(final String[] args) {
		GraphDatabaseService graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabase(DB_PATH);

		GraphUtil.registerShutdownHook(graphDb);

		Transaction tx = graphDb.beginTx();
		try {

			Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
			// cleanup first for this tutorial
			GraphUtil.cleanUp(graphDb, nodeIndex);

			Node userNode1 = graphDb.createNode();
			userNode1.setProperty("id", 1);
			userNode1.setProperty("name", "Peter");
			nodeIndex.add(userNode1, "id", 1);
			nodeIndex.add(userNode1, "name", "Peter");

			Node userNode2 = graphDb.createNode();
			userNode2.setProperty("id", 2);
			userNode2.setProperty("name", "Ray");
			nodeIndex.add(userNode2, "id", 2);
			nodeIndex.add(userNode2, "name", "Ray");

			tx.success();

			System.out.println("searching for user with id=2..");
			Node user = nodeIndex.get("id", 2).getSingle();
			System.out.println("The name of the user with id=2 is: "
					+ user.getProperty("name"));

			System.out.println("searching for user with name=Peter..");
			Node user2 = nodeIndex.get("name", "Peter").getSingle();
			System.out.println("The id of the user with name=Peter is: "
					+ user2.getProperty("id"));
		} finally {
			tx.close();
			;
			graphDb.shutdown();
		}

	}

}
