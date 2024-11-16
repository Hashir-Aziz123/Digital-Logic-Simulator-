package org.example.project_oop;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class OrGate extends Gate
{
    OrGate(Connection input_line1 , Connection input_line2, Connection output_line)
    {
        // initialization
        this.input_connection1 = input_line1;
        this.input_connection2 = input_line2;
        this.output_connection = output_line;

        // setting the gate values for connections
        this.input_connection1.setCharacteristics(this,false);
        this.input_connection2.setCharacteristics(this,false);
        this.output_connection.setCharacteristics(this,true);
    }

    public static void drawOrGate(MouseEvent event , AnchorPane placement_area)
    {
        //getting cords
        double x_cord = event.getX();
        double y_cord = event.getY();

        //drawing the line
        QuadCurve curved_line1 = new QuadCurve(x_cord-20, y_cord - 40, x_cord, y_cord, x_cord-20, y_cord +40);
        curved_line1.setStroke(Color.BLACK);
        curved_line1.setFill(null);
        curved_line1.setStrokeWidth(4);
        QuadCurve curved_line2 = new QuadCurve( x_cord-20 , y_cord -40 , x_cord+85, y_cord, x_cord-20  , y_cord+ 40); // starting point , control points , end points
        curved_line2.setStroke(Color.BLACK);
        curved_line2.setFill(null);
        curved_line2.setStrokeWidth(4);

        //creating input/output lines
        Connection input_line1 = new Connection(x_cord-20,y_cord+8,x_cord-12,y_cord+8 , placement_area , false);
        input_line1.setStroke(Color.BLACK);
        input_line1.setStrokeWidth(7);
        Connection input_line2 = new Connection(x_cord-20,y_cord-8,x_cord-12,y_cord-8 , placement_area, false);
        input_line2.setStroke(Color.BLACK);
        input_line2.setStrokeWidth(7);
        Connection output_line = new Connection(x_cord+35, y_cord, x_cord + 35 + 8 , y_cord, placement_area , false);
        output_line.setStroke(Color.BLACK);
        output_line.setStrokeWidth(7);

        //initialzing the or gate
         new OrGate( input_line1 , input_line2, output_line );


        boolean canDraw = isCanDraw(placement_area, curved_line1, curved_line2, input_line1, input_line2, output_line);

        // Add the gate components if no collision
        if (canDraw) {
            placement_area.getChildren().addAll(curved_line1 , curved_line2, input_line1, input_line2, output_line);
        } else {
            System.out.println("Can not place or gate on another or gate");
        }

        // adding event handling for dragging the gate around
        curved_line1.setOnMousePressed( e -> { dragGate( curved_line1 , curved_line2, input_line1 ,input_line2 ,output_line , e , placement_area); });
        curved_line2.setOnMousePressed( e -> { dragGate  ( curved_line1 , curved_line2, input_line1 ,input_line2 ,output_line , e ,placement_area ); });

        //Tooltip after deletion
        Tooltip tooltip = new Tooltip("Delete Gate");
        tooltip.setShowDelay(Duration.seconds(0.3));
        tooltip.setAutoHide(true);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {

            deleteGate(curved_line1 , curved_line2,input_line1 , input_line2 , output_line, placement_area);
        });

        tooltip.setGraphic(deleteButton);

        // Install the tooltip on multiple gate components
        Tooltip.install(curved_line1, tooltip);
        Tooltip.install(curved_line2, tooltip);
    }

    private static boolean isCanDraw(AnchorPane placement_area, QuadCurve curved_line1, QuadCurve curved_line2, Connection input_line1, Connection input_line2, Connection output_line) {
        boolean canDraw = true;
        for (Node gate : placement_area.getChildren()) {
            if ( gate instanceof QuadCurve || gate instanceof Line || gate instanceof Circle || gate instanceof Rectangle)
            {
                if (gate.getBoundsInParent().intersects( curved_line1.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(curved_line2.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(input_line1.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(input_line2.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(output_line.getBoundsInParent())
                        )
                {
                    canDraw = false;
                    break;
                }
            }
        }
        return canDraw;
    }


    @Override
    public void simulateGate() {
        System.out.println("Simulating or gate");
        this.getOutputConnection().getBoolValue().set(
                (this.getInputConnection1().getBoolValue().get() ) || (this.getInputConnection2().getBoolValue().get() ));
    }

    public static void dragGate( QuadCurve curved_line1 , QuadCurve curved_line2 , Connection input_line1 ,
                                 Connection input_line2 , Connection output_line , MouseEvent event1 , AnchorPane placementArea )
    {
        Node cause ;
        double orgX = event1.getSceneX();
        double orgY = event1.getSceneY();

        if ( event1.getSource() == curved_line1 ) {
            cause = curved_line1;
            System.out.print("The cause is" + cause);
        }
        else
        {
            cause = curved_line2;
            System.out.print("The cause is" + cause);
        }

        double orgTranslateXVrtCurve1 = curved_line1.getTranslateX();
        double orgTranslateYVrtCurve1 = curved_line1.getTranslateY();
        double orgTranslateXCurve2   = curved_line2.getTranslateX();
        double orgTranslateYCurve2   = curved_line2.getTranslateY();
        double orgTranslateXInp1    = input_line1.getTranslateX();
        double orgTranslateYInp1    = input_line1.getTranslateY();
        double orgTranslateXInp2    = input_line2.getTranslateX();
        double orgTranslateYInp2    = input_line2.getTranslateY();
        double orgTranslateXOut     = output_line.getTranslateX();
        double orgTranslateYOut     = output_line.getTranslateY();

        if ( input_line1.getIsConnected() )
            input_line1.getConnectingWire().deleteWire( input_line1.getConnectingWire(), placementArea);
        if ( input_line2.getIsConnected() )
            input_line2.getConnectingWire().deleteWire( input_line2.getConnectingWire(), placementArea);
        if( output_line.getIsConnected() )
            output_line.getConnectingWire().deleteWire( output_line.getConnectingWire(), placementArea);

        cause.setOnMouseDragged( event2 ->
                {
                    curved_line1.setTranslateX( orgTranslateXVrtCurve1 + event2.getSceneX() - orgX);
                    curved_line1.setTranslateY( orgTranslateYVrtCurve1 + event2.getSceneY() - orgY);

                    curved_line2.setTranslateX( orgTranslateXCurve2 + event2.getSceneX() - orgX);
                    curved_line2.setTranslateY( orgTranslateYCurve2 + event2.getSceneY() - orgY);

                    input_line1.setTranslateX( orgTranslateXInp1+ event2.getSceneX() - orgX);
                    input_line1.setTranslateY( orgTranslateYInp1+ event2.getSceneY() - orgY);

                    input_line1.updatePosition( input_line1.getStartX() , input_line1.getStartY() ,
                            input_line1.getEndX() , input_line1.getEndY() );

                    input_line2.setTranslateX( orgTranslateXInp2+ event2.getSceneX() - orgX);
                    input_line2.setTranslateY( orgTranslateYInp2+ event2.getSceneY() - orgY);

                    input_line2.updatePosition( input_line2.getStartX() , input_line2.getStartY() ,
                            input_line2.getEndX() , input_line2.getEndY() );

                    output_line.setTranslateX( orgTranslateXOut + event2.getSceneX() - orgX);
                    output_line.setTranslateY( orgTranslateYOut+ event2.getSceneY() - orgY);

                    output_line.updatePosition( output_line.getStartX() , output_line.getStartY() ,
                            output_line.getEndX() , output_line.getEndY() );

                }
        );


    }
    public static void deleteGate(QuadCurve curved_line1, QuadCurve curved_line2 , Connection input_line1, Connection input_line2 ,
                                  Connection output_line, AnchorPane placement_area)
    {
        if ( input_line1.getIsConnected() )
        {
            System.out.println(input_line1.getConnectingWire());
            input_line1.getConnectingWire().deleteWire(input_line1.getConnectingWire(), placement_area);
        }
        if ( input_line2.getIsConnected() )
        {
            System.out.println(input_line2.getConnectingWire());
            input_line2.getConnectingWire().deleteWire(input_line2.getConnectingWire(), placement_area);
        }
        if( output_line.getIsConnected() )
        {
            System.out.println(output_line.getConnectingWire());
            output_line.getConnectingWire().deleteWire(output_line.getConnectingWire(), placement_area);
        }
        placement_area.getChildren().removeAll( curved_line1 , curved_line2, input_line1 , input_line2 , output_line);
        System.out.println("Gate deleted");
    }

}
