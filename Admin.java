package vendingmachine;

import java.util.Scanner;

public class Admin {
    private String password;
    private VendingMachine vendingMachine;

    public Admin(String password, VendingMachine vendingMachine) {
        this.password = password;
        this.vendingMachine = vendingMachine;
    }

    public boolean authenticate(String inputPassword) {
        return inputPassword.equals(password);
    }

    public void manageStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the product name to add: ");
        String productName = scanner.nextLine();
        System.out.println("Enter the price of the product: ");
        double productPrice = scanner.nextDouble();
        System.out.println("Enter the quantity to add: ");
        int quantity = scanner.nextInt();

        vendingMachine.addProduct(productName, productPrice, quantity);
        System.out.println("Product added successfully.");
    }

    public void showMachineBalance() {
        System.out.println("Machine coin balances:");
        vendingMachine.getCoinBalances().forEach((coin, balance) -> {
            System.out.println("Coin: " + coin + " - Balance: " + balance);
        });
    }

    public void addMachineBalance() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the coin denomination to add: ");
        int coin = scanner.nextInt();
        System.out.print("Enter the amount to add: ");
        int amountToAdd = scanner.nextInt();
        if (vendingMachine.getCoinBalances().containsKey(coin) && amountToAdd > 0) {
            vendingMachine.increaseCoinBalance(coin, amountToAdd);
            System.out.println("Balance added successfully.");
        } else {
            System.out.println("Invalid coin denomination or amount.");
        }
    }

    public void removeMachineBalance() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the coin denomination to remove: ");
        int coin = scanner.nextInt();
        System.out.print("Enter the amount to remove: ");
        int amountToRemove = scanner.nextInt();
        if (vendingMachine.getCoinBalances().containsKey(coin) && amountToRemove > 0) {
            vendingMachine.decreaseCoinBalance(coin, amountToRemove);
            System.out.println("Balance removed successfully.");
        } else {
            System.out.println("Invalid coin denomination or amount.");
        }
    }
}
