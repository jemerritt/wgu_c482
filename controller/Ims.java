package controller;

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

/**Ims Class provides control for the main Inventory Management System screen.
 *
 * @author Joseph Merritt
 * */
public class Ims implements Initializable {

    public ImageView exitButton;
    public ImageView minButton;
    public ImageView partAddB;
    public ImageView partModB;
    public ImageView partDelB;
    public ImageView prodAddB;
    public ImageView prodModB;
    public ImageView prodDelB;
    public TableView<Part> partsTable;
    public TableView<Product> productsTable;
    public TableColumn<Product,Integer> prodIdColumn;
    public TableColumn<Product,String> prodNameColumn;
    public TableColumn<Product,Integer> prodInvColumn;
    public TableColumn<Product,Double> prodPriceColumn;
    public TableColumn<Part,Integer> partIdColumn;
    public TableColumn<Part,String> partNameColumn;
    public TableColumn<Part,Integer> partInvColumn;
    public TableColumn<Part,Double> partPriceColumn;
    public TextField partSearch;
    public TextField productSearch;
    public Label partSearchError;
    public Label productSearchError;
    public ImageView bExitB;
    Image exitPressed = new Image("img/exit_pressed.png");
    Image bexitPressed = new Image("img/exitB_pressed.png");
    Image minPressed = new Image("img/min_pressed.png");
    Image min = new Image("img/min.png");
    Image add = new Image("img/add.png");
    Image addPressed = new Image("img/add_pressed.png");
    Image mod = new Image("img/modify.png");
    Image modPressed = new Image("img/modify_pressed.png");
    Image del = new Image("img/delete.png");
    Image delPressed = new Image("img/delete_pressed.png");

    /**Initializes controller and sets initial table contents.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        prodIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**Initialize static variable for sending part information between screens.*/
    private static Part selectedPart = null;

    /**
     * @return Selected part from allParts table.
     * */
    public static Part getSelectedPart(){
        return selectedPart;
    }

    /**Initialize static variable for sending product information between screens.*/
    private static Product selectedProduct = null;

    /**
     * @return Selected product from allProducts table.
     * */
    public static Product getSelectedProduct(){
        return selectedProduct;
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
    public void partAddPressed() {
        partAddB.setImage(addPressed);
    }

    /**Loads Add Part screen.
     *
     * @param mouseEvent Mouse event action from button release used to get main Stage.
     * @throws IOException
     * */
    public void partAddReleased(MouseEvent mouseEvent) throws IOException {
        partAddB.setImage(add);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddPart.fxml")));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 960, 720);
        root.setOnMousePressed(pressEvent -> root.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
        stage.setTitle("Add Part");
        scene.getStylesheets().add("/style/style.css");
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void partModPressed() {
        partModB.setImage(modPressed);
    }

    /**Loads Modify Part screen.
     *
     * @param mouseEvent Mouse event action from button release used to get main Stage.
     * @throws IOException
     * */
    public void partModReleased(MouseEvent mouseEvent) throws IOException {
        partModB.setImage(mod);
        selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null)
            return;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyPart.fxml")));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 960, 720);
        root.setOnMousePressed(pressEvent -> root.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
        stage.setTitle("Modify Part");
        scene.getStylesheets().add("/style/style.css");
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void partDelPressed() {
        partDelB.setImage(delPressed);
    }

    /**Prompts user for delete confirmation. Successful confirmation removes selected part.*/
    public void partDelReleased() {
        partDelB.setImage(del);
        selectedPart = partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null)
            return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete " +
                selectedPart.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            if(!Inventory.deletePart(selectedPart)){
                Alert deleteAlert = new Alert(Alert.AlertType.ERROR);
                deleteAlert.setTitle("Error Dialog");
                deleteAlert.setContentText("No part found.");
                deleteAlert.showAndWait();
            }
        }
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void prodAddPressed() {
        prodAddB.setImage(addPressed);
    }

    /**Loads the Add Product screen.
     *
     * @param mouseEvent Mouse event action from button release used to get main Stage.
     * @throws IOException
     * */
    public void prodAddReleased(MouseEvent mouseEvent) throws IOException {
        prodAddB.setImage(add);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddProduct.fxml")));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 960, 720);
        root.setOnMousePressed(pressEvent -> root.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
        stage.setTitle("Add Product");
        scene.getStylesheets().add("/style/style.css");
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void prodModPressed() {
        prodModB.setImage(modPressed);
    }

    /**Loads the Add Product screen.
     *
     * @param mouseEvent Mouse event action from button release used to get main Stage.
     * @throws IOException
     * */
    public void prodModReleased(MouseEvent mouseEvent) throws IOException {
        prodModB.setImage(mod);

        selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null)
            return;

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyProduct.fxml")));
        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 960, 720);
        root.setOnMousePressed(pressEvent -> root.setOnMouseDragged(dragEvent -> {
            stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
            stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
        }));
        stage.setTitle("Modify Part");
        scene.getStylesheets().add("/style/style.css");
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void prodDelPressed() {
        prodDelB.setImage(delPressed);
    }

    /**Prompts user for delete confirmation if selected product has no associated parts.
     * Successful confirmation removes selected product.*/
    public void prodDelReleased() {
        prodDelB.setImage(del);
        Product p = productsTable.getSelectionModel().getSelectedItem();

        if (p == null)
            return;

        if(p.getAllAssociatedParts().size() > 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Product can not be deleted until all associated parts have been removed.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete " +
                p.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            if(!Inventory.deleteProduct(p)){
                Alert deleteAlert = new Alert(Alert.AlertType.ERROR);
                deleteAlert.setTitle("Error Dialog");
                deleteAlert.setContentText("No part found.");
                deleteAlert.showAndWait();
            }
        }
    }

    /**Filter all parts table using text entered.*/
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

    /**Filter all products table using text entered.*/
    public void productSearchAction() {
        String q = productSearch.getText();

        ObservableList<Product> products = Inventory.lookupProduct(q);

        if (products.size() == 0) {
            try {
                int id = Integer.parseInt(q);
                Product p = Inventory.lookupProduct(id);
                if (p != null)
                    products.add(p);
            }
            catch (NumberFormatException e)
            {
                //ignore
            }
        }

        if (products.size() == 0) {
            productSearchError.setText("No Matching Products Found");
        }
        else {
            productSearchError.setText("");
        }

        productsTable.setItems(products);
    }

    /**Controls custom button highlighting behavior on mouse press.*/
    public void bExitBPressed() {
        bExitB.setImage(bexitPressed);
    }

    /**Exits program on exit button mouse release.*/
    public void bExitBReleased() {
        System.exit(0);
    }
}


