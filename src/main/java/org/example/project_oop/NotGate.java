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

public class NotGate extends Gate {



        NotGate(Connection input_line1  , Connection output_line)
        {
            this.input_connection1 = input_line1;
            this.output_connection = output_line;

            // setting the gate values for connections
            this.input_connection1.setCharacteristics(this, false);
            this.output_connection.setCharacteristics(this, true);

        }
        public static void drawNotGate(MouseEvent event , AnchorPane placement_area)
        {
            //getting cord
            double x_cord = event.getX();
            double y_cord = event.getY();

            //drawing the gates
            Line vertical_line1 = new Line(x_cord, y_cord - 40, x_cord , y_cord +40);
            vertical_line1.setStroke(Color.BLACK);
            vertical_line1.setStrokeWidth(4);
            Line diagonal_1 = new Line (x_cord, y_cord + 40, x_cord+45, y_cord);
            diagonal_1.setStroke(Color.BLACK);
            diagonal_1.setStrokeWidth(4);
            Line diagonal_2 = new Line (x_cord , y_cord -40, x_cord +45, y_cord);
            diagonal_2.setStroke(Color.BLACK);
            diagonal_2.setStrokeWidth(4);
            Circle negates = new Circle( x_cord + 45 +4, y_cord , 4 ); // centre, radius
            negates.setStroke(Color.BLACK);
            negates.setStrokeWidth(4);
            negates.setFill(null);

            //creating input/output lines
            Connection input_line = new Connection(x_cord-8,y_cord,x_cord-2,y_cord, placement_area,false);
            input_line.setStroke(Color.BLACK);
            input_line.setStrokeWidth(7);
            Connection output_line = new Connection(x_cord+45+11, y_cord, x_cord + 45 + 19 , y_cord, placement_area,true);
            output_line.setStroke(Color.BLACK);
            output_line.setStrokeWidth(7);

            //initalizing the gate
            new NotGate(input_line ,output_line);



            boolean canDraw = isCanDraw(placement_area, vertical_line1, diagonal_1, diagonal_2, negates, input_line, output_line);

            // Add the gate components if no collision
            if (canDraw) {
                placement_area.getChildren().addAll(vertical_line1,diagonal_1,diagonal_2, negates, input_line, output_line);
            } else {
                System.out.println("Can not place Not gate on another not gate");
            }

            // adding event handling for dragging the gate around
            vertical_line1.setOnMousePressed( e -> { dragGate( vertical_line1 , diagonal_1 , diagonal_2 , negates , input_line ,output_line , e ,  placement_area); });
            diagonal_1.setOnMousePressed( e -> { dragGate( vertical_line1 , diagonal_1 , diagonal_2 , negates , input_line ,output_line , e , placement_area); });
            diagonal_2.setOnMousePressed( e -> { dragGate( vertical_line1 , diagonal_1 , diagonal_2 , negates , input_line ,output_line , e , placement_area); });
            negates.setOnMousePressed( e -> { dragGate( vertical_line1 , diagonal_1 , diagonal_2 , negates , input_line ,output_line , e ,  placement_area); });

            // add tool tip for deletion
            Tooltip tooltip = new Tooltip("Delete Gate");
            tooltip.setShowDelay(Duration.seconds(0.3));
            tooltip.setAutoHide(true);

            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                // Implement the deletion logic here
                // For example, you can call a method to delete the gate
                deleteGate(vertical_line1 , diagonal_1, diagonal_2 , negates,input_line , output_line, placement_area);
            });

            tooltip.setGraphic(deleteButton);

            // Install the tooltip on multiple gate components
            Tooltip.install(vertical_line1, tooltip);
            Tooltip.install(diagonal_1, tooltip);
            Tooltip.install(diagonal_2, tooltip);
            Tooltip.install(negates, tooltip);

        }

    private static boolean isCanDraw(AnchorPane placement_area, Line vertical_line1, Line diagonal_1, Line diagonal_2, Circle Negates, Connection input_line, Connection output_line) {
        boolean canDraw = true;
        for (Node gate : placement_area.getChildren()) {
            if (gate instanceof Line || gate instanceof QuadCurve || gate instanceof Circle || gate instanceof Rectangle) {
                if (gate.getBoundsInParent().intersects(vertical_line1.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(diagonal_1.getBoundsInParent() )
                        || gate.getBoundsInParent().intersects(diagonal_2.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(Negates.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(input_line.getBoundsInParent())
                        || gate.getBoundsInParent().intersects(output_line.getBoundsInParent()))
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
        this.getOutputConnection().getBoolValue().set( ! (this.getInputConnection1().getBoolValue().get() ) );
        System.out.println("Not gating performing function.    Input:" + this.getInputConnection1().getBoolValue().get() +
                "    Output: " + this.getOutputConnection().getBoolValue().get() );
    }

    public static void dragGate( Line vertical_line , Line diagonal1 , Line diagonal2 , Circle negates ,
                                 Connection input_line1 , Connection output_line , MouseEvent event1 , AnchorPane placementArea ) {
        Node cause;
        double orgX = event1.getSceneX();
        double orgY = event1.getSceneY();

        if (event1.getSource() == vertical_line) {
            cause = vertical_line;
            System.out.print("The cause is" + cause);
        } else if (event1.getSource() == diagonal1) {
            cause = diagonal1;
            System.out.print("The cause is" + cause);
        } else if (event1.getSource() == diagonal2) {
            cause = diagonal2;
            System.out.print("The cause is" + cause);
        } else {
            cause = negates;
            System.out.print("The cause is" + cause);
        }

        double orgTranslateXVrtLine = vertical_line.getTranslateX();
        double orgTranslateYVrtLine = vertical_line.getTranslateY();
        double orgTranslateXDiag1 = diagonal1.getTranslateX();
        double orgTranslateYDiag1 = diagonal1.getTranslateY();
        double orgTranslateXDiag2 = diagonal2.getTranslateX();
        double orgTranslateYDiag2 = diagonal2.getTranslateY();
        double orgTranslateXCircle = negates.getTranslateX();
        double orgTranslateYCircle = negates.getTranslateY();
        double orgTranslateXInp1 = input_line1.getTranslateX();
        double orgTranslateYInp1 = input_line1.getTranslateY();
        double orgTranslateXOut = output_line.getTranslateX();
        double orgTranslateYOut = output_line.getTranslateY();

        if ( input_line1.getIsConnected() )
            input_line1.getConnectingWire().deleteWire( input_line1.getConnectingWire(), placementArea);
        if( output_line.getIsConnected() )
            output_line.getConnectingWire().deleteWire( output_line.getConnectingWire(), placementArea);

            cause.setOnMouseDragged( event2 ->
                {
                    vertical_line.setTranslateX( orgTranslateXVrtLine + event2.getSceneX() - orgX);
                    vertical_line.setTranslateY( orgTranslateYVrtLine + event2.getSceneY() - orgY);

                    diagonal1.setTranslateX( orgTranslateXDiag1 + event2.getSceneX() - orgX);
                    diagonal1.setTranslateY( orgTranslateYDiag1 + event2.getSceneY() - orgY);

                    diagonal2.setTranslateX( orgTranslateXDiag2 + event2.getSceneX() - orgX);
                    diagonal2.setTranslateY( orgTranslateYDiag2+ event2.getSceneY() - orgY);

                    negates.setTranslateX( orgTranslateXCircle + event2.getSceneX() - orgX);
                    negates.setTranslateY( orgTranslateYCircle+ event2.getSceneY() - orgY);

                    input_line1.setTranslateX( orgTranslateXInp1+ event2.getSceneX() - orgX);
                    input_line1.setTranslateY( orgTranslateYInp1+ event2.getSceneY() - orgY);

                    input_line1.updatePosition( input_line1.getStartX() , input_line1.getStartY() ,
                                                input_line1.getEndX() , input_line1.getEndY() );

                    output_line.setTranslateX( orgTranslateXOut + event2.getSceneX() - orgX);
                    output_line.setTranslateY( orgTranslateYOut+ event2.getSceneY() - orgY);

                    output_line.updatePosition( output_line.getStartX() , output_line.getStartY() ,
                            output_line.getEndX() , output_line.getEndY() );

                }
            );
    }

    public static void deleteGate(Line vertical_line , Line diagonal1 , Line diagonal2 , Circle negates,  Connection input_line, Connection output_line, AnchorPane placement_area)
    {
        if ( input_line.getIsConnected() )
            input_line.getConnectingWire().deleteWire( input_line.getConnectingWire(), placement_area);
        if( output_line.getIsConnected() )
            output_line.getConnectingWire().deleteWire( output_line.getConnectingWire(), placement_area);
        placement_area.getChildren().removeAll(vertical_line , diagonal1 , diagonal2 ,negates , input_line , output_line);
        System.out.println("Gate deleted");
    }



}
