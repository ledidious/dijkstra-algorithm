package sample;

import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * CLASS NODE
 * DIJKSTRA
 *
 * @author Nils Terhart & Mario Gierke
 * @version 1.0
 * @return value of node
 */
public class Edge extends Line {

    /**
     * weight respectively costs of using edge
     */
    private int weight;

    /**
     * previous note of edge
     */
    private Node prevNode;

    /**
     * following note of edge
     */
    private Node followNode;

    /**
     * The text node containing the {@link #weight} of the edge.
     */
    private Text text;

    /**
     * Konstruktor
     */
    public Edge( int theWeight, Node thePrevNode, Node theFollowNode ) {
        this.weight = theWeight;
        this.prevNode = thePrevNode;
        this.followNode = theFollowNode;

        this.text = new Text( Integer.toString( theWeight ) );

        if( thePrevNode.getLayoutX() > theFollowNode.getLayoutX() ) {
            final Node temp = thePrevNode;
            thePrevNode = theFollowNode;
            theFollowNode = temp;
        }

        setStartX( thePrevNode.getLayoutX() + thePrevNode.getRadius() );
        setStartY( thePrevNode.getLayoutY() );

        setEndX( theFollowNode.getLayoutX() - theFollowNode.getRadius() );
        setEndY( theFollowNode.getLayoutY() );

        text.setX( getStartX() + ( getEndX() - getStartX() ) / 2 );
        text.setY( getStartY() + ( getEndY() - getStartY() ) / 2 );
    }

    public Text getText() {
        return text;
    }
}
