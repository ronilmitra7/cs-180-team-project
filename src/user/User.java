package src.user;

import src.Marketplace.Item;

import java.util.ArrayList;

public class User implements UserInterface {
    private String username;
    private String password;
    private double balance;
    private ArrayList<Item> itemsForSale;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
