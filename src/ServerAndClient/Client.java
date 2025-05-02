package ServerAndClient;
import Database.Database;
import user.User;
import Messaging.Messaging;

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
    private User user;
    private ArrayList<String> list;
    private double originalBalance = 0.0;

    private void welcomePage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255));
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

        JButton loginButton = new JButton("Log in");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(0, 72, 255, 255));

        menuBar.add(loginButton);

        JButton signupButton = new JButton("Sign up");
        signupButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        signupButton.setForeground(Color.WHITE);
        signupButton.setBackground(new Color(0, 72, 255, 255));

        menuBar.add(signupButton);

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

        panel.add(menuBar, BorderLayout.NORTH);
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void signupPage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255, 255));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 72, 255, 255));
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Enter your full name");
        nameLabel.setBounds(100, 100, 250, 30);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(370, 100, 300, 30);
        panel.add(nameField);

        JLabel emailLabel = new JLabel("Enter your email");
        emailLabel.setBounds(100, 160, 300, 30);
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        emailLabel.setForeground(Color.WHITE);
        panel.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(370, 160, 300, 30);
        panel.add(emailField);

        JLabel usernameLabel = new JLabel("Enter your username");
        usernameLabel.setBounds(100, 220, 250, 30);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(370, 220, 300, 30);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Enter your password");
        passwordLabel.setBounds(100, 280, 250, 30);
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        passwordLabel.setForeground(Color.WHITE);

        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(370, 280, 300, 30);
        panel.add(passwordField);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        signUpButton.setBounds(230, 360, 150, 40);
        panel.add(signUpButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backButton.setBounds(410, 360, 150, 40);
        panel.add(backButton);

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                Database database = new Database();

                if (database.userExists(username)) {
                    JOptionPane.showMessageDialog(frame, "Username already in use", null,
                            JOptionPane.ERROR_MESSAGE);
                } else if (username.contains(",") || username.contains(" ")) {
                    JOptionPane.showMessageDialog(frame, "Username can't contain commas or spaces", null,
                            JOptionPane.ERROR_MESSAGE);
                } else if (username.length() > 11) {
                    JOptionPane.showMessageDialog(frame, "Username cannot exceed 11 characters", null,
                            JOptionPane.ERROR_MESSAGE);
                } else if (password.contains(" ")) {
                    JOptionPane.showMessageDialog(frame, "Password can't contain spaces", null,
                            JOptionPane.ERROR_MESSAGE);
                } else if (password.length() > 11) {
                    JOptionPane.showMessageDialog(frame, "Password cannot exceed 11 characters", null,
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    user = new User(name, email, username, password);
                    database.signUp(user);


                    try {
                        oos.writeObject(user);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }


                    menuPage(frame);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                welcomePage(frame);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void loginPage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255, 255));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 72, 255, 255));
        panel.setLayout(null);

        JLabel usernameLabel = new JLabel("Enter your username");
        usernameLabel.setBounds(100, 150, 250, 30);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));

        usernameLabel.setForeground(Color.WHITE);

        panel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(370, 150, 300, 30);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Enter your password");
        passwordLabel.setBounds(100, 210, 250, 30);
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));

        passwordLabel.setForeground(Color.WHITE);

        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(370, 210, 300, 30);
        panel.add(passwordField);

        JButton logInButton = new JButton("Log In");
        logInButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logInButton.setBounds(230, 300, 150, 40);
        panel.add(logInButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backButton.setBounds(410, 300, 150, 40);
        panel.add(backButton);

        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                Database database = new Database();

                user = new User(username, password);

                if (database.logIn(user)) {

                    try {
                        oos.writeObject(user);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }


                    menuPage(frame);
                } else {
                    JOptionPane.showMessageDialog(frame, "Make sure you enter in your username and password correctly",
                            null, JOptionPane.ERROR_MESSAGE);
                    user = null;
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                welcomePage(frame);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    private void menuPage(JFrame frame) {
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

        JPanel grid = new JPanel(new GridLayout(4, 2, 10, 10));
        grid.setBackground(new Color(0, 72, 255));
        grid.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));


        JButton searchButton = new JButton("Search User");
        JButton buyButton = new JButton("Buy Item");
        JButton listButton = new JButton("List Item");
        JButton messageButton = new JButton("Message User");
        JButton checkMessageButton = new JButton("Check Messages");
        JButton balanceButton = new JButton("Check Balance");
        JButton deleteButton = new JButton("Delete your Account");

        JButton logoutButton = new JButton("Log Out");

        grid.add(searchButton);
        grid.add(buyButton);
        grid.add(listButton);
        grid.add(messageButton);
        grid.add(checkMessageButton);
        grid.add(balanceButton);
        grid.add(deleteButton);
        grid.add(logoutButton);

        messageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                messageUserPage(frame);
            }
        });

        checkMessageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                receiveMessagePage(frame);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchUserPage(frame);
            }
        });


        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buyItemPage(frame);
            }
        });

        listButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listItemPage(frame);
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                balancePage(frame);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deletePage(frame);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                welcomePage(frame);
            }
        });

        panel.add(grid);

        frame.getContentPane().add(panel);
        panel.setVisible(true);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    private void searchUserPage(JFrame frame) {
        frame.getContentPane().removeAll();
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());


        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255, 255));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 72, 255, 255));
        panel.setSize(800, 600);

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setBounds(70, 50, 120, 30);
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        panel.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(200, 50, 400, 30);
        panel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 19));
        searchButton.setBounds(320, 100, 160, 35);
        panel.add(searchButton);

        JTextArea searchResult = new JTextArea();
        searchResult.setEditable(false);
        searchResult.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchResult.setLineWrap(true);
        searchResult.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(searchResult);
        scrollPane.setBounds(200, 150, 400, 250);
        panel.add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 19));
        backButton.setBounds(320, 420, 160, 35);
        panel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuPage(frame);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = searchField.getText();

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Username can't be empty", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    System.out.println("Waiting");
                    oos.writeObject("1");
                    oos.flush();
                    System.out.println("Sent");

                    System.out.println("Waiting");
                    oos.writeObject(username);
                    oos.flush();
                    System.out.println("Sent");

                    System.out.println("Waiting");
                    String results = (String) ois.readObject();
                    System.out.println("Received");
                    searchResult.setText(results);

                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        frame.add(panel);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();


        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBackground(new Color(0, 72, 255, 255));
        searchPanel.setSize(800, 45);
        content.add(searchPanel, BorderLayout.NORTH);

        JTextField searchField = new JTextField("Search a User");
        searchField.setBounds(0, 0, 300, 45);
        searchPanel.add(searchField);

        JTextPane displaySearches = new JTextPane();
        displaySearches.setSize(800, 555);
        content.add(displaySearches, BorderLayout.CENTER);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(300, 45, 300, 45);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText();
                displaySearches.setText("");
                //Todo: do smth with searchTerm ^
            }
        });

        content.add(searchButton, BorderLayout.SOUTH);
        frame.add(content);
        frame.setVisible(true);

    }

    private void buyItemPage(JFrame frame) {

    }

    private void listItemPage(JFrame frame) {

    }

    private void messageUserPage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255, 255));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(0, 72, 255, 255));
        panel.setSize(800, 600);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(100, 100, 500, 50);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(100, 250, 300, 45);
        panel.add(usernameField);

        JLabel messageLabel = new JLabel("Message: ");
        messageLabel.setBounds(100, 120, 500, 200);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        panel.add(messageLabel);

        JTextField messageField = new JTextField();
        messageField.setBounds(100, 250, 300, 45);
        panel.add(messageField);

        JButton sendButton = new JButton("Send Message");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 19));
        sendButton.setBounds(300, 400, 200, 40);
        panel.add(sendButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String message = messageField.getText();
                Database database = new Database();

                if (message.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "You can't send an empty message", null,
                            JOptionPane.ERROR_MESSAGE);
                }

                if (database.userExists(username)) {
                    JOptionPane.showMessageDialog(frame, "Message Sent", null,
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String error = String.format("User %s doesn't exist", username);
                    JOptionPane.showMessageDialog(frame, error, null, JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);

    }

    private void receiveMessagePage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255, 255));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(new Color(0, 72, 255, 255));
        panel.setSize(800, 600);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(100, 100, 500, 50);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(100, 250, 300, 45);
        panel.add(usernameField);

        JButton checkMessagesButton = new JButton("Check Messages");
        checkMessagesButton.setFont(new Font("Segoe UI", Font.BOLD, 19));
        checkMessagesButton.setBounds(300, 400, 200, 40);
        panel.add(checkMessagesButton);

        checkMessagesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();

                Database database = new Database();
                Messaging messaging = new Messaging(user);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }


    private void buyItemPage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255, 255));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 72, 255, 255));
        panel.setSize(800, 600);

        JLabel itemLabel = new JLabel("Enter item name: ");
        itemLabel.setBounds(100, 30, 250, 30);
        itemLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        itemLabel.setForeground(Color.WHITE);
        panel.add(itemLabel);

        JTextField itemField = new JTextField();
        itemField.setBounds(100, 60, 300, 30);
        panel.add(itemField);

        JTextArea itemArea = new JTextArea();
        itemArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        itemArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(itemArea);
        scrollPane.setBounds(100, 110, 600, 180);
        panel.add(scrollPane);

        JLabel selectionLabel = new JLabel("Enter item number to buy: ");
        selectionLabel.setBounds(100, 310, 250, 30);
        selectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        selectionLabel.setForeground(Color.WHITE);
        panel.add(selectionLabel);

        JTextField selectionField = new JTextField();
        selectionField.setBounds(100, 340, 80, 30);
        panel.add(selectionField);

        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        searchButton.setBounds(420, 60, 120, 30);
        panel.add(searchButton);

        JButton buyButton = new JButton("Buy");
        buyButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        buyButton.setBounds(100, 375, 80, 30);
        panel.add(buyButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backButton.setBounds(325, 420, 150, 40);
        panel.add(backButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = itemField.getText();

                if (name == null || name.equals("")) {
                    JOptionPane.showMessageDialog(frame, "Item name can't be empty", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    oos.writeObject("2");
                    oos.flush();
                    oos.writeObject(name);
                    oos.flush();

                    list = (ArrayList<String>) ois.readObject();
                    originalBalance = (Double) ois.readObject();

                    if (list.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "No items found", null, JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String results = String.format("%-5s %-20s %-10s %-15s%n", "No.", "Item Name", "Price", "Seller");
                    results += "-------------------------------------------------------------\n";

                    for (int i = 0; i < list.size(); i++) {
                        String[] parts = list.get(i).split(",");

                        String itemName = parts[1];
                        String price = parts[2];
                        String seller = parts[3];

                        results += String.format("%-5d %-20s %-10s %-15s%n", i + 1, itemName, price, seller);
                    }

                    itemArea.setText(results);

                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selection;

                try {
                    selection = Integer.parseInt(selectionField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Selection", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (selection < 1 || selection > list.size()) {
                    JOptionPane.showMessageDialog(frame, "Invalid Selection", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                selection--;

                double newBalance;

                try {
                    oos.writeObject(selection);
                    oos.flush();
                    newBalance = (Double) ois.readObject();

                    if (originalBalance == newBalance) {
                        JOptionPane.showMessageDialog(frame, "You don't have enough money to buy this item",
                                null, JOptionPane.ERROR_MESSAGE);
                        return;
                    }


                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(frame, "Successfully purchased item", null, JOptionPane.INFORMATION_MESSAGE);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuPage(frame);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();

    }

    private void listItemPage(JFrame frame) {
        frame.getContentPane().removeAll();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255, 255));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 72, 255, 255));
        panel.setSize(800, 600);

        JLabel itemNameLabel = new JLabel("Enter Item Name: ");
        itemNameLabel.setBounds(100, 150, 250, 30);
        itemNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        panel.add(itemNameLabel);

        JTextField itemNameField = new JTextField();
        itemNameField.setBounds(370, 150, 300, 30);
        panel.add(itemNameField);

        JLabel priceLabel = new JLabel("Enter Item Price: ");
        priceLabel.setBounds(100, 210, 250, 30);
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        panel.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(370, 210, 300, 30);
        panel.add(priceField);

        JButton listItemButton = new JButton("List Item");
        listItemButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        listItemButton.setBounds(230, 300, 150, 40);
        panel.add(listItemButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backButton.setBounds(410, 300, 150, 40);
        panel.add(backButton);

        listItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = itemNameField.getText();
                double price;

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Item name can't be empty", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    price = Double.parseDouble(priceField.getText());

                    if (price <= 0) {
                        JOptionPane.showMessageDialog(frame, "Price can't be 0 or lower", null, JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid price", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    oos.writeObject("3");
                    oos.flush();
                    oos.writeObject(name);
                    oos.flush();
                    oos.writeObject(price);
                    oos.flush();

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(frame, "Item Listed", null, JOptionPane.INFORMATION_MESSAGE);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuPage(frame);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();

    }

    private void messageUserPage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255, 255));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 72, 255, 255));
        panel.setSize(800, 600);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(80, 50, 120, 30);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 50, 480, 30);
        panel.add(usernameField);

        JLabel messageLabel = new JLabel("Message: ");
        messageLabel.setBounds(100, 100, 100, 30);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        messageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(messageLabel);

        JTextArea messageArea = new JTextArea();
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBounds(200, 100, 480, 250);
        panel.add(scrollPane);

        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 19));
        sendButton.setBounds(250, 400, 130, 40);
        panel.add(sendButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 19));
        backButton.setBounds(420, 400, 130, 40);
        panel.add(backButton);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String message = messageArea.getText();
                Database database = new Database();

                if (message.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "You can't send an empty message", null,
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!database.userExists(username)) {
                    String error = String.format("User %s doesn't exist", username);
                    JOptionPane.showMessageDialog(frame, error, null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    oos.writeObject("4");
                    oos.flush();
                    oos.writeObject(username);
                    oos.flush();
                    oos.writeObject(message);
                    oos.flush();

                    JOptionPane.showMessageDialog(frame, "Message Sent", null,
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuPage(frame);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();

    }

    private void receiveMessagePage(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBackground(new Color(0, 72, 255, 255));
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(0, 72, 255, 255));
        panel.setSize(800, 600);

        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setBounds(70, 50, 120, 30);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 19));
        panel.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(200, 50, 400, 30);
        panel.add(usernameField);

        JButton checkMessagesButton = new JButton("Check");
        checkMessagesButton.setFont(new Font("Segoe UI", Font.BOLD, 19));
        checkMessagesButton.setBounds(320, 100, 160, 35);
        panel.add(checkMessagesButton);

        JTextArea messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBounds(200, 150, 400, 250);
        panel.add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 19));
        backButton.setBounds(320, 420, 160, 35);
        panel.add(backButton);

        checkMessagesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                Database database = new Database();
                Messaging messaging = new Messaging(user);

                if (!database.userExists(username)) {
                    JOptionPane.showMessageDialog(frame, "User not found", null, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    oos.writeObject("5");
                    oos.flush();
                    oos.writeObject(username);
                    oos.flush();

                    ArrayList<String> messages = (ArrayList<String>) ois.readObject();

                    if (messages.isEmpty()) {
                        messageArea.setText("No messages found");
                    } else {
                        for (String message : messages) {
                            messageArea.append(message + "\n");
                        }
                    }

                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuPage(frame);
            }
        });

        frame.add(panel);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
    }

    private void balancePage(JFrame frame) {

    }

    private void deletePage(JFrame frame) {


        content.add(deleteButton, BorderLayout.SOUTH);
        frame.add(content);
        frame.setVisible(true);
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
        welcomePage(frame);

        user = introMenu();
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
            System.out.println("5. Check Messages");
            System.out.println("6. Check Balance");
            System.out.println("7. Delete your Account");

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
                        Database db = new Database();
                        String username;

                        while (true) {
                            System.out.println("Enter the user you want to check messages from:");
                            username = scanner.nextLine();

                            if (!db.userExists(username)) {
                                System.out.printf("User %s does not exist.%n", username);
                            } else {
                                break;
                            }
                        }

                        oos.writeObject(username);
                        oos.flush();

                        ArrayList<String> messages = (ArrayList<String>) ois.readObject();

                        if (messages.isEmpty()) {
                            System.out.println("No messages found");
                        } else {
                            for (String line : messages) {
                                System.out.println(line);
                            }
                        }

                        break;

                    case "6":
                        Double balance = (Double) ois.readObject();
                        System.out.println("Your current balance is " + balance);
                        break;

                    case "7":
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