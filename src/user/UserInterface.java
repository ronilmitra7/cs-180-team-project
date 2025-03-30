package src.user;

import src.Marketplace.Item;

import java.util.ArrayList;

public interface UserInterface {
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
    double getBalance();
    void setBalance(double balance);
    ArrayList<Item> getItemsForSale();
    void setItemsForSale(ArrayList<Item> items);
}
