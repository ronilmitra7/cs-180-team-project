package Marketplace;

import user.User;
import Database.Database;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

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
            try (BufferedReader reader = new BufferedReader(new FileReader("listedItemsDatabase.txt"));
                PrintWriter writer = new PrintWriter(new FileWriter("soldItemsDatabase.txt", true))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                    if (item.toString().equals(line)) {
                        String[] parts = line.split(",");
                        double price = Double.parseDouble(parts[2]);
                        User seller = item.getSeller();

                        if (user.getBalance() < price) {
                            System.out.println("You do not have enough money to buy this item");
                        } else {
                            user.setBalance(user.getBalance() - price);
                            contents.remove(contents.size() - 1);
                            seller.setBalance(seller.getBalance() + price);

                            PrintWriter pw = new PrintWriter(new FileWriter("listedItemsDatabase.txt"));
                            for (String content : contents) {
                                pw.println(content);
                            }
                            pw.close();
                        }
                    }
                }

                writer.println(item.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void listItem(String name, double price) {
        String itemID = UUID.randomUUID().toString();
        Item item = new Item(itemID, name, price, user);
        Database database = new Database();

        if (database.itemForSale(item)) {
            System.out.println("Item is already for sale");
        } else if (database.itemSold(item)) {
            System.out.println("Item was already sold");
        } else {
            try (PrintWriter writer = new PrintWriter(new FileWriter("listedItemsDatabase.txt", true))) {
                writer.println(item.toString());
                System.out.println("Item has been listed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
