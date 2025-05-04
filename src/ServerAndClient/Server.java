package ServerAndClient;

import Database.Database;
import Marketplace.Item;
import user.User;
import Messaging.Messaging;
import Marketplace.Marketplace;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This is the server class where we establish where the user needs to go
 * <p>
 * <p>
 * Phase 2
 *
 * @version April 19, 2025
 */

public class Server extends Database implements Runnable, ServerInterface {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            User user = null;
            Database database = new Database();

            do {
                if (user == null) {
                    user = (User) ois.readObject();
                }

                Messaging messaging = new Messaging(user);

                String choice = (String) ois.readObject();

                switch (choice) {
                    case "1":
                        String searchedName = (String) ois.readObject();
                        User searchedUser = database.searchUser(searchedName);

                        String response = "";
                        if (searchedUser != null) {
                            String username = searchedUser.getUsername();
                            ArrayList<String> userItems = listedItemSearch(username);
                            response += "You have found the user: " + username + "\n";

                            if (userItems.isEmpty()) {
                                response += "This user isn't selling anything \n";
                            } else {
                                response += "This user is selling: \n";
                                for (int i = 0; i < userItems.size(); i++) {
                                    String itemRep = userItems.get(i);
                                    int begin = itemRep.indexOf(",") + 1;
                                    int end = itemRep.lastIndexOf(",");
                                    itemRep = itemRep.substring(begin, end);
                                    String item = itemRep.substring(0, itemRep.indexOf(",")) + ": ";
                                    String price = "$" + itemRep.substring(itemRep.indexOf(",") + 1, itemRep.length());
                                    itemRep = item + price;
                                    response += itemRep + "\n";
                                }
                            }
                        } else {
                            response = "User not found";
                        }

                        oos.writeObject(response);
                        oos.flush();

                        break;

                    case "2":
                        Marketplace marketplace = new Marketplace(user);
                        String item = (String) ois.readObject();

                        ArrayList<String> tempItemList = listedItemSearch(item);
                        ArrayList<String> itemList = new ArrayList<>();

                        for (String i : tempItemList) {
                            if (!i.split(",")[3].equals(user.getUsername())) {
                                itemList.add(i);
                            }
                        }

                        oos.writeObject(itemList);
                        oos.flush();

                        double originalBalance = user.getBalance();

                        oos.writeObject(originalBalance);
                        oos.flush();

                        boolean isEmpty = (Boolean) ois.readObject();

                        if (isEmpty) {
                            break;
                        }

                        int itemPurchasedIndex = (Integer) ois.readObject();
                        String itemPurchased = itemList.get(itemPurchasedIndex);
                        String[] parts = itemPurchased.split(",");

                        User seller = database.searchUser(parts[3]);
                        String itemID = parts[0];
                        String itemName = parts[1];
                        double price = Double.parseDouble(parts[2]);

                        marketplace.buyItem(new Item(itemID, itemName, price, seller));

                        double modifiedBalance = user.getBalance();
                        oos.writeObject(modifiedBalance);
                        oos.flush();

                        break;

                    case "3":
                        Marketplace mp = new Marketplace(user);

                        String name = (String) ois.readObject();
                        price = (double) ois.readObject();

                        mp.listItem(name, price);

                        break;

                    case "4":
                        String username;
                        String message;

                        username = (String) ois.readObject();
                        message = (String) ois.readObject();
                        messaging.sendMessage(message, username);

                        break;

                    case "5":
                        username = (String) ois.readObject();

                        ArrayList<String> messages = messaging.receiveMessage(username);
                        oos.writeObject(messages);
                        oos.flush();
                        break;

                    case "6":
                        double balance = user.getBalance();
                        oos.writeObject(balance);
                        oos.flush();
                        break;

                    case "7":
                        String password = (String) ois.readObject();
                        User fullUser = (User) ois.readObject();

                        if (fullUser.getPassword().equals(password)) {
                            boolean deleteSuccess = deleteUser(fullUser);
                            if (deleteSuccess) {
                                response = "Account successfully deleted";
                                oos.writeObject(response);
                                oos.flush();

                            } else {
                                oos.writeObject("Failed to delete account");
                                oos.flush();
                            }
                        } else {
                            message = "Incorrect password";
                            oos.writeObject(message);
                            oos.flush();
                        }

                        break;

                    case "8":
                        user = null;
                        break;

                    default:
                        break;
                }

                /*String selection;

                try {
                    selection = (String) ois.readObject();
                    if (selection.equals("2")) {
                        break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();*
                }*/

            } while (true);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(4242);

            while (true) {
                Socket socket = ss.accept();

                Thread thread = new Thread(new Server(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}