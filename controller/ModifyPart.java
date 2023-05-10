package controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**ModifyPart Class provides control for Modify Part form.
 *
 * @author Joseph Merritt
 * */
public class ModifyPart implements Initializable {

    public ImageView exitButton;
    public ImageView minButton;
    public ImageView saveB;
    public ImageView cancelB;
    public RadioButton inHouseRB;
    public RadioButton outsourcedRB;
    public Label radioBLabel;
    public TextField name;
    public TextField inv;
    public TextField price;
    public TextField max;
    public TextField rbField;
    public TextField minF;
    public TextField idField;
    Image exitPressed = new Image("img/exit_pressed.png");
    Image minPressed = new Image("img/min_pressed.png");
    Image min = new Image("img/min.png");
    Image save = new Image("img/save.png");
    Image savePressed = new Image("img/save_pressed.png");
    Image cancel = new Image("img/cancel.png");
    Image cancelPressed = new Image("img/cancel_pressed.png");
    Part selectedPart = Ims.getSelectedPart();

    /**Initializes controller and sets initial values to those of the selected part from Ims screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setText(String.valueOf(selectedPart.getId()));
        name.setText(selectedPart.getName());
        inv.setText(String.valueOf(selectedPart.getStock()));
        price.setText(String.valueOf(selectedPart.getPrice()));
        max.setText(String.valueOf(selectedPart.getMax()));
        minF.setText(String.valueOf(selectedPart.getMin()));
        if(selectedPart instanceof InHouse){
            rbField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        } else if (selectedPart instanceof Outsourced) {
            outsourcedRB.setSelected(true);
            radioBLabel.setText("Company Name");
            rbField.setText(((Outsourced) selectedPart).getCompanyName());
        }
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void exitPressed() {
        exitButton.setImage(exitPressed);
    }

    /**Exits program on exit button mouse release.*/
    public void exitReleased() {
        System.exit(0);
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void minPressed() {
        minButton.setImage(minPressed);
    }

    /**Minimize program on minimize button mouse release.*/
    public void minReleased() {
        minButton.setImage(min);
        Stage stage = (Stage) minButton.getScene().getWindow();
        stage.setIconified(true);
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void savePressed() {
        saveB.setImage(savePressed);
    }

    /**Updates selected part in inventory on a successful save and loads the main Ims screen.
     * Text validation is performed on text field inputs.
     *
     * @param mouseEvent Mouse event action from button release used to get main Stage.
     * @throws IOException
     * */
    public void saveReleased(MouseEvent mouseEvent) throws IOException {
        saveB.setImage(save);

        int id = selectedPart.getId();
        String priceS = price.getText().replaceAll("\\$","");


        if(inHouseRB.isSelected()) {
            try {
                double d = Double.parseDouble(priceS);
                double priceP = Math.round(d*100.0)/100.0;
                int stockP = Integer.parseInt(inv.getText());
                int minP = Integer.parseInt(minF.getText());
                int maxP = Integer.parseInt(max.getText());
                int midP = Integer.parseInt(rbField.getText());

                if(minP > maxP){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Minimum inventory must be less than maximum.");
                    alert.showAndWait();
                    return;
                } else if (stockP < minP || stockP > maxP) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setContentText("Inv amount must be between Min and Max.");
                    alert.showAndWait();
                    return;
                }
                Inventory.deletePart(selectedPart);
                InHouse part = new InHouse(id, name.getText(), priceP, stockP, minP, maxP, midP);
                Inventory.addPart(part);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid value for each text field.");
                alert.showAndWait();
                return;
            }
        }
        else if (outsourcedRB.isSelected()) {
            try {
                double d = Double.parseDouble(priceS);
                double priceP = Math.round(d*100.0)/100.0;
                int stockP = Integer.parseInt(inv.getText());
                int minP = Integer.parseInt(minF.getText());
                int maxP = Integer.parseInt(max.getText());

                Inventory.deletePart(selectedPart);
                Outsourced part = new Outsourced(id, name.getText(), priceP, stockP, minP, maxP, rbField.getText());
                Inventory.addPart(part);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid value for each text field.");
                alert.showAndWait();
                return;
            }
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Ims.fxml")));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 960, 720);
        root.setOnMousePressed(pressEvent -> root.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
        stage.setTitle("Inventory Management System");
        scene.getStylesheets().add("/style/style.css");
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void cancelPressed() {
        cancelB.setImage(cancelPressed);
    }

    /**Cancels input and returns to main Ims screen without saving changes.
     *
     * @param mouseEvent Mouse event action from button release used to get main Stage.
     * @throws IOException
     * */
    public void cancelReleased(MouseEvent mouseEvent) throws IOException {
        cancelB.setImage(cancel);


        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Ims.fxml")));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 960, 720);
        root.setOnMousePressed(pressEvent -> root.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
        stage.setTitle("Inventory Management System");
        scene.getStylesheets().add("/style/style.css");
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**Changes label and clears text of text field when In-house radio button is selected.*/
    public void inHouseRBAction() {
        if(inHouseRB.isSelected()){
            radioBLabel.setText("Machine ID");
            rbField.setText("");
        }
        else if (outsourcedRB.isSelected()) {
            radioBLabel.setText("Company Name");
            rbField.setText("");
        }
    }

    /**Changes label and clears text of text field when Outsourced radio button is selected.*/
    public void outsourcedRBAction() {
        if(inHouseRB.isSelected()){
            radioBLabel.setText("Machine ID");
            rbField.setText("");
        }
        else if (outsourcedRB.isSelected()) {
            radioBLabel.setText("Company Name");
            rbField.setText("");
        }
    }
}


