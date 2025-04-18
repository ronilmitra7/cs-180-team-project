package ServerAndClient;
import Database.Database;
import user.User;
import Messaging.Messaging;
import Marketplace.Marketplace;
import Marketplace.Item;

import java.io.*;//Imported IO
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Database implements Runnable, ServerInterface {

    private Socket socket;

    private ObjectOutputStream oos;

    private ObjectInputStream ois;

    public static final Object gateKeeper = new Object(); //Added a gateKeeper

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        try {

            oos = new ObjectOutputStream(socket.getOutputStream());

            ois = new ObjectInputStream(socket.getInputStream());

            User user = (User)ois.readObject();

            //user.setBalance(0);

            Database database = new Database();

            Messaging messaging = new Messaging(user);

            do {

                String choice = (String) ois.readObject();

                switch (choice) {
                    case "1":
                        //search user
                        String searchedName = (String) ois.readObject();
                        System.out.println(searchedName);
                        User searchedUser = database.searchUser(searchedName);
                        System.out.println(searchedUser);
                        String response = "";
                        if (searchedUser != null) {
                            String username = searchedUser.getUsername();
                            ArrayList<String> itemUserSells = listedItemSearch(username);
                            response += "You have found the user: " + username + "\n";
                            response += "This user is selling: \n";
                            //response.concat(String.format("You have found the user: %s\n", username));
                            //response.concat("This user is selling: \n");
                            if (itemUserSells.isEmpty()) {
                                response += "nothing \n";
                            } else {
                                for (int i = 0; i < itemUserSells.size(); i++) {
                                    response += itemUserSells.get(i) + "\n";
                                    //response.concat(String.format("%s\n", itemUserSells.get(i)));
                                }
                            }
                        } else {
                            response = "Can't find the user.";
                        }
                        oos.writeObject(response);
                        oos.flush();
                        break;


                    case "2":

                        Marketplace market = new Marketplace(user);

                        String itemSelected = (String) ois.readObject();

                        ArrayList<String> itemSelectedListtemp = listedItemSearch(itemSelected);

                        ArrayList<String> itemSelectedList = new ArrayList<>();

                        for (String i : itemSelectedListtemp) {

                            if (!i.split(",")[3].equals(user.getUsername())) {

                                itemSelectedList.add(i);

                            }

                        }

                        if(itemSelectedList.isEmpty()) {

                            oos.writeObject(itemSelectedList);

                            oos.flush();

                            System.out.println("There no matched result");

                            break;

                        }

                        oos.writeObject(itemSelectedList);

                        oos.flush();

                        int itemPurchased =  (Integer) ois.readObject();

                        String itemReturned = itemSelectedList.get(itemPurchased);

                        String[] itemReturnedList = itemReturned.split(",");

                        market.buyItem(new Item(itemReturnedList[0], itemReturnedList[1], Double.parseDouble(itemReturnedList[2].trim()), new User(itemReturnedList[3],"123")));

                        double modifiedBalance = user.getBalance();

                        oos.writeObject(modifiedBalance);

                        oos.flush();

                        oos.flush();

                        break;

                    case "3":

                        Marketplace marketSell = new Marketplace(user);

                        String name = (String) ois.readObject();

                        double price = (double) ois.readObject();

                        marketSell.listItem(name, price);

                        String finalResponse = "Transaction Success";

                        oos.writeObject(finalResponse);

                        oos.flush();
                        
                        //sell item
                        break;

                    case "4":

                        String username;

                        String message;

                        try {

                            username = (String) ois.readObject();

                            message = (String) ois.readObject();

                            messaging.sendMessage(message, username);

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                        break;

                    case "5":
                        //check balance
                        double balance = user.getBalance();
                        oos.writeObject(balance);
                        oos.flush();
                        break;

                    case "6":
                        //delete account

                        String usernameForDel = (String) ois.readObject();

                        System.out.println(usernameForDel);

                        if (user.getUsername().equals(usernameForDel)) {
                            boolean deleteSuccess = deleteUser(user);
                            if (deleteSuccess) {

                                String responseForDel = "Account successfully deleted";

                                oos.writeObject(responseForDel);

                                oos.flush();

                            } else {
                                oos.writeObject("Failed to delete account");
                                oos.flush();
                            }
                        }
                        else {
                            String messageForDel = "Incorrect username";
                            oos.writeObject(messageForDel);
                        }

                        oos.flush();
                        break;

                    default:
                        break;
                }

                String selection;

                try {
                    selection = (String) ois.readObject();
                    if (selection.equals("2")) {
                        break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

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

                System.out.println("Waiting for connection...");

                Socket socket = ss.accept();

                System.out.println("Connection accepted");

                Thread thread = new Thread(new Server(socket));

                thread.start();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
