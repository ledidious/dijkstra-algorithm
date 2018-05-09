package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class Main extends Application {

    private Pane rootPane;

    private static final String STRING_INFINITE = "\u221E";

    private List<Node> nodes;

    private List<Edge> edges;

    private PriorityQueue<Node> priorityQueue;

    private Stack<Node> outgoingNodes;

    /**
     * Die Knoten, die bereits bearbeitet wurden und von denen keine ausgehenden Kanten verlaufen.
     */
    private Set<Node> impassedNodes;

    public Main() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        priorityQueue = new PriorityQueue<>( Comparator.comparing( Node :: getWeight ) );

        outgoingNodes = new Stack<>();
        impassedNodes = new HashSet<>();
    }

    @Override
    public void start( Stage primaryStage ) {

        // Prepare

        rootPane = new Pane();
        primaryStage.setTitle( "Hello World" );
        primaryStage.setScene( new Scene( rootPane, 500, 500 ) );

        final Node nodeS = addNode( "S", 20, 100 );
        Node.setAsStartNode( nodeS );

        final Node nodeU = addNode( "U", 100, 20 );
        final Node nodeX = addNode( "X", 140, 120 );
        final Node nodeV = addNode( "V", 200, 20 );
        Node.setAsTargetNode( nodeV );
        final Node nodeY = addNode( "Y", 240, 120 );

        addEdge( 5, nodeS, nodeX );
        addEdge( 10, nodeS, nodeU );
        addEdge( 3, nodeX, nodeU );
        addEdge( 1, nodeU, nodeV );
        addEdge( 2, nodeX, nodeY );
        addEdge( 6, nodeV, nodeY );

        primaryStage.show();

        // Implementation
        priorityQueue.addAll( nodes );

        doDijkstra( Node.startNode, 0 );
    }

    private void doDijkstra( Node outgoingNode, int recursion ) {
        Edge minEdge = null;

        for( Node neighbourNode : outgoingNode.getNeighbourNodes() ) {
            final Edge edge = outgoingNode.getEdgeToNode( neighbourNode );

            neighbourNode.setWeight( outgoingNode.getWeight() + edge.getWeight() );

            if( minEdge == null ) {
                minEdge = edge;
            }
            else {
                if( edge.getWeight() < minEdge.getWeight() ) {
                    minEdge = edge;
                }
            }
        }

        if( minEdge == null ) {
            outgoingNodes.pop();
            impassedNodes.add( outgoingNode );
        }
        else {
            final Node newOutgoingNode = minEdge.getFollowNode();
            if( newOutgoingNode == null ) {
                outgoingNodes.pop();
                impassedNodes.add( outgoingNode );
            }
            else {
                outgoingNodes.push( newOutgoingNode );
                doDijkstra( newOutgoingNode, recursion + 1 );
                return;
            }
        }

        outgoingNode = outgoingNodes.peek();
        for( Edge edge : outgoingNode.getOutgoingEdges() ) {
            if( ! impassedNodes.contains( edge.getFollowNode() ) ) {
                doDijkstra( edge.getFollowNode(), recursion + 1 );
            }
        }
    }

    private Node addNode( String id, int x, int y ) {

        final Node node = new Node( id );
        node.setX( x );
        node.setY( y );

        rootPane.getChildren().add( node );
        rootPane.getChildren().add( node.getText() );

        nodes.add( node );

        return node;
    }

    private void addEdge( int weight, Node prevNode, Node followNode ) {

        final Edge edge = new Edge( weight, prevNode, followNode );

        rootPane.getChildren().add( edge );
        rootPane.getChildren().add( edge.getText() );

        edges.add( edge );
    }

    public static void main( String[] args ) {
        launch( args );
    }
}
