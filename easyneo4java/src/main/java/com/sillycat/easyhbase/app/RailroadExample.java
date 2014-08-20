package com.sillycat.easyhbase.app;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

import com.sillycat.easyhbase.util.GraphUtil;
import com.sillycat.easyhbase.util.RelTypes;

public class RailroadExample {

	private static String DB_PATH = "/tmp/neo4j";
	 
	public static void main(final String[] args) {
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		GraphUtil.registerShutdownHook(graphDb);
 
		Transaction tx = graphDb.beginTx();
		try {
			Index<Node> nodeIndex = graphDb.index().forNodes("nodes");
			Node londonNode = graphDb.createNode();
			londonNode.setProperty("name", "London");
			nodeIndex.add(londonNode, "name", "London");
 
			Node brightonNode = graphDb.createNode();
			brightonNode.setProperty("name", "Brighton");
			nodeIndex.add(brightonNode, "name", "Brighton");
 
			Node portsmouthNode = graphDb.createNode();
			portsmouthNode.setProperty("name", "Portsmouth");
			nodeIndex.add(portsmouthNode, "name", "Portsmouth");
 
			Node bristolNode = graphDb.createNode();
			bristolNode.setProperty("name", "Bristol");
			nodeIndex.add(bristolNode, "name", "Bristol");
 
			Node oxfordNode = graphDb.createNode();
			oxfordNode.setProperty("name", "Oxford");
			nodeIndex.add(oxfordNode, "name", "Oxford");
 
			Node gloucesterNode = graphDb.createNode();
			gloucesterNode.setProperty("name", "Gloucester");
			nodeIndex.add(gloucesterNode, "name", "Gloucester");
 
			Node northamptonNode = graphDb.createNode();
			northamptonNode.setProperty("name", "Northampton");
			nodeIndex.add(northamptonNode, "name", "Northampton");
 
			Node southamptonNode = graphDb.createNode();
			southamptonNode.setProperty("name", "Southampton");
			nodeIndex.add(southamptonNode, "name", "Southampton");
 
			// london -> brighton ~ 52mi
			Relationship r1 = londonNode.createRelationshipTo(brightonNode,
					RelTypes.LEADS_TO);
			r1.setProperty("distance", 52);
 
			// brighton -> portsmouth ~ 49mi
			Relationship r2 = brightonNode.createRelationshipTo(portsmouthNode,
					RelTypes.LEADS_TO);
			r2.setProperty("distance", 49);
 
			// portsmouth -> southampton ~ 20mi
			Relationship r3 = portsmouthNode.createRelationshipTo(
					southamptonNode, RelTypes.LEADS_TO);
			r3.setProperty("distance", 20);
 
			// london -> oxford ~95mi
			Relationship r4 = londonNode.createRelationshipTo(oxfordNode,
					RelTypes.LEADS_TO);
			r4.setProperty("distance", 95);
 
			// oxford -> southampton ~66mi
			Relationship r5 = oxfordNode.createRelationshipTo(southamptonNode,
					RelTypes.LEADS_TO);
			r5.setProperty("distance", 66);
 
			// oxford -> northampton ~45mi
			Relationship r6 = oxfordNode.createRelationshipTo(northamptonNode,
					RelTypes.LEADS_TO);
			r6.setProperty("distance", 45);
 
			// northampton -> bristol ~114mi
			Relationship r7 = northamptonNode.createRelationshipTo(bristolNode,
					RelTypes.LEADS_TO);
			r7.setProperty("distance", 114);
 
			// southampton -> bristol ~77mi
			Relationship r8 = southamptonNode.createRelationshipTo(bristolNode,
					RelTypes.LEADS_TO);
			r8.setProperty("distance", 77);
 
			// northampton -> gloucester ~106mi
			Relationship r9 = northamptonNode.createRelationshipTo(
					gloucesterNode, RelTypes.LEADS_TO);
			r9.setProperty("distance", 106);
 
			// gloucester -> bristol ~35mi
			Relationship r10 = gloucesterNode.createRelationshipTo(bristolNode,
					RelTypes.LEADS_TO);
			r10.setProperty("distance", 35);
 
			tx.success();
 
			System.out
					.println("searching for the shortest route from London to Bristol..");
			PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
					PathExpanders.forTypeAndDirection(RelTypes.LEADS_TO,
							Direction.BOTH), "distance");
 
			WeightedPath path = finder.findSinglePath(londonNode, bristolNode);
			System.out.println("London - Bristol with a distance of: "
					+ path.weight() + " and via: ");
			for (Node n : path.nodes()) {
				System.out.print(" " + n.getProperty("name"));
			}
 
			System.out
					.println("\nsearching for the shortest route from Northampton to Brighton..");
			path = finder.findSinglePath(northamptonNode, brightonNode);
			System.out.println("Northampton - Brighton with a distance of: "
					+ path.weight() + " and via: ");
			for (Node n : path.nodes()) {
				System.out.print(" " + n.getProperty("name"));
			}
 
		} finally {
			tx.close();;
			graphDb.shutdown();
		}
	}
	
}
