import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UX {
    Scanner scan = new Scanner(System.in);
    String userName; String password;
    String brand; int size; String color;
    List<Customer> customerList;
    List<Shoe> productList;
    Customer c = new Customer();
    Customer currentCustomer;
    Shoe selectedShoe;

    DatabaseHandler dbh = new DatabaseHandler();

    public UX() throws IOException {
        customerList = dbh.getCustomers();
        productList = dbh.getProductList();
        userLogIn();

    }

    public void userLogIn(){

        while(currentCustomer == null){
            System.out.print("Skriv ditt användarnamn: "); userName = scan.nextLine();
            System.out.print("Skriv ditt lösenord: "); password = scan.nextLine();
            customerList.stream()
                    .filter(c -> c.getName().equals(userName) && c.getPassword().equals(password))
                    .findFirst()
                    .ifPresent(foundCustomer -> {
                        currentCustomer = foundCustomer;
                    });
            if (currentCustomer == null){
                System.out.println("Användare hittas ej. Försök igen.");
            }else{
                System.out.println("Login successful for user: " + currentCustomer.getName());
                showProducts();
            }
        }
    }

    public void showProducts(){
        System.out.println("Välj en produkt ur listan:");
        productList.forEach(s -> System.out.println("Färg: " + s.getColor() + " | " +
                "Märke: " + s.getBrand() + " | " +
                "Storlek: " + s.getSize() + " | " +
                "Kategori: " + s.getCategory() + " | " +
                "Pris: " + s.getPrice() + "\n"));
        chooseProduct();
    }

    public void chooseProduct(){
        while(selectedShoe == null){
            System.out.print("Märke: "); brand = scan.nextLine();
            System.out.print("Färg: "); color = scan.nextLine();
            System.out.print("Storlek: "); size = scan.nextInt();
            scan.nextLine();
            productList.stream().filter(findShoe -> findShoe.brand.equals(brand)
                    && findShoe.color.equals(color)
                    && findShoe.size == size)
                    .findFirst()
                    .ifPresent(foundShoe -> selectedShoe = foundShoe);
            if (!(selectedShoe == null)){
                if (selectedShoe.stock >0){
                    System.out.println("Produkten lades till i beställningen:" + "\n" + selectedShoe);
                    dbh.addToCart(20,currentCustomer.id, selectedShoe.id);
                } else{
                    System.out.println("Produkten finns ej i lager.");
                }
            } else{
                System.out.println("Produkten hittades ej.");
            }

        }
        //System.out.println("sko_id = " + selectedShoe.id + " Kund_id = " + currentCustomer.id);

    }
    public static void main(String[] args) throws IOException {
        new UX();
    }

}
