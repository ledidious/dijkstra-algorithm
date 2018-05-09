package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Pane rootPane;

    private static final String STRING_INFINITE = "\u221E";

    private List<Node> nodes;

    private List<Edge> edges;

    public Main() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    @Override
    public void start( Stage primaryStage ) {

        rootPane = new Pane();
        primaryStage.setTitle( "Hello World" );
        primaryStage.setScene( new Scene( rootPane, 500, 500 ) );

        final Node nodeS = addNodeX( "S", 20, 100 );
        Node.setAsStartNode( nodeS );

        final Node nodeU = addNodeX( "U", 100, 20 );
        final Node nodeX = addNodeX( "X", 140, 120 );
        final Node nodeV = addNodeX( "V", 200, 20 );
        Node.setAsTargetNode( nodeV );
        final Node nodeY = addNodeX( "Y", 240, 120 );

        addEdge( 5, nodeS, nodeX );
        addEdge( 10, nodeS, nodeU );
        addEdge( 3, nodeX, nodeU );
        addEdge( 1, nodeU, nodeV );
        addEdge( 2, nodeX, nodeY );
        addEdge( 6, nodeV, nodeY );

        //        final Circle nodeS = addNode( 20, 100, "0" );
        //        final Circle nodeU = addNode( 100, 20, STRING_INFINITE );
        //        final Circle nodeX = addNode( 140, 120, STRING_INFINITE );
        //        final Circle nodeV = addNode( 200, 20, STRING_INFINITE );
        //        final Circle nodeY = addNode( 240, 120, STRING_INFINITE );
        //
        //        connectNode( nodeS, nodeX, 5 );
        //        connectNode( nodeS, nodeU, 10 );
        //        connectNode( nodeX, nodeU, 3 );
        //        connectNode( nodeU, nodeV, 1 );
        //        connectNode( nodeX, nodeY, 2 );
        //        connectNode( nodeV, nodeY, 6 );

        primaryStage.show();
    }

    private Node addNodeX( String id, int x, int y ) {

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

    private Circle addNode( int x, int y, String value ) {

        final Circle circle = new Circle( 20 );
        circle.setFill( Paint.valueOf( "white" ) );
        circle.setStroke( Paint.valueOf( "black" ) );
        circle.setLayoutX( x );
        circle.setLayoutY( y );

        final Text text = new Text();
        text.setText( value );
        text.setX( x - 8 );
        text.setY( y + 2 );

        rootPane.getChildren().add( circle );
        rootPane.getChildren().add( text );

        return circle;
    }

    private void connectNode( Circle fromNode, Circle toNode, int value ) {

        final Line line = new Line();

        if( fromNode.getLayoutX() > toNode.getLayoutX() ) {
            final Circle temp = fromNode;
            fromNode = toNode;
            toNode = temp;
        }

        line.setStartX( fromNode.getLayoutX() + fromNode.getRadius() );
        line.setStartY( fromNode.getLayoutY() );

        line.setEndX( toNode.getLayoutX() - toNode.getRadius() );
        line.setEndY( toNode.getLayoutY() );

        final Text text = new Text( Integer.toString( value ) );
        text.setX( line.getStartX() + ( line.getEndX() - line.getStartX() ) / 2 );
        text.setY( line.getStartY() + ( line.getEndY() - line.getStartY() ) / 2 );

        rootPane.getChildren().add( line );
        rootPane.getChildren().add( text );
    }

    public static void main( String[] args ) {
        launch( args );
    }
}
