package ServerAndClient;
import Database.Database;
import Marketplace.Item;
import user.User;

import java.awt.image.RasterOp;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client extends Database implements Runnable, ClientInterface {

    private Socket socket;

    private ObjectOutputStream oos;

    private ObjectInputStream ois;

    public void run() {

        Scanner scanner = new Scanner(System.in);

        try {

            System.out.println("Client connecting");

            socket = new Socket("localhost", 4242);

            System.out.println("Client connected");

            oos = new ObjectOutputStream(socket.getOutputStream());

            oos.flush();

            ois = new ObjectInputStream(socket.getInputStream());


        } catch (IOException e) {

            e.printStackTrace();

        }
        
        User user = introMenu();

        try {

            oos.writeObject(user);

            oos.flush();

        } catch (IOException e) {

            e.printStackTrace();

        }

        do {

            System.out.println("What would you like to do?");

            System.out.println("1. Search User");

            System.out.println("2. Buy Item");

            System.out.println("3. Sell Item");

            System.out.println("4. Message User");

            System.out.println("5. Check Balance");

            System.out.println("6. Delete your Account");

            String choice = scanner.nextLine();

            try {

                oos.writeObject(choice);

                oos.flush();

                System.out.println("Write: " + choice);

                switch (choice) {
                    case "1":
                        //search user
                        try {
                            System.out.println("Please enter the exact username of the user you'd like to search for.");
                            String enterSearchTerm = scanner.nextLine();
                            System.out.println(enterSearchTerm);
                            oos.writeObject(enterSearchTerm);
                            oos.flush();
                            System.out.println((String) ois.readObject());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException c) {
                            c.printStackTrace();
                        }
                        break;

                    case "2":
                        //buy item

                        boolean flag = true;

                        System.out.println("Type the name of the item you want to purchase: ");

                        String itemSelected = scanner.nextLine();

                        oos.writeObject(itemSelected);

                        oos.flush();

                        System.out.println("Write: " + itemSelected);

                        System.out.println("Here is the result for your searching:");

                        int itemSelectedListSize = 0;

                        try {

                            ArrayList<String> itemSelectedList = (ArrayList<String>) ois.readObject();

                            itemSelectedListSize = itemSelectedList.size();

                            if (itemSelectedList.isEmpty()) {

                                System.out.println("There no matched result");

                                break;

                            }

                            for (int i = 0; i < itemSelectedList.size(); i++) {

                                System.out.println((i + 1) + "|" + itemSelectedList.get(i).split(",")[1] + " "
                                        + itemSelectedList.get(i).split(",")[2] + " "
                                        + itemSelectedList.get(i).split(",")[3]);

                            }

                        } catch (ClassNotFoundException cne) {


                            System.out.println("Read: NONE| Exception Happened");

                            cne.printStackTrace();

                        }

                        int itemPurchased = 0;

                        do {

                            try {

                                flag = false;

                                System.out.println("Which One would you prefer? (Select the index)");

                                itemPurchased = Integer.parseInt(scanner.nextLine()) - 1;

                                if (itemPurchased > itemSelectedListSize - 1 || itemPurchased < 0) {

                                    System.out.println("Invalid Input!");

                                    flag = true;

                                }

                            } catch (NumberFormatException nfe) {

                                System.out.println("Invalid Input!");

                                flag = true;

                            }

                        } while (flag == true);


                        oos.writeObject(itemPurchased);

                        oos.flush();

                        try {

//                            String confirmMessage = (String) ois.readObject();
//
//                            System.out.println(confirmMessage);

                            double modifiedBalance = (double) ois.readObject();

                            System.out.println("Your current balance: " + modifiedBalance);

                        } catch (ClassNotFoundException cne) {

                            cne.printStackTrace();

                        }

                        break;

                    case "3":

                        System.out.println("Enter the name of the Item: ");

                        String name = scanner.nextLine();

                        boolean verify = true;

                        double price = 0;

                        do {

                            verify = false;

                            try {

                                System.out.println("Enter the price of the Item: ");

                                price = Double.parseDouble(scanner.nextLine());

                            } catch (NumberFormatException nfe) {

                                System.out.println("Please enter a valid price!");

                                verify = true;

                            }

                        } while (verify == true);

                        oos.writeObject(name);

                        oos.flush();

                        oos.writeObject(price);

                        oos.flush();

                        try {

                            System.out.println((String) ois.readObject());

                        } catch (ClassNotFoundException cnfe) {

                            cnfe.printStackTrace();

                        }

                        break;

                    case "4":
                        System.out.println("Enter the user you want to message:");
                        String username = scanner.nextLine();
                        oos.writeObject(username);
                        oos.flush();

                        System.out.println("Enter the message content:");
                        String message = scanner.nextLine();
                        oos.writeObject(message);
                        oos.flush();

                        break;

                    case "5":
                        //check balance
                        Double balance = (Double) ois.readObject();
                        System.out.println("Your current balance is " + balance);
                        break;
                    case "6":
                        //delete your account

                        System.out.println("Enter username if you are sure you want to delete your account");
                        String username1 = scanner.nextLine();
                        oos.writeObject(username1);
                        oos.flush();

                        System.out.println("test 1");
                        String response;
                        response = (String) ois.readObject();
                        System.out.println(response);

                        break;

                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Do you want to perform another action?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            String selection = scanner.nextLine();
            try {
                oos.writeObject(selection);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (selection.equals("1")) {
                continue;
            } else if (selection.equals("2")) {
                System.out.print("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice");
            }


        } while (true);
        
    }

    public static void main(String[] args) {

        Client client = new Client();

        Thread thread = new Thread(client);

        thread.start();


    }
}
