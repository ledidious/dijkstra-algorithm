package sample;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Node extends Circle {

    public static Node targetNode = null;
    public static Node startNode  = null;

    private static final String STRING_INFINITE = "\u221E";

    /**
     * ID respectively name of the node
     */
    private String ID;

    /**
     * weight of note
     */
    private int weight;

    private PriorityQueue<Edge> edges;

    /**
     * The text element containing the {@link #weight}.
     */
    private Text valueText;

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

    public void connectNode( Edge edge ) {
        edges.add( edge );
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

    public List<Edge> getOutgoingEdges() {
        return new LinkedList<>( edges );
    }

    @Override
    public String toString() {
        return "Node{" +
                "ID='" + ID + '\'' +
                ", weight=" + weight +
                '}';
    }
}
