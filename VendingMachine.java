package vendingmachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine {
    private ArrayList<Product> products;
    private ArrayList<Integer> acceptedCoins;
    private Map<Integer, Integer> coinBalances; // Balance for each coin type
    private Scanner scanner;

    public VendingMachine() {
        products = new ArrayList<>();
        scanner = new Scanner(System.in);
        coinBalances = new HashMap<>();
        initializeProducts();
        initializeCoins();
    }

    public void initializeProducts() {
        products.add(new Product("Chips", 1.00, 5));
        products.add(new Product("Soda", 1.50, 3));
        products.add(new Product("Chocolate", 2.00, 2));
    }

    public void initializeCoins() {
        acceptedCoins = new ArrayList<>();
        acceptedCoins.add(1);
        acceptedCoins.add(2);
        acceptedCoins.add(5);
        acceptedCoins.add(10);
        acceptedCoins.add(20);
        for (int coin : acceptedCoins) {
            coinBalances.put(coin, 0); // Initialize each coin balance to 0
        }
    }

    public void showMainMenu() {
        int choice = -1;

        while (choice != 2) {
            System.out.println("Welcome to the Vending Machine!");
            System.out.println("-----------------------------------");
            System.out.println("1. Buy Product");
            System.out.println("2. Exit");
            System.out.println("3. Admin Access");
            System.out.print("Please select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    buyProduct();
                    break;
                case 2:
                    System.out.println("Thank you for using the vending machine. Goodbye!");
                    break;
                case 3:
                    adminAccess();
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }
        }
    }

    public void buyProduct() {
        System.out.println("\nAvailable Products:");
        for (int i = 0; i < products.size(); i++) {
            System.out.print((i + 1) + ". ");
            products.get(i).displayProduct();
        }

        System.out.print("Enter the product number you wish to buy: ");
        int productNumber = scanner.nextInt() - 1;

        if (productNumber >= 0 && productNumber < products.size()) {
            Product selectedProduct = products.get(productNumber);
            if (selectedProduct.getQuantity() > 0) {
                processPayment(selectedProduct);
            } else {
                System.out.println("Sorry, " + selectedProduct.getName() + " is out of stock.\n");
            }
        } else {
            System.out.println("Invalid product selection. Please try again.\n");
        }
    }

    public void processPayment(Product product) {
        System.out.println("\nYou selected " + product.getName() + " for $" + product.getPrice());
        double totalInserted = 0;

        while (totalInserted < product.getPrice()) {
            System.out.println("Accepted coins: 1, 2, 5, 10, 20");
            System.out.print("Please insert a valid coin: ");
            int coin = scanner.nextInt();

            if (acceptedCoins.contains(coin)) {
                totalInserted += coin;
                coinBalances.put(coin, coinBalances.get(coin) + 1); // Update specific coin balance
                System.out.println("Current inserted amount: $" + totalInserted);
            } else {
                System.out.println("Invalid coin. Please insert a valid coin.");
            }
        }

        if (totalInserted >= product.getPrice()) {
            double change = totalInserted - product.getPrice();
            product.reduceQuantity();
            System.out.println("Thank you! Dispensing " + product.getName());
            if (change > 0) {
                System.out.println("Returning change: $" + String.format("%.2f", change));
            }
            System.out.println();
        }
    }

    public void addProduct(String name, double price, int quantity) {
        products.add(new Product(name, price, quantity));
    }

    public Map<Integer, Integer> getCoinBalances() {
        return coinBalances;
    }

    public void increaseCoinBalance(int coin, int amount) {
        coinBalances.put(coin, coinBalances.get(coin) + amount);
    }

    public void decreaseCoinBalance(int coin, int amount) {
        if (coinBalances.get(coin) >= amount) {
            coinBalances.put(coin, coinBalances.get(coin) - amount);
        } else {
            System.out.println("Insufficient balance in the machine for coin: " + coin);
        }
    }

    private void adminAccess() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        Admin admin = new Admin("admin123", this);

        if (admin.authenticate(password)) {
            System.out.println("Access granted.");
            int adminChoice = -1;
            while (adminChoice != 0) {
                System.out.println("\nAdmin Menu:");
                System.out.println("1. Add Product");
                System.out.println("2. Show Machine Coin Balances");
                System.out.println("3. Add to Coin Balance");
                System.out.println("4. Remove from Coin Balance");
                System.out.println("0. Exit Admin Mode");
                System.out.print("Please select an option: ");
                adminChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (adminChoice) {
                    case 1:
                        admin.manageStock();
                        break;
                    case 2:
                        admin.showMachineBalance();
                        break;
                    case 3:
                        admin.addMachineBalance();
                        break;
                    case 4:
                        admin.removeMachineBalance();
                        break;
                    case 0:
                        System.out.println("Exiting Admin Mode.");
                        break;
                    default:
                        System.out.println("Invalid selection. Please try again.");
                        break;
                }
            }
        } else {
            System.out.println("Invalid password. Access denied.");
        }
    }
}
