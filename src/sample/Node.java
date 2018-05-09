package sample;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.List;

public class Node extends Circle {

    public static Node targetNode = null;
    public static Node startNode  = null;

    private static final String STRING_INFINITE = "\u221E";

    private static int quantityNodes = 0;

    /**
     * ID respectively name of the node
     */
    private String ID;

    /**
     * weight of note
     */
    private int weight;

    /**
     * list of connected notes
     */
    private List<Node> connectedNodes;

    /**
     * The text node containing the {@link #weight}.
     */
    private Text text;

    /**
     * Konstruktor
     */
    public Node( String thisID ) {
        super( 20 );

        this.ID = thisID;
        this.text = new Text();

        setFill( Paint.valueOf( "white" ) );
        setStroke( Paint.valueOf( "black" ) );

        setWeightUndefined();

        quantityNodes++;
    }

    // Static methods
    // ======================================================

    public static void setAsStartNode( Node node ) {
        node.setWeight( 0 );
        startNode = node;
    }

    public static void setAsTargetNode( Node node ) {
        node.setWeightUndefined();
        targetNode = node;
    }

    // Object methods
    // ======================================================

    /**
     * Adds a connected node to the list of {@link #connectedNodes}.
     */
    public void addConnectedNode( Node connectedNode ) {
        connectedNodes.add( connectedNode );
    }

    public void setX( int x ) {
        text.setX( x - 8 );
        setLayoutX( x );
    }

    public void setY( int y ) {
        text.setY( y + 2 );
        setLayoutY( y );
    }

    public void setWeight( int weight ) {
        this.weight = weight;
        text.setText( Integer.toString( weight ) );
    }

    public void setWeightUndefined() {
        this.weight = - 1;
        text.setText( STRING_INFINITE );
    }

    public int getWeight() {
        return weight;
    }

    public Text getText() {
        return text;
    }
}
