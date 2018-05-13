package sample;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * An edge connecting different {@link Node}s. Extends gui element {@link Line}
 * because displayed by a line in the gui.
 *
 * @author Nils Terhart & Mario Gierke
 * @version 1.2
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

    private final Circle endPoint;

    /**
     * Konstruktor
     */
    public Edge( int theWeight, Node thePrevNode, Node theFollowNode ) {
        this.weight = theWeight;
        this.prevNode = thePrevNode;
        this.followNode = theFollowNode;

        this.text = new Text( Integer.toString( theWeight ) );

        thePrevNode.connectNode( this );

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

        // Angle
        endPoint = new Circle( 3 );
        endPoint.setLayoutX( getEndX() );
        endPoint.setLayoutY( getEndY() );
        endPoint.setFill( Paint.valueOf( "black" ) );
    }

    public Text getText() {
        return text;
    }

    public Circle getEndPoint() {
        return endPoint;
    }

    public Node getPrevNode() {
        return prevNode;
    }

    public Node getFollowNode() {
        return followNode;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "weight=" + weight +
                ", prevNode=" + prevNode +
                ", followNode=" + followNode +
                '}';
    }
}
