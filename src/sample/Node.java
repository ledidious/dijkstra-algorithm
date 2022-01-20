package sample;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Represents the nodes in the graph that are connected by {@link Edge}s and
 * each have a different distance relative to the {@link #startNode}.
 *
 * Extends gui element {@link Circle} because displayed by a circle in the gui.
 *
 * @author ledidious & marisbeautypalace
 * @version 1.2
 */
public class Node extends Circle {

    /**
     * Infinite string '∞'
     */
    private static final String STRING_INFINITE = "\u221E";

    /**
     * Which node is the target?
     */
    public static Node targetNode = null;

    /**
     * Which node marks the start where all ways to other nodes begin?
     */
    public static Node startNode = null;

    /**
     * ID respectively name of the node
     */
    private String ID;

    /**
     * weight of note
     */
    private int weight;

    /**
     * The edges which start from this node. Saved in a priority queue
     * because the edges having a lower {@link Edge#weight}, are processed as first ones.
     */
    private PriorityQueue<Edge> edges;

    /**
     * The text element containing the {@link #weight}.
     */
    private Text valueText;

    /**
     * The text element containing the {@link #id}.
     */
    private Text idText;

    /**
     * Konstruktor
     */
    public Node( String thisID ) {
        super( 20 );

        this.ID = thisID;
        this.valueText = new Text();
        this.idText = new Text( thisID );
        this.edges = new PriorityQueue<>( Comparator.comparing( Edge :: getWeight ) );

        setFill( Paint.valueOf( "white" ) );
        setStroke( Paint.valueOf( "black" ) );

        setWeightUndefined();
    }

    // Static methods
    // ======================================================

    /**
     * Sets {@link #startNode}.
     *
     * @param node start node
     */
    public static void setAsStartNode( Node node ) {
        node.setWeight( 0 );
        startNode = node;
    }

    /**
     * Sets {@link #targetNode}.
     *
     * @param node target node
     */
    public static void setAsTargetNode( Node node ) {
        node.setWeightUndefined();
        targetNode = node;
    }

    // Object methods
    // ======================================================

    /**
     * Connects two nodes with the declared {@code edge} and adds
     * the edge to {@link #edges} if this node is the start node.
     *
     * @param edge a connecting edge.
     */
    public void connectNode( Edge edge ) {
        if( edge.getPrevNode().equals( this ) ) {
            edges.add( edge );
        }
    }

    public void setX( int x ) {
        idText.setX( x - 8 );
        valueText.setX( x - 8 );
        setLayoutX( x );
    }

    public void setY( int y ) {
        idText.setY( y - getRadius() - 5 );
        valueText.setY( y + 2 );
        setLayoutY( y );
    }

    public void setWeight( int weight ) {
        this.weight = weight;
        valueText.setText( Integer.toString( weight ) );
    }

    public void setWeightUndefined() {
        this.weight = Integer.MAX_VALUE;
        valueText.setText( STRING_INFINITE );
    }

    public int getWeight() {
        return weight;
    }

    public Text getIdText() {
        return idText;
    }

    public Text getValueText() {
        return valueText;
    }

    public PriorityQueue<Edge> getOutgoingEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return "Node{" +
                "ID='" + ID + '\'' +
                ", weight=" + weight +
                '}';
    }
}
