package org.example.project_oop;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class AndGate extends Gate
{

    AndGate(Connection input_line1 , Connection input_line2, Connection output_line)
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

    public static void drawAndGate(MouseEvent event , AnchorPane placement_area)
    {

        // getting cords
        double x_cord = event.getX();
        double y_cord = event.getY();

        // creating lines
        Line vertical_line = new Line(x_cord, y_cord - 40, x_cord , y_cord +40);
        vertical_line.setStroke(Color.BLACK);
        vertical_line.setStrokeWidth(4);
        QuadCurve curved_line = new QuadCurve( x_cord , y_cord -40 , x_cord + 85  , y_cord , x_cord  , y_cord+ 40); // starting point , control points , end points
        curved_line.setStroke(Color.BLACK);
        curved_line.setFill(null);
        curved_line.setStrokeWidth(4);

        //creating input/output lines
        Connection input_line1 = new Connection(x_cord-8,y_cord+8,x_cord-2,y_cord+8, placement_area,false);
        input_line1.setStroke(Color.BLACK);
        input_line1.setStrokeWidth(7);
        Connection input_line2 = new Connection(x_cord-8,y_cord-8,x_cord-2,y_cord-8, placement_area,false);
        input_line2.setStroke(Color.BLACK);
        input_line2.setStrokeWidth(7);
        Connection output_line = new Connection(x_cord+45, y_cord, x_cord + 45 + 8 , y_cord, placement_area,false);
        output_line.setStroke(Color.BLACK);
        output_line.setStrokeWidth(7);

        //initializing the gate
        new AndGate( input_line1 , input_line2, output_line );

        // Check for collisions with existing gates
        boolean canDraw = isCanDraw(placement_area, vertical_line, curved_line, input_line1, input_line2, output_line);

        // Add the gate components if no collision
        if (canDraw)
        {
            placement_area.getChildren().addAll(vertical_line , curved_line, input_line1, input_line2, output_line);
        } else
        {
            System.out.println("Can not place And gate on another and gate");
        }

        // adding event handling for dragging the gate around
        vertical_line.setOnMousePressed( e -> { dragGate( vertical_line , curved_line, input_line1 ,input_line2 ,output_line , e ,  placement_area ); });
        curved_line.setOnMousePressed( e -> { dragGate  ( vertical_line , curved_line, input_line1 ,input_line2 ,output_line , e , placement_area ); });

        Tooltip tooltip = new Tooltip("Delete Gate");
        tooltip.setShowDelay(Duration.seconds(0.3));
        tooltip.setAutoHide(true);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            deleteGate(vertical_line , curved_line,input_line1 , input_line2 , output_line, placement_area);
        });

        tooltip.setGraphic(deleteButton);

        // Install the tooltip on multiple gate components
        Tooltip.install(vertical_line, tooltip);
        Tooltip.install(curved_line, tooltip);

    }

    private static boolean isCanDraw(AnchorPane placement_area, Line vertical_line, QuadCurve curved_line, Connection input_line1, Connection input_line2, Connection output_line) {
        boolean canDraw = true;
        for (Node gate : placement_area.getChildren()) {
            if (gate instanceof QuadCurve || gate instanceof Line || gate instanceof Circle || gate instanceof Rectangle)
            {
                if (gate.getBoundsInParent().intersects(vertical_line.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(curved_line.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(input_line1.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(input_line2.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(output_line.getBoundsInParent()))
                {
                    canDraw = false;

                    break;
                }
            }
        }
        return canDraw;
    }


    public void simulateGate()
    {
        System.out.println("Simulating and gate");
        this.getOutputConnection().getBoolValue().set(
                (this.getInputConnection1().getBoolValue().get() ) && (this.getInputConnection2().getBoolValue().get() ));
    }


    public static void dragGate( Line vertical_line , QuadCurve curved_line , Connection input_line1 ,
                                 Connection input_line2 , Connection output_line , MouseEvent event1 , AnchorPane placementArea )
    {
        Node cause ;
        double orgX = event1.getSceneX();
        double orgY = event1.getSceneY();

        if ( event1.getSource() == vertical_line ) {
            cause = vertical_line;
            System.out.print("The cause is" + cause);
        }
        else
        {
            cause = curved_line;
            System.out.print("The cause is" + cause);
        }

        double orgTranslateXVrtLine = vertical_line.getTranslateX();
        double orgTranslateYVrtLine = vertical_line.getTranslateY();
        double orgTranslateXCurve   = curved_line.getTranslateX();
        double orgTranslateYCurve   = curved_line.getTranslateY();
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
                    vertical_line.setTranslateX( orgTranslateXVrtLine + event2.getSceneX() - orgX);
                    vertical_line.setTranslateY( orgTranslateYVrtLine + event2.getSceneY() - orgY);

                    curved_line.setTranslateX( orgTranslateXCurve + event2.getSceneX() - orgX);
                    curved_line.setTranslateY( orgTranslateYCurve + event2.getSceneY() - orgY);

                    input_line1.setTranslateX( orgTranslateXInp1+ event2.getSceneX() - orgX);
                    input_line1.setTranslateY( orgTranslateYInp1+ event2.getSceneY() - orgY);

                    input_line2.setTranslateX( orgTranslateXInp2+ event2.getSceneX() - orgX);
                    input_line2.setTranslateY( orgTranslateYInp2+ event2.getSceneY() - orgY);


                    output_line.setTranslateX( orgTranslateXOut + event2.getSceneX() - orgX);
                    output_line.setTranslateY( orgTranslateYOut+ event2.getSceneY() - orgY);
                    

                }
        );
    }

    public static void deleteGate(Line vertical_line , QuadCurve curved_line , Connection input_line1, Connection input_line2 ,
                                  Connection output_line, AnchorPane placement_area)
    {
        if ( input_line1.getIsConnected() )
            input_line1.getConnectingWire().deleteWire( input_line1.getConnectingWire(), placement_area);
        if ( input_line2.getIsConnected() )
            input_line2.getConnectingWire().deleteWire( input_line2.getConnectingWire(), placement_area);
        if( output_line.getIsConnected() )
            output_line.getConnectingWire().deleteWire( output_line.getConnectingWire(), placement_area);
        placement_area.getChildren().removeAll(vertical_line , curved_line, input_line1 , input_line2 , output_line);
        System.out.println("Gate deleted");
    }

}





