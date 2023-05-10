package model;

/**Model for outsourced part.
 *
 * @author Joseph Merritt
 * */
public class Outsourced extends Part{

    private String companyName;

    /**Constructor for Outsourced class.
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the part inventory
     * @param min the part inventory minimum
     * @param max the part inventory maximum
     * @param companyName the part supplier company name
     * */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**Returns the part company name.
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**Sets the parts company name.
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
