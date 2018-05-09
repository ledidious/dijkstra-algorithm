package Dijkstra;

import java.util.List;
import java.util.PriorityQueue;


public class Node {
	
	/**CLASS NODE
	 * DIJKSTRA
	 * @author 	Nils Terhart & Mario Gierke
	 * @version 1.0
	 * @see 	HS Osnabrueck
	 * @return	value of node 
	 */
	
	private static int quantityNodes = 0; 
	private String ID;					//ID respectively name of the node
	private int weight; 				//weight of note
	private List<Node> connectedNotes;	//list of connected notes
	public final static String TARGETNODE = "TARGETNODE";
	
	//Konstruktor
	
	Node(String thisID, int thisWeight){
		this.ID = thisID;
		this.weight = thisWeight; 
		quantityNodes++; 
	}

	//Methoden
	

	}
	

