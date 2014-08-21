package com.sillycat.easyhbase.app;

import java.util.Iterator;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
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
			//create node London
			londonNode.setProperty("name", "London");
			//add name into index
			nodeIndex.add(londonNode, "name", "London");
 
			//Brighton
			Node brightonNode = graphDb.createNode();
			brightonNode.setProperty("name", "Brighton");
			nodeIndex.add(brightonNode, "name", "Brighton");
 
			//Portsmouth
			Node portsmouthNode = graphDb.createNode();
			portsmouthNode.setProperty("name", "Portsmouth");
			nodeIndex.add(portsmouthNode, "name", "Portsmouth");
 
			//Bristol
			Node bristolNode = graphDb.createNode();
			bristolNode.setProperty("name", "Bristol");
			nodeIndex.add(bristolNode, "name", "Bristol");
 
			//Oxford
			Node oxfordNode = graphDb.createNode();
			oxfordNode.setProperty("name", "Oxford");
			nodeIndex.add(oxfordNode, "name", "Oxford");
 
			//Gloucester
			Node gloucesterNode = graphDb.createNode();
			gloucesterNode.setProperty("name", "Gloucester");
			nodeIndex.add(gloucesterNode, "name", "Gloucester");
 
			//Northampton
			Node northamptonNode = graphDb.createNode();
			northamptonNode.setProperty("name", "Northampton");
			nodeIndex.add(northamptonNode, "name", "Northampton");
 
			//Southampton
			Node southamptonNode = graphDb.createNode();
			southamptonNode.setProperty("name", "Southampton");
			nodeIndex.add(southamptonNode, "name", "Southampton");
 
			// london -> brighton ~ 52mi
			Relationship r1 = londonNode.createRelationshipTo(brightonNode,
					RelTypes.LEADS_TO);
			//relationship gets property distance
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
 
			System.out.println("searching for the shortest route from London to Bristol..");
			PathFinder<WeightedPath> finder = GraphAlgoFactory.dijkstra(
					PathExpanders.forTypeAndDirection(RelTypes.LEADS_TO,Direction.OUTGOING), "distance");
 
			WeightedPath path = finder.findSinglePath(londonNode, bristolNode);
			System.out.println("London - Bristol with a distance of: " + path.weight() + " and via: ");
			for (Node n : path.nodes()) {
				System.out.print(" " + n.getProperty("name"));
			}
			
			System.out.println();
			System.out.println();
			
			System.out.println("searching for all routes from London to southamptonNode..");
			PathFinder<Path> finderAll = GraphAlgoFactory.allPaths(
					PathExpanders.forTypeAndDirection(RelTypes.LEADS_TO,Direction.OUTGOING), Integer.MAX_VALUE);
			
			Iterable<Path> allPaths = finderAll.findAllPaths(londonNode, southamptonNode);
			Iterator<Path> it = allPaths.iterator();
			while(it.hasNext()){
				Path tmp_path = (Path) it.next();
				System.out.println("Path is as follow: ");
				int distance = 0;
				for (Node n : tmp_path.nodes()) {
					System.out.print(" " + n.getProperty("name"));
					distance = distance + (Integer)n.getRelationships(RelTypes.LEADS_TO, Direction.OUTGOING).iterator().next().getProperty("distance");
				}
				System.out.println(" with distance:" + distance);
			}
 
 
		} finally {
			tx.close();;
			graphDb.shutdown();
		}
	}
	
}
