package src.Database;

import src.user.User;

import src.Marketplace.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database implements DatabaseInterface {

    public static final Object gateKeeper = new Object();

    private User user;
    private boolean menu;

    /*
    Basic implementation of logging in
    TODO: make restrictions for username and password in User.java
     */
    public void introMenu() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Welcome to the Market Place!");
            System.out.println("1. Sign up");
            System.out.println("2. Log in");
            System.out.println("3. Delete account");

            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter your full name");
            String name = scanner.nextLine();

            System.out.println("Enter your email");
            String email = scanner.nextLine();

            System.out.println("Enter your username");
            String username = scanner.nextLine();

            System.out.println("Enter your password");
            String password = scanner.nextLine();

            if (choice == 1) {
                //log in
            } else if (choice == 2) {
                //sign up
            } else if (choice == 3) {
                deleteUser(new User(name, email, username, password));
            } else {
                System.out.println("Invalid choice! Please try again");
            }
        } while (menu);
    }

    /*
    TODO: Read through the file, find the user's information, check if it matches
     */

    public static synchronized boolean logIn(User user) {
        Scanner scanner = new Scanner(System.in);

        try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static synchronized boolean signUp(User user) {
        Scanner scanner = new Scanner(System.in);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userProfileDatabase.txt",
                true))) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static synchronized void deleteUser(User user) {
        ArrayList<String> users = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line);
            }

            boolean found = false;
            for (int i = 0; i < users.size(); i++) {
                if (user.toString().equals(users.get(i))) {
                    users.remove(i);
                    System.out.println("User deleted");
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("User not found");
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("userProfileDatabase.txt"));
            for (int i = 0; i < users.size(); i++) {
                writer.write(users.get(i) + "\n");
            }

            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean ifExists(Item item) {

        String fileSource = "itemProfileDatabase.txt";

        synchronized (gateKeeper) {

            try (BufferedReader bfr = new BufferedReader(new FileReader(new File(fileSource)))) {

                ArrayList<String> databaseInformation = new ArrayList<>();

                while (true) {

                    String content = bfr.readLine();

                    if (content == null) {

                        break;

                    }

                    databaseInformation.add(content);

                }

                for(String i : databaseInformation) {

                    if (item.toString().equals(i)) {

                        return true;

                    }

                }

            } catch (IOException ioe) {

                ioe.printStackTrace();

            }

            return false;

        }

    }

    public void buyItem(Item item) {

        String fileSource = "itemProfileDatabase.txt";

        synchronized (gateKeeper) {

            try {

                BufferedReader bfr = new BufferedReader(new FileReader(new File(fileSource)));

                ArrayList<String> databaseInformation = new ArrayList<>();

                ArrayList<String> refreshedDatabaseInformation = new ArrayList<>();

                boolean flag = false;

                while (true) {

                    String content = bfr.readLine();

                    if (content == null) {

                        break;

                    }

                    databaseInformation.add(content);

                }

                for (String i : databaseInformation) {

                    if (item.toString().equals(i)) {

                        flag = true;

                        continue;

                    } else {

                        refreshedDatabaseInformation.add(i);

                    }

                }

                if (flag == false) {

                    System.out.println("Unable to purchase this since the Item you entered does not exist");

                } else {

                    //User behavior to be implemented

                    System.out.println("Successfully purchased the item!");

                }

                BufferedWriter bfw = new BufferedWriter(new FileWriter(new File(fileSource)));

                for (String i : refreshedDatabaseInformation) {

                    bfw.write(i + "\n");

                }

                bfr.close();

                bfw.close();

            } catch (IOException e) {

                e.printStackTrace();

            }
        }

    }

    public void sellItem(Item item) {

        String fileSource = "itemProfileDatabase.txt";

        synchronized (gateKeeper) {

            try (BufferedWriter bfw = new BufferedWriter(new FileWriter(new File(fileSource),true))) {

                if (this.ifExists(item) != true) {

                    //User behavior to be implemented

                    bfw.write(item.toString() + "\n");

                    System.out.println("Successfully Added the item");

                } else {

                    System.out.println("The item already existed ");

                }

            } catch (IOException ioe) {

                ioe.printStackTrace();

            }

        }

    }



    public static synchronized boolean userExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(username)) {
                    System.out.println("User found");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("User not found");
        return false;
    }
}
