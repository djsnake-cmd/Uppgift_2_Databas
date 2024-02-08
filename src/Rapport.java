import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Rapport {
    Scanner scan = new Scanner(System.in);
    int choice;
    int reportChoice;
    String searchColor;
    String searchBrand;
    int searchSize;

    List<Customer> customerList = new ArrayList<>();
    List<CustomerOrder> orderList = new ArrayList<>();
    DatabaseHandler dbh = new DatabaseHandler();

    public Rapport() throws SQLException, IOException, InterruptedException {
        while (reportChoice != 6) {
            System.out.println("""
                    0: Avsluta programmet
                    1: Sök vilka kunder som köpt en typ av vara
                    2: Lista antalet ordrar lagda av alla kunder
                    """);
            reportChoice = scan.nextInt();

            switch (reportChoice) {
                case 1: {
                    while (choice != 4) {
                        searchProductPurchases();
                        Thread.sleep(2000);

                    }
                    break;
                }
                case 2: {
                    listCustomersOrders();
                    break;
                }
                case 3: {

                }
            }
        }




    }

    private void searchProductPurchases() throws IOException, SQLException {
        orderList = dbh.getCustomerOrderList();

            System.out.println("""
                    1: sök färg
                    2: sök märke
                    3: Sök storlek
                    4: Avsluta""");
            choice = scan.nextInt();
            scan.nextLine();
            switch (choice){
                case 1: {
                    System.out.println("Vilken färg vill du söka på?");
                    searchColor = scan.nextLine();
                    System.out.println(orderList.stream().filter(order -> order.getColor().equals(searchColor)).map(order -> "Kund: " + order.getCustomerName() +
                            " | " + "Gata: " + order.getCustomerStreet()).distinct().collect(Collectors.joining("\n")));
                    break;

                }
                case 2:{
                    System.out.println("Vilket märke vill du söka på?");
                    searchBrand = scan.nextLine();
                    System.out.println(orderList.stream().filter(order -> order.getBrand().equals(searchBrand)).map(order -> "kund: " +
                            order.getCustomerName() + " Gata: " + order.getCustomerStreet()).distinct().collect(Collectors.joining("\n")));
                    break;

                }
                case 3:{
                    System.out.println("Vilken storlek vill du söka på?");
                    searchSize = scan.nextInt();
                    System.out.println(orderList.stream().filter(order -> order.getSize() == searchSize).map(order -> "kund: " +
                            order.getCustomerName() + " Gata: " + order.getCustomerStreet()).distinct().collect(Collectors.joining("\n")));
                    break;

                }
            }
    }

    private void listCustomersOrders() throws SQLException, IOException {
        orderList = dbh.getCustomerOrderList();

        Map<String, Long> customerOrders =
                orderList.stream().collect(Collectors.groupingBy(CustomerOrder::getCustomerName, Collectors.counting()));

        System.out.println(customerOrders);
    }

    private void listCustomerTotalSpendings() throws SQLException, IOException {
        orderList = dbh.getCustomerOrderList();
    }



    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        new Rapport();
    }
}
