import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class DatabaseHandler {

    Properties prop = new Properties();
    List<Customer> customerList = new ArrayList<>();
    List<Shoe> productList = new ArrayList<>();
    List<CustomerOrder> orderList = new ArrayList<>();
    List<OrderDetails> orderDetailsList = new ArrayList<>();
    private Connection getConnection() throws IOException, SQLException {
        prop.load(new FileInputStream("src/Settings.properties"));
        return DriverManager.getConnection(
                prop.getProperty("connectionString"),
                prop.getProperty("username"),
                prop.getProperty("password"));
    }
    public void addToCart(int orderID, int customerID, int shoeID){
        try(Connection con = getConnection()) {
            CallableStatement cStmt = con.prepareCall("CALL AddToCart(?,?,?)");
            cStmt.setInt(1,orderID);
            cStmt.setInt(2,customerID);
            cStmt.setInt(3,shoeID);
            cStmt.execute();

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getCustomers() throws IOException {
        try(Connection con = getConnection()){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT *  FROM customer;");
            while(rs.next()){
                customerList.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("street"),
                        rs.getString("password")));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return customerList;
    }

    public List<CustomerOrder> getCustomerOrderList() throws SQLException, IOException {
        try(Connection con = getConnection()){
            CallableStatement cStmt = con.prepareCall("CALL GetAllOrders");
            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();

            while(rs.next()){
                    Customer matchingCustomer = getCustomers().stream()
                            .filter(s -> {
                                try {
                                    return s.getName().equals(rs.getString("name"));
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .findFirst()
                            .orElse(null);

                    Shoe matchingShoe = getProductList().stream()
                                    .filter(s-> {
                                        try{
                                            return  s.getBrand().equals(rs.getString("brand")) &&
                                                    s.getSize() == rs.getInt("size") &&
                                                    s.getColor().equals(rs.getString("color"));
                                        } catch (SQLException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }).toList().get(0);

                    orderList.add(new CustomerOrder(
                            rs.getInt("id"),
                            rs.getString("brand"),
                            rs.getString("color"),
                            rs.getInt("size"),
                            rs.getInt("quantity"),
                            rs.getDate("order_date"),
                            matchingCustomer,
                            rs.getString("customer.street")
                    ));

            }

        }
        return orderList;
    }

    public List<OrderDetails> getOrderDetailsList() throws SQLException, IOException {
        try (Connection con = getConnection()) {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM order_details");

            while (rs.next()) {
                CustomerOrder matchingOrder = getCustomerOrderList().stream()
                        .filter(s -> {
                            try {
                                return s.getId() == rs.getInt("order_id");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .findFirst()
                        .orElse(null);
                Shoe matchingShoe = getProductList().stream()
                        .filter(s-> {
                            try{
                                return s.getId() == rs.getInt("shoe_id");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .findFirst()
                        .orElse(null);
                orderDetailsList.add(new OrderDetails(
                        matchingOrder,
                        matchingShoe,
                        rs.getInt("quantity"))
                );
            }
        }
        return orderDetailsList;
    }

    public List<Shoe> getProductList(){
        try(Connection con = getConnection()){
            CallableStatement cStmt = con.prepareCall("CALL ShowAllProducts");
            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();

            while(rs.next()){
                productList.add(new Shoe(
                        rs.getInt("id"),
                        rs.getInt("price"),
                        rs.getString("category_id"),
                        rs.getString("color"),
                        rs.getString("brand"),
                        rs.getInt("size"),
                        rs.getInt("in_stock")));
            }

        }catch (SQLException | IOException e){
            e.printStackTrace();
        }
        return productList;
    }

}
