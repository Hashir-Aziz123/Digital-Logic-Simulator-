package org.example.project_oop;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class Connection extends Line
{
    private Gate connected_gate;

    private Wire connectingWire;

    private Boolean isConnected;
    private Boolean isOutputConnection;
    private SimpleBooleanProperty boolValue;
    public Connection(double x, double y, double width, double height, AnchorPane placement_area , boolean bool )
    {
        super(x, y, width, height);

        // basic initialization and setting up event handling
        this.isConnected = false;

        // setting up a action listener with a bool value
        this.boolValue = new SimpleBooleanProperty(bool);
        this.boolValue.addListener( (observable,oldValue,newValue) ->
                handleBoolValueChange(this) );

        // drawing the wire and transferring data
        this.setOnMousePressed( event ->
                {
                    if( event.getSource() == this)
                    {
                        System.out.println("Drawing Wire");
                        drawWire(event, this , placement_area);
                    }
                }
        );
    }





    // Override the intersects method to check for collision // crucial for program functiom
    @Override
    public boolean intersects(Bounds bounds) {
        return this.getBoundsInParent().intersects(bounds);
    }


    // utility functions
    public Boolean getIsOutputConnection()
    {
        return isOutputConnection;
    }

    public Boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(Boolean isConnected)
    {
        this.isConnected = isConnected;
    }

    public Gate getConnectedGate()
    {
        return connected_gate;
    }

    public void setConnectingWire(Wire connectingWire)
    {
        this.connectingWire = connectingWire;
    }

    public Wire getConnectingWire() {
        return connectingWire;
    }


    public SimpleBooleanProperty getBoolValue()
    {
        return boolValue;
    }


    //imp functions

    public void setCharacteristics(Gate connected_gate , Boolean isOutputConnection)
    {
        this.connected_gate = connected_gate;
        this.isOutputConnection = isOutputConnection;

    }

    public void drawWire(MouseEvent event , Connection connection , AnchorPane placement_area)
    {
        new Wire(event, connection, placement_area);
    }

    private void handleBoolValueChange(Connection connection)
    {
        System.out.println("Bool value changed");
        if (connection.getIsOutputConnection())
        {
            if (connection.getIsConnected()) {
                connection.getConnectingWire().getOutputConnection().getBoolValue().set(connection.getBoolValue().get());
                System.out.println("Value set as same as output connection " + connection.getConnectingWire().getOutputConnection().getBoolValue().get());

                connection.getConnectingWire().getOutputConnection().getConnectedGate().simulateGate();
                System.out.println("Simulating gate due to bool change");
            }
        } else {
            connection.getConnectedGate().simulateGate();
            System.out.println("Simulating gate due to bool change");
        }

        // try simulating gate on creation

    }

    public void updatePosition( double x1, double y1, double x2, double y2)
    {
        this.setStartX(x1);
        this.setStartY(y1);
        this.setEndX(x2);
        this.setEndY(y2);
    }


}
