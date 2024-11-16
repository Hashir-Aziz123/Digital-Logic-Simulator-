package org.example.project_oop;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

public class LogicProbe extends Gate
{

    private Rectangle probe;
    public LogicProbe(Rectangle probe, Connection input_connection1)
    {

        this.input_connection1= input_connection1;
        this.probe = probe;

        // setting the gate values for connections
        this.input_connection1.setCharacteristics(this, false);
    }
    public static void drawLogicProbe(MouseEvent event , AnchorPane placement_area)
    {

        // getting cords
        double x_cord = event.getX();
        double y_cord = event.getY();



        // creating lines
        Rectangle probe = new Rectangle(x_cord-20, y_cord-22, 40, 44); // (x, y, width, height)
        probe.setFill(Color.BLUE); // Set fill color
        probe.setStroke(Color.BLACK);
        probe.setStrokeWidth(5);


        //creating input/output lines
        Connection input_line = new Connection(x_cord-20-14, y_cord, x_cord - 20 - 4  , y_cord, placement_area,false);
        input_line.setStroke(Color.BLACK);
        input_line.setStrokeWidth(6);

         new LogicProbe(probe, input_line);

        // Check for collisions with existing gates
        boolean canDraw = isCanDraw(placement_area,probe, input_line);

        // Add the gate components if no collision
        if (canDraw) {
            placement_area.getChildren().addAll(probe , input_line);
        } else {
            System.out.println("Can not place a logic probe on another logic probe");
        }

        // adding event handling for dragging the gate around
        probe.setOnMousePressed( e -> { dragGate( probe , input_line , e , placement_area); });

        // add tool tip for deletion
        Tooltip tooltip = new Tooltip("Delete Gate");
        tooltip.setShowDelay(Duration.seconds(0.3));
        tooltip.setAutoHide(true);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            deleteGate(probe,input_line , placement_area);
        });

        tooltip.setGraphic(deleteButton);

        // Install the tooltip on multiple gate components
        Tooltip.install(probe, tooltip);


    }

    private static boolean isCanDraw(AnchorPane placement_area, Rectangle probe ,Connection input_line) {
        boolean canDraw = true;
        for (Node gate : placement_area.getChildren()) {
            if (gate instanceof QuadCurve || gate instanceof Line || gate instanceof Circle || gate instanceof Rectangle)
            {
                if (gate.getBoundsInParent().intersects(probe.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(input_line.getBoundsInParent()))
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
        System.out.println("Logic probe simulated");
        probe.setFill( this.input_connection1.getBoolValue().get()  ? Color.RED : Color.BLUE );
    }

    public static void dragGate( Rectangle probe , Connection input_line1  , MouseEvent event1 , AnchorPane placementArea)
    {
        Node cause = probe ;
        double orgX = event1.getSceneX();
        double orgY = event1.getSceneY();


        double orgTranslateXInp1    = input_line1.getTranslateX();
        double orgTranslateYInp1    = input_line1.getTranslateY();

        double orgTranslateXPrb     = probe.getTranslateX();
        double orgTranslateYPrb     = probe.getTranslateY();

        if ( input_line1.getIsConnected() )
            input_line1.getConnectingWire().deleteWire( input_line1.getConnectingWire(), placementArea);

        cause.setOnMouseDragged( event2 ->
                {
                    probe.setTranslateX( orgTranslateXPrb + event2.getSceneX() - orgX);
                    probe.setTranslateY( orgTranslateYPrb + event2.getSceneY() - orgY);

                    input_line1.setTranslateX( orgTranslateXInp1+ event2.getSceneX() - orgX);
                    input_line1.setTranslateY( orgTranslateYInp1+ event2.getSceneY() - orgY);

                    input_line1.updatePosition( input_line1.getStartX() , input_line1.getStartY() ,
                            input_line1.getEndX() , input_line1.getEndY() );

                }
        );
    }

    public static void deleteGate( Rectangle probe, Connection input_line,AnchorPane placement_area)
    {
        if ( input_line.getIsConnected() )
            input_line.getConnectingWire().deleteWire( input_line.getConnectingWire(), placement_area);
        placement_area.getChildren().removeAll( probe , input_line );
        System.out.println("Gate deleted");
    }
}


