package model;

/**
 * CLASS EDGE
 * DIJKSTRA
 *
 * @author Nils Terhart & Mario Gierke
 * @version 1.0
 * @return value of edge
 */
public class Edge {


    private int  weight;        //weight respectively costs of using edge
    private Node prevNode;        //previous note of edge
    private Node followNode;    //following note of edge

    //Konstruktor

    Edge( int theWeight, Node thePrevNode, Node theFollowNode ) {
        this.weight = theWeight;
        this.prevNode = thePrevNode;
        this.followNode = theFollowNode;
    }
}
