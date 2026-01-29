package Database;

public class Ingriedient {

    private int ID;
    private String name;
    private String quantity;

    public Ingriedient(Ingriedient ingriedient) {
        this.ID = ingriedient.getID();
        this.name = ingriedient.getName();
        this.quantity = ingriedient.getQuantity();
    }

    public String getName() {
        return name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getQuantity() {
        return quantity;
    }

    public Ingriedient(int ID, String name, String quantity) {
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
    }

}
