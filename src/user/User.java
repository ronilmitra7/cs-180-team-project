package user;

import Marketplace.Item;

import java.util.ArrayList;

public class User implements UserInterface {
    private String username;
    private String email;
    private String name;
    private String password;
    private double balance;
    private ArrayList<Item> itemsForSale;

    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
        this.name = "";
        this.email = "";
        this.username = "";
        this.password = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<Item> getItemsForSale() {
        return itemsForSale;
    }

    public void setItemsForSale(ArrayList<Item> items) {
        this.itemsForSale = items;
    }

    public void addItem(Item item) {
        itemsForSale.add(item);
    }

    public static boolean isValid(User user) {
        if (user.getUsername().length() > 11) {
            System.out.println("Username cannot exceed 11 characters.");
            return false;
        } else if (user.getUsername().contains(" ")) {
            System.out.println("Username cannot contain spaces.");
            return false;
        }
        if (user.getPassword().length() > 11) {
            System.out.println("Password cannot exceed 11 characters.");
            return false;
        } else if (user.getPassword().contains(" ")) {
            System.out.println("Password cannot contain spaces.");
            return false;
        }

        return true;
    }

    public String toString() {
        return String.format("%s,%s,%s,%s", name, email, username, password);
    }
}
