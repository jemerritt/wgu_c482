package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Model for product.
 *
 * @author Joseph Merritt
 * */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**Constructor for Product class.
     * @param id the product id
     * @param name the product name
     * @param price the product price
     * @param stock the product inventory
     * @param min the product inventory minimum
     * @param max the product inventory maximum
     * */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**Returns the product id.
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**Sets the product id.
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**Returns the product name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**Sets the product name.
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**Returns the product price.
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**Sets the product price.
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**Returns the product stock.
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**Sets the product stock.
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**Returns the product min.
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**Sets the product min.
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**Returns the product max.
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**Sets the product max.
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**Adds the supplied part to the associated parts list.
     * @param part the part to add to associatedParts list
     * */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**Removes the supplied part from the associated parts list.
     * @param part the part to remove from associatedParts list
     * @return true if part is not null or false if null
     * */
    public boolean deleteAssociatedPart(Part part){
        if (associatedParts.contains(part)){
            associatedParts.remove(part);
            return true;
        }
        else
            return false;
    }

    /**Returns the list of associated parts.
     * @return the associatedParts list
     * */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
