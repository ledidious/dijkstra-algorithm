package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;

/**
 * Entry point and implementation of algorithm.
 *
 * @author Nils Terhart & Mario Gierke
 * @version 1.2
 */
public class Main extends Application {

    // Attributes
    // ======================================================

    /**
     * The root pane containing all other gui elements.
     */
    private Pane rootPane;

    /**
     * The list of outgoing nodes. Each of the edges starting from an outgoing node,
     * are followed and checked for the shortest way to their neighbour nodes and their neighbours.
     */
    private Set<Node> outgoingNodes = new HashSet<>();

    /**
     * Flag that controls execution of the next step after
     * button "Next" has been clicked.
     */
    private boolean execNextStep = false;

    /**
     * Current edge and node which are currently highlighted in green.
     */
    private Edge currentEdge = null;
    private Node currentNode = null;

    // Static methods
    // ======================================================

    /**
     * Main program
     *
     * @param args arguments from command line
     */
    public static void main( String[] args ) {
        launch( args );
    }

    // Object methods
    // ======================================================

    /**
     * Start method invoked from java fx.
     *
     * @param primaryStage the root gui container
     */
    @Override
    public void start( Stage primaryStage ) {

        /*
         * Root panel
         */
        rootPane = new Pane();
        primaryStage.setTitle( "Dijkstra Algorithmus" );
        primaryStage.setScene( new Scene( rootPane, 500, 500 ) );

        prepareGraph();

        /*
         * Next button
         */
        final Button nextButton = new Button( "Weiter" );
        nextButton.setOnMouseClicked( event -> execNextStep = true );
        nextButton.setLayoutX( 20 );
        nextButton.setLayoutY( 300 );
        nextButton.setDefaultButton( true );
        rootPane.getChildren().add( nextButton );

        /*
         * Exit button
         */
        final Button exitButton = new Button( "Beenden" );
        exitButton.setLayoutX( 100 );
        exitButton.setLayoutY( 300 );
        exitButton.setCancelButton( true );
        exitButton.setOnMouseClicked( ( event ) -> System.exit( 0 ) );
        exitButton.setVisible( false );
        rootPane.getChildren().add( exitButton );

        /*
         * Start algorithm in separate thread
         */
        final Thread thread = new Thread( () -> {
            doDijkstra( Node.startNode );
            exitButton.setVisible( true );
            nextButton.setVisible( false );
        } );
        thread.setDaemon( true );
        thread.start();

        // Display gui
        primaryStage.show();
    }

    /**
     * Prepares the graph consisting from the different
     * nodes and edges.
     */
    private void prepareGraph() {

        final Node nodeS = addNode( "S", 40, 150 );
        Node.setAsStartNode( nodeS );

        final Node nodeU = addNode( "U", 140, 70 );
        final Node nodeX = addNode( "X", 180, 200 );
        final Node nodeV = addNode( "V", 240, 70 );
        Node.setAsTargetNode( nodeV );
        final Node nodeY = addNode( "Y", 280, 170 );

        addEdge( 5, nodeS, nodeX );
        addEdge( 10, nodeS, nodeU );
        addEdge( 3, nodeX, nodeU );
        addEdge( 1, nodeU, nodeV );
        addEdge( 2, nodeX, nodeY );
        addEdge( 6, nodeV, nodeY );
    }

    /**
     * Adds a new node to the graph and returns the node.
     *
     * @param id id of the node
     * @param x  x position
     * @param y  y position
     *
     * @return the node created
     */
    private Node addNode( String id, int x, int y ) {

        final Node node = new Node( id );
        node.setX( x );
        node.setY( y );
        node.setStrokeWidth( 2 );

        rootPane.getChildren().add( node );
        rootPane.getChildren().add( node.getIdText() );
        rootPane.getChildren().add( node.getValueText() );

        return node;
    }

    /**
     * Connects two nodes by creating an edge between them and inserting it
     * in the graph.
     *
     * @param weight     the weight of the edge
     * @param prevNode   the start node
     * @param followNode the end node
     */
    private void addEdge( int weight, Node prevNode, Node followNode ) {

        final Edge edge = new Edge( weight, prevNode, followNode );
        edge.setStrokeWidth( 2 );

        rootPane.getChildren().add( edge );
        rootPane.getChildren().add( edge.getText() );
        rootPane.getChildren().add( edge.getEndPoint() );
    }

    /**
     * Recursive Method that implements the actual dijkstra algorithm.
     *
     * @param outgoingNode the next outgoing node
     */
    private void doDijkstra( Node outgoingNode ) {

        // If not already processed as outgoing node
        if( outgoingNodes.contains( outgoingNode ) ) {
            return;
        }

        // Add new outgoing node and highlight
        outgoingNodes.add( outgoingNode );
        setCurrentEdgeAndNode( null, null );
        highlightSpecialNodes( outgoingNode );

        waitForNextStep();

        // Iterate over outgoing edges and update neighbour's weight
        // if lower than old weight
        for( Edge edge : outgoingNode.getOutgoingEdges() ) {

            final Node neighbourNode = edge.getFollowNode();
            if( outgoingNode.getWeight() + edge.getWeight() < neighbourNode.getWeight() ) {
                neighbourNode.setWeight( outgoingNode.getWeight() + edge.getWeight() );
            }

            setCurrentEdgeAndNode( edge, neighbourNode );
            waitForNextStep();
        }

        // Iterate over edges corresponding to the order of weights and
        // take as next outgoing node
        for( Edge edge : outgoingNode.getOutgoingEdges() ) {
            doDijkstra( edge.getFollowNode() );
        }
    }

    /**
     * Method that blocks as long as {@link #execNextStep} is {@code false}.
     */
    private void waitForNextStep() {
        while( ! execNextStep ) {
            try {
                Thread.sleep( 5 );
            }
            catch( InterruptedException e ) {
                throw new IllegalThreadStateException();
            }
        }
        // Reset flag because just one step executed at once
        execNextStep = false;
    }

    /**
     * Highlight all special nodes with different colors
     *
     * @param outgoingNode the current handled outgoing node
     */
    private void highlightSpecialNodes( Node outgoingNode ) {
        for( Node node : outgoingNodes ) {
            node.setStroke( Paint.valueOf( "Gold" ) );
        }
        outgoingNode.setStroke( Paint.valueOf( "blue" ) );
    }

    /**
     * Set edge and node that are currently checked and which will be
     * highlighted in green after method invocation.
     *
     * @param edge current edge
     * @param node current nod
     */
    private void setCurrentEdgeAndNode( Edge edge, Node node ) {
        if( currentEdge != null ) {
            currentEdge.setStroke( Paint.valueOf( "black" ) );
        }
        if( currentNode != null ) {
            currentNode.setStroke( Paint.valueOf( "black" ) );
        }

        currentEdge = edge;
        currentNode = node;

        if( currentEdge != null ) {
            currentEdge.setStroke( Paint.valueOf( "green" ) );
        }
        if( currentNode != null ) {
            currentNode.setStroke( Paint.valueOf( "green" ) );
        }
    }
}
