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
import java.util.Random;//Imported Random

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

            Random random = new Random();//Defined Random object

            User user = (User)ois.readObject();

            System.out.println("Read: " + user);

            Database database = new Database();

            Messaging messaging = new Messaging(user);

            do {

                String choice = (String) ois.readObject();

                System.out.println("Read: " + choice);

                switch (choice) {
                    case "1":
                        //search user
                        break;

                    case "2":

                        Marketplace market = new Marketplace(user);

                        String itemSelected = (String) ois.readObject();

                        System.out.println("Read: " + itemSelected);

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

                        System.out.println("Write:" + itemSelectedList);

                        int itemPurchased =  (Integer) ois.readObject();

                        System.out.println("Read:" + itemPurchased);

                        String itemReturned = itemSelectedList.get(itemPurchased);

                        String[] itemReturnedList = itemReturned.split(",");

                        String confirmMessage = market.buyItem(new Item(itemReturnedList[0], itemReturnedList[1], Double.parseDouble(itemReturnedList[2].trim()), new User(itemReturnedList[3],"123")));

                        oos.writeObject(confirmMessage);

                        oos.flush();

                        System.out.println("Write:" + confirmMessage);

                        double modifiedBalance = user.getBalance();

                        oos.writeObject(modifiedBalance);

                        oos.flush();

                        System.out.println("Write:" + modifiedBalance);

                        oos.flush();

                        break;

                    case "3":

                        BufferedWriter bfr = new BufferedWriter(new FileWriter(new File("listedItemsDatabase.txt"),true));

                        String name = (String) ois.readObject();

                        System.out.println("Read: " + name);

                        double price = (double) ois.readObject();

                        System.out.println("Read: " + price);

                        String itemID = "";

                        boolean flag = true;

                        do {

                            flag = false;

                            itemID = user.getName() + name + random.nextInt(1000);

                            if (listedItemSearch(itemID).size() > 0) {

                                flag = true;

                            }

                        } while (flag == true);

                        Item itemForSale = new Item(itemID, name, price, user);

                        synchronized (gateKeeper) {

                            bfr.write(itemForSale.toString() + "\n");

                        }

                        String finalResponse = "Transaction Success";

                        oos.writeObject(finalResponse);

                        oos.flush();

                        bfr.close();

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
                        break;

                    case "6":
                        //delete account
                        break;

                    default:
                        break;
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
