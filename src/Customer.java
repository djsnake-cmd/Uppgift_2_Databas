public class Customer {
    int id;
    String name;
    String password;
    String street;

    public Customer(){}

    public Customer(int id, String name, String street, String password) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getPassword() {
        return password;
    }
}
