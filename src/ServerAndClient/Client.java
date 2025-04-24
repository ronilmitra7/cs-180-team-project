package ServerAndClient;
import Database.Database;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the client class that gets the host and sets up the menu for what the user would like to do
 *
 *
 * Phase 2
 * @version April 19, 2025
 */

public class Client extends Database implements Runnable, ClientInterface {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private void showWelcomePage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255, 255));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0, 72, 255, 255));
        panel.setSize(800, 600);

        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        welcomePanel.setBounds(50, 50, 700, 450);
        welcomePanel.setBackground(new Color(0, 72, 255, 255));


        JLabel welcomeLabel = new JLabel("Welcome to the Marketplace!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        panel.add(welcomePanel);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(0, 72, 255, 255));
        menuBar.setLayout(new FlowLayout());

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(0, 72, 255, 255));
        menuBar.add(loginButton);

        JButton signupButton = new JButton("Signup");
        signupButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBackground(new Color(0, 72, 255, 255));
        menuBar.add(signupButton);

        panel.add(menuBar, BorderLayout.NORTH);
        frame.getContentPane().add(panel);

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signupPage(frame);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginPage(frame);
            }
        });

    }

    private void signupPage(JFrame frame) {

    }

    private void loginPage(JFrame frame) {

    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        try {
            socket = new Socket("localhost", 4242);
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Marketplace");
        showWelcomePage(frame);

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
            System.out.println("3. List Item");
            System.out.println("4. Message User");
            System.out.println("5. Check Balance");
            System.out.println("6. Delete your Account");

            String choice = scanner.nextLine();

            try {
                oos.writeObject(choice);
                oos.flush();

                switch (choice) {
                    case "1":
                        System.out.println("Enter the username you want to search for:");

                        String searchedUser = scanner.nextLine();
                        oos.writeObject(searchedUser);
                        oos.flush();

                        String result = (String) ois.readObject();
                        System.out.println(result);

                        break;

                    case "2":
                        boolean flag = true;

                        System.out.println("Type the name of the item you want to purchase: ");
                        String item = scanner.nextLine();
                        oos.writeObject(item);
                        oos.flush();
                    
                        System.out.println("Here are the items that match your search:");
                        int itemListSize = 0;
                        ArrayList<String> itemList = (ArrayList<String>) ois.readObject();
                        itemListSize = itemList.size();

                        if (itemList.isEmpty()) {
                            System.out.println("No matching items found");
                            break;
                        }

                        System.out.printf("%-5s %-20s %-10s %-15s%n", "No.", "Item Name", "Price", "Seller");
                        System.out.println("-------------------------------------------------------------");

                        for (int i = 0; i < itemList.size(); i++) {
                            String[] parts = itemList.get(i).split(",");

                            String itemName = parts[1];
                            String price = parts[2];
                            String seller = parts[3];

                            System.out.printf("%-5d %-20s %-10s %-15s%n", i + 1, itemName, price, seller);
                        }

                        int itemNumber;

                        do {
                            flag = false;

                            System.out.println("Enter the number for the item you want to buy");
                            itemNumber = scanner.nextInt() - 1;
                            scanner.nextLine();

                            if (itemNumber > itemListSize - 1 || itemNumber < 0) {
                                System.out.println("Invalid Input!");
                                flag = true;
                            }

                        } while (flag);

                        double tempBalance = (double) ois.readObject();

                        oos.writeObject(itemNumber);
                        oos.flush();

                        double modifiedBalance = (double) ois.readObject();

                        if (tempBalance == modifiedBalance) {
                            System.out.println("Transaction Failed! Your balance is not enough!");

                        } else {
                            System.out.printf("Transaction successful! Your current balance: %s%n", modifiedBalance);
                        }

                        break;

                    case "3":
                        System.out.println("Enter the name of the item: ");
                        String name = scanner.nextLine();

                        double price;
                        while (true) {
                            System.out.print("Enter the price of the item: ");
                            try {
                                price = Double.parseDouble(scanner.nextLine());
                                if (price <= 0) {
                                    System.out.println("Price must be greater than zero.");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input. Please enter a number.");
                            }
                        }

                        oos.writeObject(name);
                        oos.flush();
                        oos.writeObject(price);
                        oos.flush();

                        System.out.println("Item was listed");

                        break;

                    case "4":
                        Database database = new Database();
                        String recipient;
                        while (true) {
                            System.out.println("Enter the user you want to message:");
                            recipient = scanner.nextLine();

                            if (!database.userExists(recipient)) {
                                System.out.printf("User %s does not exist.%n", recipient);
                            } else {
                                break;
                            }
                        }

                        oos.writeObject(recipient);
                        oos.flush();

                        String message;
                        while (true) {
                            System.out.println("Enter the message content:");
                            message = scanner.nextLine();

                            if (message.isEmpty()) {
                                System.out.println("You can't send an empty message");
                            } else {
                                break;
                            }
                        }

                        oos.writeObject(message);
                        oos.flush();

                        System.out.printf("Message sent to %s%n", recipient);

                        break;

                    case "5":
                        Double balance = (Double) ois.readObject();
                        System.out.println("Your current balance is " + balance);
                        break;

                    case "6":
                        System.out.println("Enter your password if you are sure you want to delete your account");
                        String password = scanner.nextLine();
                        oos.writeObject(password);
                        oos.flush();

                        String response;
                        response = (String) ois.readObject();
                        System.out.println(response);

                        break;

                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
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