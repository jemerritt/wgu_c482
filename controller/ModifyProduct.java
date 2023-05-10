package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**ModifyProduct Class provides control for Modify Product form.
 *
 * @author Joseph Merritt
 * */
public class ModifyProduct implements Initializable {

    public ImageView exitButton;
    public ImageView minButton;
    public ImageView saveB;
    public ImageView cancelB;
    public TextField name;
    public TextField inv;
    public TextField price;
    public TextField max;
    public TextField minF;
    public TableView<Part> partsTable;
    public TableView<Part> associatedPartsTable;
    public ImageView rapB;
    public ImageView partAddB;
    public TextField partSearch;
    public TableColumn<Part,Integer> partIdColumn;
    public TableColumn<Part,String> partNameColumn;
    public TableColumn<Part,Integer> partInvColumn;
    public TableColumn<Part,Double> partPriceColumn;
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public TableColumn<Part,Integer> aPartIdColumn;
    public TableColumn<Part,String> aPartNameColumn;
    public TableColumn<Part,Integer> aPartInvColumn;
    public TableColumn<Part,Double> aPartPriceColumn;
    public Label partSearchError;
    public TextField idField;

    Image exitPressed = new Image("img/exit_pressed.png");
    Image minPressed = new Image("img/min_pressed.png");
    Image min = new Image("img/min.png");
    Image save = new Image("img/save.png");
    Image savePressed = new Image("img/save_pressed.png");
    Image cancel = new Image("img/cancel.png");
    Image cancelPressed = new Image("img/cancel_pressed.png");
    Image add = new Image("img/add.png");
    Image addPressed = new Image("img/add_pressed.png");
    Image rap = new Image("img/rap.png");
    Image rapPressed = new Image("img/rap_pressed.png");
    Product selectedProduct = Ims.getSelectedProduct();

    /**Initializes controller and sets initial values to those of the selected product from Ims screen.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setText(String.valueOf(selectedProduct.getId()));
        name.setText(selectedProduct.getName());
        inv.setText(String.valueOf(selectedProduct.getStock()));
        price.setText(String.valueOf(selectedProduct.getPrice()));
        max.setText(String.valueOf(selectedProduct.getMax()));
        minF.setText(String.valueOf(selectedProduct.getMin()));

        associatedParts = selectedProduct.getAllAssociatedParts();

        partsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTable.setItems(associatedParts);
        aPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        aPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        aPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        aPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
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

    /**Updates selected product in inventory on a successful save and loads the main Ims screen.
     * Text validation is performed on text field inputs.
     *
     * @param mouseEvent Mouse event action from button release used to get main Stage.
     * @throws IOException
     * */
    public void saveReleased(MouseEvent mouseEvent) throws IOException {
        saveB.setImage(save);

        int id = 0;
        String priceS = price.getText().replaceAll("\\$","");

        for (Product p : Inventory.getAllProducts()){
            if(p.getId() > id){
                id = p.getId();
            }
        }
        id++;

            try {
                double d = Double.parseDouble(priceS);
                double priceP = Math.round(d*100.0)/100.0;
                int stockP = Integer.parseInt(inv.getText());
                int minP = Integer.parseInt(minF.getText());
                int maxP = Integer.parseInt(max.getText());

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

                Inventory.deleteProduct(selectedProduct);
                Product product = new Product(id, name.getText(), priceP, stockP, minP, maxP);

                for(Part p : associatedParts)
                {
                    product.addAssociatedPart(p);
                }

                Inventory.addProduct(product);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please enter a valid value for each text field.");
                alert.showAndWait();
                return;
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

    /**Controls custom button highlighting behavior on mouse press.*/
    public void partAddPressed() {
        partAddB.setImage(addPressed);
    }

    /**Adds part from upper all parts table to lower associated parts table.*/
    public void partAddReleased() {
        partAddB.setImage(add);

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null){
            associatedParts.add(selectedPart);
            associatedPartsTable.setItems(associatedParts);
        }
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void rapPressed() {
        rapB.setImage(rapPressed);
    }

    /**Removes part from lower associated parts table to upper all parts table.*/
    public void rapReleased() {
        rapB.setImage(rap);

        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete " +
                    selectedPart.getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                if(!associatedParts.remove(selectedPart)){
                    Alert deleteAlert = new Alert(Alert.AlertType.ERROR);
                    deleteAlert.setTitle("Error Dialog");
                    deleteAlert.setContentText("No part found.");
                    deleteAlert.showAndWait();
                }
            }
            associatedPartsTable.setItems(associatedParts);
        }
    }

    /**Filter upper all parts table using text entered.*/
    public void partSearchAction() {
        String q = partSearch.getText();

        ObservableList<Part> parts = Inventory.lookupPart(q);

        if (parts.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Part p = Inventory.lookupPart(id);
                if (p != null)
                    parts.add(p);
            }
            catch (NumberFormatException e)
            {
                //ignore
            }
        }

        if (parts.size() == 0) {
            partSearchError.setText("No Matching Parts Found");
        }
        else {
            partSearchError.setText("");
        }

        partsTable.setItems(parts);
    }
}


