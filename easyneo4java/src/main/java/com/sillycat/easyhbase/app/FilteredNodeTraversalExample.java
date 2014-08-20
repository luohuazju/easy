package com.sillycat.easyhbase.app;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.TraversalPosition;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.sillycat.easyhbase.util.GraphUtil;
import com.sillycat.easyhbase.util.RelTypes;

public class FilteredNodeTraversalExample {

	private static String DB_PATH = "/tmp/neo4j";
	 
	public static void main(final String[] args) {
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		
		GraphUtil.registerShutdownHook(graphDb);
 
		Transaction tx = graphDb.beginTx();
		try {
			Node peterNode = graphDb.createNode();
			peterNode.setProperty("id", 1);
			peterNode.setProperty("name", "Peter");
 
			Node rayNode = graphDb.createNode();
			rayNode.setProperty("id", 2);
			rayNode.setProperty("name", "Ray");
 
			Node egonNode = graphDb.createNode();
			egonNode.setProperty("id", 3);
			egonNode.setProperty("name", "Egon");
 
			Node winstonNode = graphDb.createNode();
			winstonNode.setProperty("id", 4);
			winstonNode.setProperty("name", "Winston");
 
			Node slimerNode = graphDb.createNode();
			slimerNode.setProperty("id", 5);
			slimerNode.setProperty("name", "Slimer");
 
			Relationship rel1 = peterNode.createRelationshipTo(rayNode,
					RelTypes.KNOWS);
			rel1.setProperty("visibility", "public");
 
			Relationship rel2 = rayNode.createRelationshipTo(egonNode,
					RelTypes.KNOWS);
			rel2.setProperty("visibility", "hidden");
 
			Relationship rel3 = rayNode.createRelationshipTo(winstonNode,
					RelTypes.KNOWS);
			rel3.setProperty("visibility", "public");
 
			Relationship rel4 = winstonNode.createRelationshipTo(slimerNode,
					RelTypes.KNOWS);
			rel4.setProperty("visibility", "public");
 
			tx.success();
 
			System.out
					.println("traversing nodes for Peter's public acquaintances..");
			Traverser acquaintanceTraverser = getAcquaintances(peterNode);
			for (Node acquaintanceNode : acquaintanceTraverser) {
				System.out.println("Peter knows "
						+ acquaintanceNode.getProperty("name") + " (id: "
						+ acquaintanceNode.getProperty("id") + ") at depth: "
						+ acquaintanceTraverser.currentPosition().depth());
			}
		} finally {
			tx.finish();
		}
 
		graphDb.shutdown();
	}
 
	private static Traverser getAcquaintances(final Node personNode) {
		return personNode.traverse(Order.BREADTH_FIRST,
				StopEvaluator.END_OF_GRAPH, new ReturnableEvaluator() {
					public boolean isReturnableNode(
							final TraversalPosition currentPos) {
						return !currentPos.isStartNode()
								&& currentPos.lastRelationshipTraversed()
										.hasProperty("visibility")
								&& "public".equals(currentPos
										.lastRelationshipTraversed()
										.getProperty("visibility"));
					}
				}, RelTypes.KNOWS, Direction.OUTGOING);
	}
	
	
}
