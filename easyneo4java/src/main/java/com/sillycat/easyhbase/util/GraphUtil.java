package com.sillycat.easyhbase.util;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.Index;
import org.neo4j.tooling.GlobalGraphOperations;

public class GraphUtil {

	public static void cleanUp(final GraphDatabaseService graphDb,
			final Index<Node> nodeIndex) {
		for (Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
			for (Relationship rel : node.getRelationships()) {
				rel.delete();
				//delete all relationship
			}
			//remove all nodes from index
			nodeIndex.remove(node);
			//remote node itself
			node.delete();
		}
 
	}
 
	public static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}
	
}
