package Dijkstra;

public class Edge {

	/**CLASS EDGE
	 * DIJKSTRA
	 * @author 	Nils Terhart & Mario Gierke
	 * @version 1.0
	 * @see 	HS Osnabrueck
	 * @return	value of edge
	 */
	
	private int weight; 		//weight respectively costs of using edge
	private Node prevNode; 		//previous note of edge
	private Node followNode; 	//following note of edge
	
	//Konstruktor
	
	Edge(int theWeight, Node thePrevNode, Node theFollowNode){
		this.weight = theWeight;
		this.prevNode = thePrevNode;
		this.followNode = theFollowNode;
	}
	
	
}
