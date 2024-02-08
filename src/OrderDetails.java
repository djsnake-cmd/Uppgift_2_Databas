import com.mysql.cj.x.protobuf.MysqlxCrud;

public class OrderDetails {
    CustomerOrder customerOrder = new CustomerOrder();
    Shoe shoe = new Shoe();
    int quantity;

    public OrderDetails(CustomerOrder customerOrder, Shoe shoe, int quantity) {
        this.customerOrder = customerOrder;
        this.shoe = shoe;
        this.quantity = quantity;
    }

    public OrderDetails(){

    }
}
