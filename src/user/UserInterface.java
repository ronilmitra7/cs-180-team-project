package src.user;

import src.Marketplace.Item;

import java.util.ArrayList;

public interface UserInterface {
    String getName();
    void setName(String name);

    String getEmail();
    void setEmail(String email);

    String getUsername();
    void setUsername(String username);

    String getPassword();
    void setPassword(String password);

    double getBalance();
    void setBalance(double balance);

    ArrayList<Item> getItemsForSale();
    void addItem(Item item);
}
