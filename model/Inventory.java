package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Model for inventory.
 *
 * @author Joseph Merritt
 * */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**Test method for populating test data.*/
    public static void addTestData(){
        InHouse a = new InHouse(1,"Bar",9.99,4,1,5, 5);
        Inventory.addPart(a);
        InHouse b = new InHouse(2,"Button",5.99,2,1,50, 75);
        Inventory.addPart(b);
        Outsourced c = new Outsourced(3,"Plate",7.50,25,5,30, "Versa");
        Inventory.addPart(c);
        Product d = new Product(4,"PC",799.99,3,1,10);
        Inventory.addProduct(d);
        Product e = new Product(5,"Console",499.99,8,5,22);
        Inventory.addProduct(e);
    }

    /**Adds part to allParts list.
     * @param part the part to add to allParts list
     * */
    public static void addPart(Part part){
        allParts.add(part);
    }

    /**Adds product to allProducts list.
     * @param product the product to add to allProducts list
     * */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /**Removes selected part from allParts list.
     * @param selectedPart the part to remove from allParts list
     * @return true if selectedPart is not null or false if null
     * */
    public static boolean deletePart(Part selectedPart) {
        if (selectedPart != null){
            allParts.remove(selectedPart);
            return true;
        }
        else
            return false;
    }

    /**Search for part by id.
     * @param partId the partId to find in allParts list
     * @return the part from allParts list with corresponding id or null if partId does not exist
     * */
    public static Part lookupPart(int partId) {
        for(Part p : allParts){
            if(p.getId() == partId){
                return p;
            }
        }
        return null;
    }

    /**Search for part by full or partial name.
     * @param partName the string to compare to allParts list
     * @return list of all parts with matching characters to partName String
     * */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> partList= FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part p : allParts){
            if(p.getName().contains(partName)){
                partList.add(p);
            }
        }

        return partList;
    }

    /**Search for product by id.
     * @param productId the partId to find in allParts list
     * @return the part from allParts list with corresponding id or null if productId does not exist
     * */
    public static Product lookupProduct(int productId) {
        for(Product p : allProducts){
            if(p.getId() == productId){
                return p;
            }
        }
        return null;
    }

    /**Search for product by full or partial name.
     * @param productName the string to compare to allProducts list
     * @return list of all products with matching characters to productName String
     * */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> productList= FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product p : allProducts){
            if(p.getName().contains(productName)){
                productList.add(p);
            }
        }

        return productList;
    }

    /**Updates selected part in allParts list.
     * @param index the index for updated part
     * @param selectedPart the part to update
     * */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index,selectedPart);
    }

    /**Updates selected product in allProducts list.
     * @param index the index for updated product
     * @param selectedProduct the product to update
     * */
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index,selectedProduct);
    }

    /**Removes selected product from allProducts list.
     * @param selectedProduct the product to remove from allProducts list
     * @return true if selectedProduct is not null or false if null
     * */
    public static boolean deleteProduct(Product selectedProduct) {
        if (selectedProduct != null){
            allProducts.remove(selectedProduct);
            return true;
        }
        else
            return false;
    }

    /**Returns list of all parts.
     * @return the list of all parts.
     * */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**Returns list of all products.
     * @return the list of all products.
     * */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
