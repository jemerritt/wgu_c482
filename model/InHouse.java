package model;

/**Model for in house part.
 *
 * @author Joseph Merritt
 * */
public class InHouse extends Part{

    private int machineId;

    /**Constructor for InHouse class.
     * @param id the part id
     * @param name the part name
     * @param price the part price
     * @param stock the part inventory
     * @param min the part inventory minimum
     * @param max the part inventory maximum
     * @param machineId the part machine ID
     * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**Sets the parts machine id.
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**Returns the parts machine id.
     * @return the machineId
     */
    public int getMachineId() {
        return machineId;
    }
}
