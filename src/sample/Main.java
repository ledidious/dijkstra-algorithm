package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Main extends Application {

    // Attributes
    // ======================================================

    /**
     * The root pane containing all other gui elements.
     */
    private Pane rootPane;

    private Stack<Node> outgoingNodes = new Stack<>();

    private boolean waitForNextStep = false;

    private Edge currentEdge = null;
    private Node currentNode = null;

    /**
     * Die Knoten, die bereits bearbeitet wurden und von denen keine ausgehenden Kanten verlaufen.
     */
    private Set<Node> deadlockNodes = new HashSet<>();

    // Static methods
    // ======================================================

    public static void main( String[] args ) {
        launch( args );
    }

    // Object methods
    // ======================================================

    @Override
    public void start( Stage primaryStage ) {

        rootPane = new Pane();
        primaryStage.setTitle( "Dijkstra Algorithmus" );
        primaryStage.setScene( new Scene( rootPane, 500, 500 ) );

        prepareGraph();
        primaryStage.show();

        final Button nextButton = new Button( "Weiter" );
        nextButton.setOnMouseClicked( event -> waitForNextStep = true );
        nextButton.setLayoutX( 20 );
        nextButton.setLayoutY( 300 );
        nextButton.setDefaultButton( true );
        rootPane.getChildren().add( nextButton );

        final Button exitButton = new Button( "Beenden" );
        exitButton.setLayoutX( 100 );
        exitButton.setLayoutY( 300 );
        exitButton.setCancelButton( true );
        exitButton.setOnMouseClicked( ( event ) -> System.exit( 0 ) );
        exitButton.setVisible( false );
        rootPane.getChildren().add( exitButton );

        final Thread thread = new Thread( () -> {
            doDijkstra( Node.startNode, 0 );
            exitButton.setVisible( true );
            nextButton.setVisible( false );
        } );
        thread.setDaemon( true );
        thread.start();
    }

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

    private void addEdge( int weight, Node prevNode, Node followNode ) {

        final Edge edge = new Edge( weight, prevNode, followNode );
        edge.setStrokeWidth( 2 );

        rootPane.getChildren().add( edge );
        rootPane.getChildren().add( edge.getText() );
        rootPane.getChildren().add( edge.getEndPoint() );
    }

    private void doDijkstra( Node outgoingNode, int recursion ) {

        outgoingNodes.push( outgoingNode );
        setCurrentEdgeAndNode( null, null );
        highlightSpecialNodes();

        waitForNextStep();

        Edge minEdge = null;
        for( Edge edge : outgoingNode.getOutgoingEdges() ) {

            final Node neighbourNode = edge.getFollowNode();
            if( outgoingNode.getWeight() + edge.getWeight() < neighbourNode.getWeight() ) {
                neighbourNode.setWeight( outgoingNode.getWeight() + edge.getWeight() );
            }

            setCurrentEdgeAndNode( edge, neighbourNode );

            if( minEdge == null ) {
                minEdge = edge;
            }
            else {
                if( edge.getWeight() < minEdge.getWeight() ) {
                    minEdge = edge;
                }
            }
            waitForNextStep();
        }

        if( minEdge == null ) {
            outgoingNodes.pop();
            deadlockNodes.add( outgoingNode );
            highlightSpecialNodes();
        }
        else {
            final Node newOutgoingNode = minEdge.getFollowNode();
            if( newOutgoingNode == null ) {
                outgoingNodes.pop();
                deadlockNodes.add( outgoingNode );
                highlightSpecialNodes();
            }
            else {
                doDijkstra( newOutgoingNode, recursion + 1 );
            }
        }

        outgoingNode = outgoingNodes.peek();
        for( Edge edge : outgoingNode.getOutgoingEdges() ) {
            if( ! deadlockNodes.contains( edge.getFollowNode() ) ) {
                doDijkstra( edge.getFollowNode(), recursion + 1 );
            }
        }
    }

    private void waitForNextStep() {
        while( ! waitForNextStep ) {
            try {
                Thread.sleep( 5 );
            }
            catch( InterruptedException e ) {
                throw new IllegalThreadStateException();
            }
        }
        waitForNextStep = false;
    }

    private void highlightSpecialNodes() {
        for( Node node : outgoingNodes ) {
            node.setStroke( Paint.valueOf( "black" ) );
        }
        if( ! outgoingNodes.isEmpty() ) {
            outgoingNodes.peek().setStroke( Paint.valueOf( "blue" ) );
        }

        for( Node node : deadlockNodes ) {
            node.setStroke( Paint.valueOf( "red" ) );
        }
    }

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
