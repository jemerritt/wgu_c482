package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Inventory;

import java.util.Objects;

//Javadoc files located in src/javadoc

/** Main Class. Creates the initial javafx stage.
 *
 * FUTURE ENHANCEMENT: Provide option to prevent duplicate parts being added to product.
 *
 * */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Ims.fxml")));
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        root.setOnMousePressed(pressEvent -> root.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
        stage.setTitle("Inventory Management System");
        final Scene scene = new Scene(root, 960, 720);
        scene.getStylesheets().add("/style/style.css");
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**Main method to launch application.
     * @param args */
    public static void main(String[] args) {

        //Add test data
        //Inventory.addTestData();

        launch(args);
    }

}
