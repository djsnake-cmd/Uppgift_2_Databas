import java.time.LocalDate;
import java.util.Date;

public class CustomerOrder {

    String brand; String color;int size; int quantity;
    int id;
    Date orderDate;
    Customer customer;
    String street;
    Shoe shoe;

    public int getQuantity() {
        return quantity;
    }

    public CustomerOrder(int id, Shoe shoe, int quantity, Date orderDate, Customer customer, String street){
        this.id = id;
        this.shoe = shoe;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.customer = customer;
        this.street = street;
    }

    //EXEMPEL
    public CustomerOrder(int id, String brand, String color, int size, int quantity, Date orderDate, Customer customer, String street) {
        this.id = id;
        this.brand = brand;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.customer = customer;
        this.street = street;
    }


    public String getCustomerName(){
        return customer.getName();
    }

    public int getShoePrice(){
        return shoe.price;
    }
    public String getShoeColor(){
        return shoe.color;
    }
    public String getShoeBrand(){
        return shoe.brand;
    }
    public int getShoeSize(){
        return shoe.size;
    }
    public String getCustomerStreet(){
        return customer.getStreet();
    }

    @Override
    public String toString(){
        return "id = " + id + " | Customer = " + customer.getName()
                + " | Märke: " + brand + " | Färg: " + color
                + " | Storlek: " + size + " | Antal: " + quantity + " | Datum: " + orderDate + "\n";
    }
}
