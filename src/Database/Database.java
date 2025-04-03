package src.Database;

import src.Marketplace.Item;
import src.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database implements DatabaseInterface {
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
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (user.getUsername().equals(parts[2])) {
                    found = true;
                    if (user.getPassword().equals(parts[3])) {
                        System.out.println("Successfully logged in!");
                        return true;
                    } else {
                        System.out.println("Incorrect password!");
                        return false;
                    }
                } else {
                    continue;
                }
            }
            if (!found) {
                System.out.println("Username not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static synchronized boolean signUp(User user) {
        Scanner scanner = new Scanner(System.in);

        try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (user.getUsername().equals(parts[2])) {
                    found = true;
                    System.out.println("Username is already in use!");
                } else {
                    continue;
                }
            }
            if (!found) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("userProfileDatabase.txt",
                        true))) {
                    writer.write(user.toString());
                    writer.newLine();
                    System.out.println("Account successfully created!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public synchronized void addItemDatabase(Item item) {
        File itemDatabaseFile = new File("itemProfileDatabase.txt");
        try {
            if (!itemDatabaseFile.exists()) {
                itemDatabaseFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        item.itemToString();
    }

    public synchronized File itemSearch(String searchTerm) {
        //
        File searchMatches = new File("SearchMatches.txt");
        try {
            if (!searchMatches.exists()) {
                searchMatches.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader bfr = new BufferedReader(new FileReader("itemProfileDatabase.txt"))) {
            ArrayList<String> file = new ArrayList<String>();
            String line = bfr.readLine();
            while (line != null) {
                file.add(line);
                line = bfr.readLine();
            }
            FileOutputStream fos = new FileOutputStream(searchMatches, true);
            PrintWriter pw = new PrintWriter(fos);

            for (int i = 0; i < file.size(); i++) {
                if (file.get(i).contains(searchTerm)) {
                    pw.println(file.get(i));
                }
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchMatches;
    }

}
