package vendingmachine;

public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    public void increaseQuantity(int amount) {
        quantity += amount;
    }

    public void displayProduct() {
        System.out.println(name + " - $" + price + " (" + quantity + " left)");
    }
}

