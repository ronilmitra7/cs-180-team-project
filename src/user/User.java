package src.user;

import src.Marketplace.Item;

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
}
