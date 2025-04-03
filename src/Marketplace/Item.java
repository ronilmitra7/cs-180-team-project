package src.Marketplace;

import src.user.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Item implements ItemInterface {

    private String itemID;

    private String name;

    private double price;

    private User seller;

    private boolean forSale;

    public Item(String itemID, String name, double price, User seller, boolean forSale) {

        this.itemID = itemID;

        this.name = name;

        this.price = price;

        this.seller = seller;

        this.forSale = forSale;

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

    public boolean isForSale() {
        return forSale;
    }

    public void setForSale(boolean forSale) {
        this.forSale = forSale;
    }

    public String toString() {

        String itemString = this.itemID + "," + this.name + "," + this.seller + "," + this.price;

        return itemString;
    }

}
