package org.example.project_oop;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Line;

import java.util.Collection;

public abstract class Gate extends Group {



    protected Connection input_connection1;
    protected Connection input_connection2;
    protected Connection input_connection3;
    protected Connection output_connection;



  public abstract void simulateGate();



    public Connection getInputConnection1()
    {
        return input_connection1;
    }

    public Connection getInputConnection2()
    {
        return input_connection2;
    }

    public Connection getInputConnection3() {
        return input_connection3;
    }

    public Connection getOutputConnection() {
        return output_connection;
    }

}


