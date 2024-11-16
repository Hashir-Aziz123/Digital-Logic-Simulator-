package org.example.project_oop;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;


public class Wire extends Line {

    private double cord_x1;
    private double cord_y1;
    private double cord_x2;
    private double cord_y2;
    private Connection input_connection;
    private Connection output_connection;

    Wire(MouseEvent event, Connection input_connection, AnchorPane placement_area)
    {
        if(input_connection.getIsOutputConnection() )
        {
            // assigning
            this.input_connection = input_connection;

            System.out.println("wire created");

            // drawing the wire
            drawWire(this, input_connection, placement_area);
        }
        else
        {
            System.out.println("Wires can only be drawn from output connections");
        }
    }

    private void drawWire(Wire wire, Connection input_connection, AnchorPane placement_area)
    {
//        // initializing wire coordinates
        this.cord_x1 = input_connection.getTranslateX() + input_connection.getStartX();
        this.cord_y1 = input_connection.getTranslateY() + input_connection.getStartY();

        setStartX(cord_x1);
        setStartY(cord_y1);
        setEndX(cord_x1);
        setEndY(cord_y1);
        setStroke(Color.BLACK);
        setStrokeWidth(4.5);

        // Add the wire to the placement area
        placement_area.getChildren().add(this);

        //Creating a tool tip to delete the wire if needed
        Tooltip tooltip = new Tooltip("");

        tooltip.setShowDelay(Duration.seconds(0.6));
        tooltip.setAutoHide(false);

        //adding button to tooltip
        addingDeleteOption(placement_area, tooltip);

        input_connection.setOnMouseDragged(event2 ->
        {
            //adjusting the wire with the mouse
            this.cord_x2 = (event2.getX() + input_connection.getTranslateX()  );
            this.cord_y2 = (event2.getY() + input_connection.getTranslateY()  );

            setEndX(cord_x2);
            setEndY(cord_y2);

            input_connection.setOnMouseReleased(event3 ->
            {
                System.out.println("Mouse drag released");
                // collision logic
                for (Node connection : placement_area.getChildren())
                    if (connection instanceof Connection)
                    {
                        if (connection.getBoundsInParent().intersects(this.getBoundsInParent()))
                        {
                            System.out.println("Collision detected");
                            Connection output_connection = (Connection) connection;
                            if (!(output_connection.getIsOutputConnection()) && !(output_connection.getIsConnected() )
                                    && ( input_connection.getConnectedGate() != output_connection.getConnectedGate() )  )
                            {
                                //connection established and output_connection found
                                System.out.println("Connection established");
                                this.output_connection = output_connection;

                                // modify to show connection has been established and store the wire in the respective connections
                                input_connection.setIsConnected(true);
                                output_connection.setIsConnected(true);
                                input_connection.setConnectingWire(this);
                                output_connection.setConnectingWire(this);

                                //transferring the value to the output_connection
                                output_connection.getBoolValue().set(input_connection.getBoolValue().get());

                                // tests
                                System.out.println("You have transferred the value :" + output_connection.getBoolValue().get());
                            } else {
                                System.out.println("Couldn't connect");
                            }
                        }

                    }
                if ( this.output_connection==null)
                    placement_area.getChildren().remove(this);

            });
        });

    }

    private void addingDeleteOption(AnchorPane placement_area, Tooltip tooltip) {
        Button deleteButton = new Button("Delete");
        deleteButton.setFocusTraversable(true);
        deleteButton.setOnAction( event1 ->
        {
            deleteWire(this , placement_area);
        });
        tooltip.setGraphic(deleteButton);
        Tooltip.install(this, tooltip);
    }

    public Connection getOutputConnection() {
        return output_connection;
    }

    public Connection getInput_connection() {
        return input_connection;
    }

    public void deleteWire(Wire wire , AnchorPane placement_area) {
        placement_area.getChildren().remove(wire);
        wire.getInput_connection().setIsConnected(false);
        wire.getOutputConnection().setIsConnected(false);
        wire.getInput_connection().setConnectingWire(null);
        wire.getOutputConnection().setConnectingWire(null);
        wire.getOutputConnection().getBoolValue().set(false); // Reset the boolean value
        System.out.println("Wire deleted");
    }
}


