package com.sillycat.easyscala.start.tour.tour2

class Graph {
  class Node {
    var connectedNodes: List[Node] = Nil
    def connectTo(node: Node) {
      if (connectedNodes.find(node.equals).isEmpty) {
        connectedNodes = node :: connectedNodes
      }
    }
  }
  var nodes: List[Node] = Nil
  def newNode: Node = {
    val res = new Node
    nodes = res :: nodes
    res
  }
}

object GraphTest extends App {
  val g = new Graph
  val n1 = g.newNode
  val n2 = g.newNode
  val n3 = g.newNode
  n1.connectTo(n2)
  n3.connectTo(n1)
}