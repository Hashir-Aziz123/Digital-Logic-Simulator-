package org.example.project_oop;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
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

public class LogicToggle extends Gate
{

    // intializes the logic toggle
    public LogicToggle(Connection output_connection )
    {
        this.output_connection = output_connection;

        // setting the gate values for connections
        this.output_connection.setCharacteristics(this, true);
    }

    // static method that draws the toggle and sets up execution
    public static void drawLogicToggle(MouseEvent event , AnchorPane placement_area)
    {

        // getting cords
        double x_cord = event.getX();
        double y_cord = event.getY();



        // creating lines
        Rectangle toggle = new Rectangle(x_cord-20, y_cord-22, 40, 44); // (x, y, width, height)
        toggle.setFill(Color.BLUE); // Set fill color
        toggle.setStroke(Color.BLACK);
        toggle.setStrokeWidth(5);


        //creating input/output lines
        Connection output_line = new Connection(x_cord+20, y_cord, x_cord + 20 + 8 , y_cord, placement_area,false);
        output_line.setStroke(Color.BLACK);
        output_line.setStrokeWidth(6);

        // initializing the toggle
        LogicToggle lg_toggle = new LogicToggle(output_line);


        //adding event handling
        changeToggleValue(toggle, lg_toggle);
//        handleOutputLine(output_line,placement_area,lg_toggle);

        // Check for collisions with existing gates
        boolean canDraw = isCanDraw(placement_area,toggle, output_line);

        // Add the gate components if no collision
        if (canDraw) {
            placement_area.getChildren().addAll(toggle , output_line);
        } else {
            System.out.println("Can not place a logic toggle on another logic toggle");
        }

        // adding event handling for dragging the gate around
        toggle.setOnMousePressed( e -> { dragGate( toggle , output_line , e , placement_area); });

        //tooltip for deleting the gate
        Tooltip tooltip = new Tooltip("Delete Gate");
        tooltip.setShowDelay(Duration.seconds(0.3));
        tooltip.setAutoHide(true);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            // Implement the deletion logic here
            // For example, you can call a method to delete the gate
            deleteGate(toggle , output_line, placement_area);
        });

        tooltip.setGraphic(deleteButton);

        // Install the tooltip on multiple gate components
        Tooltip.install(toggle, tooltip);
    }

    // colllision logic
    private static boolean isCanDraw(AnchorPane placement_area, Rectangle toggle ,Connection output_line) {
        boolean canDraw = true;
        for (Node gate : placement_area.getChildren()) {
            if (gate instanceof QuadCurve || gate instanceof Line || gate instanceof Circle || gate instanceof Rectangle)
            {
                if (gate.getBoundsInParent().intersects(toggle.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(output_line.getBoundsInParent()))
                {
                    canDraw = false;

                    break;
                }
            }
        }
        return canDraw;
    }

    // changes toggle value and assigns that value to the output_connection
    private static void changeToggleValue(Rectangle toggle, LogicToggle lgToggle)
    {
        toggle.setOnMouseClicked(event ->
        {
            lgToggle.getOutputConnection().getBoolValue().set( ! ( lgToggle.getOutputConnection().getBoolValue().get() ) );
//            lgToggle.getOutputConnection().getBoolValue().set(  ( lgToggle.isOutput().get() ) );
            System.out.println("Toggle value changed to :" + lgToggle.getOutputConnection().getBoolValue() );
            if (lgToggle.getOutputConnection().getBoolValue().get() )
                toggle.setFill(Color.RED);
            else
                toggle.setFill(Color.BLUE);
        } );
    }


    @Override
    public void simulateGate() {
        System.out.println("Logic toggle simulated");
    }

    public static void dragGate( Rectangle probe , Connection output_line  , MouseEvent event1 , AnchorPane placementArea )
    {
        Node cause = probe ;
        double orgX = event1.getSceneX();
        double orgY = event1.getSceneY();


        double orgTranslateXOut    = output_line.getTranslateX();
        double orgTranslateYOut    = output_line.getTranslateY();

        double orgTranslateXPrb     = probe.getTranslateX();
        double orgTranslateYPrb     = probe.getTranslateY();



        cause.setOnMouseDragged( event2 ->
                {
                    probe.setTranslateX( orgTranslateXPrb + event2.getSceneX() - orgX);
                    probe.setTranslateY( orgTranslateYPrb + event2.getSceneY() - orgY);

                    output_line.setTranslateX( orgTranslateXOut+ event2.getSceneX() - orgX);
                    output_line.setTranslateY( orgTranslateYOut+ event2.getSceneY() - orgY);

                    output_line.updatePosition( output_line.getStartX() , output_line.getStartY() ,
                            output_line.getEndX() , output_line.getEndY() );

                    if( output_line.getIsConnected() )
                        output_line.getConnectingWire().deleteWire( output_line.getConnectingWire(), placementArea);

                }
        );
    }

    public static void deleteGate(Rectangle toggle , Connection output_line, AnchorPane placement_area)
    {
        if( output_line.getIsConnected() )
            output_line.getConnectingWire().deleteWire( output_line.getConnectingWire(), placement_area);
        placement_area.getChildren().removeAll(toggle, output_line);
        System.out.println("Gate deleted");
    }

}
