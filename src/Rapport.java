import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Rapport {
    Scanner scan = new Scanner(System.in);
    int choice; int reportChoice;
    String searchColor; String searchBrand; int searchSize;

    List<Customer> customerList = new ArrayList<>();
    List<CustomerOrder> orderList;
    DatabaseHandler dbh = new DatabaseHandler();

    public Rapport() throws SQLException, IOException, InterruptedException {
        orderList = dbh.getCustomerOrderList();
        while (reportChoice != 6) {
            System.out.println("""
                    0: Avsluta programmet
                    1: Sök vilka kunder som köpt en typ av vara
                    2: Lista antalet ordrar lagda av alla kunder
                    3: Lista kundernas totala spenderingar
                    """);
            reportChoice = scan.nextInt();

            switch (reportChoice) {
                case 1: {
                    while (choice != 4) {
                        searchProductPurchases();
                        Thread.sleep(1500);

                    }
                    break;
                }
                case 2: {
                    listCustomersOrders();
                    Thread.sleep(1500);
                    break;
                }
                case 3: {
                    listCustomerTotalSpendings();
                    Thread.sleep(1500);
                    break;
                }
            }
        }
    }

    private void searchProductPurchases(){
        System.out.println("""
                    1: sök färg
                    2: sök märke
                    3: Sök storlek
                    4: Avsluta""");
        choice = scan.nextInt();
        scan.nextLine();

        Predicate<CustomerOrder> filterPredicate = null;
            switch (choice){
                case 1: {
                    System.out.println("Vilken färg vill du söka på?");
                    searchColor = scan.nextLine();
                    filterPredicate = order -> order.getShoeColor().equals(searchColor);
                    /*System.out.println(orderList.stream().filter(order -> order.getShoeColor().equals(searchColor)).map(order -> "Kund: " + order.getCustomerName() +
                            " | " + "Gata: " + order.getCustomerStreet()).distinct().collect(Collectors.joining("\n")));*/
                    break;

                }
                case 2:{
                    System.out.println("Vilket märke vill du söka på?");
                    searchBrand = scan.nextLine();
                    filterPredicate = order -> order.getShoeBrand().equals(searchBrand);
                    /*System.out.println(orderList.stream().filter(order -> order.getShoeBrand().equals(searchBrand)).map(order -> "kund: " +
                            order.getCustomerName() + " Gata: " + order.getCustomerStreet()).distinct().collect(Collectors.joining("\n")));*/
                    break;

                }
                case 3:{
                    System.out.println("Vilken storlek vill du söka på?");
                    searchSize = scan.nextInt();
                    filterPredicate = order -> order.getShoeSize() == searchSize;
                    /*System.out.println(orderList.stream().filter(order -> order.getShoeSize() == searchSize).map(order -> "kund: " +
                            order.getCustomerName() + " Gata: " + order.getCustomerStreet()).distinct().collect(Collectors.joining("\n")));*/
                    break;

                }
                case 4:
                    return;
            }
        performSearchCustomer(filterPredicate);
    }

    private void performSearchCustomer(Predicate<CustomerOrder> filterPredicate){
        System.out.println(orderList.stream()
                .filter(filterPredicate)
                .map(order-> "Kund: " +  order.getCustomerName() + " | Gata: " + order.getCustomerStreet())
                .distinct()
                .collect(Collectors.joining("\n")));
    }

    private void listCustomersOrders(){
        Map<String, Long> customerOrders =
                orderList.stream().collect(Collectors.groupingBy(CustomerOrder::getCustomerName, Collectors.counting()));

        System.out.println(customerOrders);
    }

    private void listCustomerTotalSpendings(){
        Map<String, Double> customerTotals =
                orderList.stream()
                        .collect(Collectors.groupingBy
                                (CustomerOrder::getCustomerName,
                                        Collectors.summingDouble(order -> order.getQuantity()* order.getShoePrice())));

        System.out.println(customerTotals);

    }

    public static void main(String[] args) throws SQLException, IOException, InterruptedException {
        new Rapport();
    }
}
