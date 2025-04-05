package Marketplace;

import user.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Item implements ItemInterface {
    private String itemID;
    private String name;
    private double price;
    private User seller;

    public Item(String itemID, String name, double price, User seller) {
        this.itemID = itemID;
        this.name = name;
        this.price = price;
        this.seller = seller;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String toString() {
        return String.format("%s,%s,%.2f,%s", itemID, name, price, seller.getUsername());
    }
}
