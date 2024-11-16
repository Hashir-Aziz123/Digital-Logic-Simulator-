package org.example.project_oop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class Gui {

        @FXML
        private AnchorPane placement_area;


        @FXML
        void drawAndGate(MouseEvent event1) {
                placement_area.setOnMouseClicked( (MouseEvent event2 ) ->
                {   AndGate.drawAndGate( event2 , placement_area ); } ) ;
        }



        @FXML
        void drawLogicProbe(MouseEvent event) {
                placement_area.setOnMouseClicked((MouseEvent event2) ->
                { LogicProbe.drawLogicProbe(event2, placement_area); } );

        }

        @FXML
        void drawLogicToggle(MouseEvent event) {
                placement_area.setOnMouseClicked((MouseEvent event2) ->
                { LogicToggle.drawLogicToggle(event2, placement_area); } );
        }

        @FXML
        void drawNandGate(MouseEvent event) {
                placement_area.setOnMouseClicked( (MouseEvent event2 ) ->
                {   NandGate.drawNandGate( event2 , placement_area ); } ) ;

        }

        @FXML
        void drawNorGate(MouseEvent event) {
                placement_area.setOnMouseClicked( (MouseEvent event2 ) ->
                {   NorGate.drawNorGate( event2 , placement_area ); } ) ;

        }

        @FXML
        void drawNotGate(MouseEvent event) {
                        placement_area.setOnMouseClicked( (MouseEvent event2 ) ->
                        {   NotGate.drawNotGate( event2 , placement_area ); } ) ;

        }

        @FXML
        void drawOrGate(MouseEvent event) {
                placement_area.setOnMouseClicked( (MouseEvent event2 ) ->
                {   OrGate.drawOrGate( event2 , placement_area ); } ) ;
        }

        @FXML
        void drawTriInputAndGate(MouseEvent event) {
                placement_area.setOnMouseClicked( (MouseEvent event2 ) ->
                {   Tri_InputAndGate.drawTri_InputAndGate( event2 , placement_area ); } ) ;
        }

        @FXML
        void drawTriInputOrGate(MouseEvent event) {
                placement_area.setOnMouseClicked( (MouseEvent event2 ) ->
                {   Tri_InputOrGate.drawTri_InputOrGate( event2 , placement_area ); } ) ;
        }

        @FXML
        void drawXnorGate(MouseEvent event) {
                placement_area.setOnMouseClicked( (MouseEvent event2 ) ->
                {   XnorGate.drawXnorGate( event2 , placement_area ); } ) ;

        }

        @FXML
        void drawXorGate(MouseEvent event) {
                placement_area.setOnMouseClicked((MouseEvent event2) ->
                { XorGate.drawXorGate(event2, placement_area); } );
        }

        @FXML
        void DisplayHelpWindow(ActionEvent event) {
                Utility.displayHelpWindow();
        }

        @FXML
        void TakeScreenshot(ActionEvent event) {
                Utility.takeScreenshot();
        }






}
