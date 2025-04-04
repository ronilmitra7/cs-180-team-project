package Marketplace;

import user.User;
import Database.Database;

import java.io.*;
import java.util.ArrayList;

public class Marketplace implements MarketplaceInterface {
    private final User user;

    public Marketplace(User user) {
        this.user = user;
    }

    public void buyItem(Item item) {
        Database database = new Database();
        ArrayList<String> contents = new ArrayList<>();

        if (!database.itemForSale(item)) {
            System.out.println("That item is not for sale");
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader("listedItems.txt"));
            PrintWriter writer = new PrintWriter(new FileWriter("soldItemsDatabase", true))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                    if (item.toString().equals(line)) {
                        String[] parts = line.split(",");
                        int price = Integer.parseInt(parts[2]);
                        String seller = parts[3];

                        if (user.getBalance() < price) {
                            System.out.println("You do not have enough money to buy this item");
                        } else {
                            writer.println(item.toString());
                            user.setBalance(user.getBalance() - price);
                            contents.remove(contents.size() - 1);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void listItem(Item item) {

    }
}
