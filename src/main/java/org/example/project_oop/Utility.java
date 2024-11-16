package org.example.project_oop;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.robot.Robot;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;



public class Utility  {

    public static void takeScreenshot()
    {
        System.out.println("Selected screenshot");
        Robot robot = new Robot();
        WritableImage image = robot.getScreenCapture(null,
                Screen.getPrimary().getBounds().getMinX(),
                Screen.getPrimary().getBounds().getMinY(),
                Screen.getPrimary().getBounds().getWidth(),
                Screen.getPrimary().getBounds().getHeight());

        // Create a unique file name using timestamp
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = "screenshot_" + timestamp + ".png";
        File file = new File(fileName);

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            System.out.println("Screenshot saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //pop up

        String message = "Screenshot saved successfully in project directory";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Digital Logic Engine Screenshot Completed:");
        alert.setHeaderText(null);  // Optional: Set a header text or leave it null
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();


        Image icon = new Image(Utility.class.getResourceAsStream("mylogo.png"));
        stage.getIcons().add(icon);
        alert.initOwner(null);  // Optional: You can set the owner window if needed

        alert.getDialogPane().getStylesheets().add(Utility.class.getResource("styles.css").toExternalForm());

        alert.showAndWait();

    }

    public static void displayHelpWindow()
    {
        System.out.println("Selected help");
        String message = "You are using Digital Logic Engine which can be used to simulate the behaviour of basic digital logic gates." +
                "\n(1): Click on a button to select a gate type to place." +
                "\n(2): Delete gates by hovering on top of a gate and pressing enter" +
                "\n(3): You can drag gates and also delete them." +
                "\n(4): Use logic toggle to provide input and logic probe to observe output." +
                "\n(5): Use wires to connect different gates." +
                "\n(6): You can delete wires but can't connect a wire from a gate to the same gate." +
                "\n(7): Gates assume input to be 0 if no toggle is connected";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Digital Logic Engine Guide:");
        alert.setHeaderText("Guide");  // Optional: Set a header text or leave it null
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();


        Image icon = new Image(Utility.class.getResourceAsStream("mylogo.png"));
        stage.getIcons().add(icon);
        alert.initOwner(null);  // Optional: You can set the owner window if needed

        alert.getDialogPane().getStylesheets().add(Utility.class.getResource("styles.css").toExternalForm());

        alert.showAndWait();

    }


}
