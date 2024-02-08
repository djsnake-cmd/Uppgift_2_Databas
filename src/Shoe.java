public class Shoe {
    int id;
    int price;
    String category;
    String color;
    String brand;
    int size;
    int stock;

    public int getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getColor() {
        return color;
    }

    public String getBrand() {
        return brand;
    }

    public int getSize() {
        return size;
    }

    public Shoe() {
    }

    public int getId() {
        return id;
    }

    public Shoe(int id, int price, String category, String color, String brand, int size, int stock) {
        this.id = id;
        this.price = price;
        this.category = category;
        this.color = color;
        this.brand = brand;
        this.size = size;
        this.stock = stock;
    }

    @Override
    public String toString(){
        return "Märke: " + brand + " | " +
                "Färg: " + color + " | " +
                "Storlek: " + size + " | ";
    }
}
