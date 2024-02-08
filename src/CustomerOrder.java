import java.time.LocalDate;
import java.util.Date;

public class CustomerOrder {

    int id;
    int customerId;
    String brand;
    String color;
    int size;
    int quantity;
    Date orderDate;
    Customer customer;
    String street;

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
    public String getCustomerStreet(){
        return customer.getStreet();
    }
    public String getColor(){
        return color;
    }
    public String getBrand(){
        return brand;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public CustomerOrder(){}

    @Override
    public String toString(){
        return "id = " + id + " | Customer = " + customer.getName()
                + " | Märke: " + brand + " | Färg: " + color
                + " | Storlek: " + size + " | Antal: " + quantity + " | Datum: " + orderDate + "\n";
    }

    public int getSize() {
        return size;
    }
}
