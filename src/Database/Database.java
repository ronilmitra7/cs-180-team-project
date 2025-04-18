package Database;

import Marketplace.Item;
import user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Creates class Database that implements DatabaseInterface
 * Contains an introMenu() method for users to log in and sign up
 * Contains methods for logging in, signing up, and other methods that store and access data
 *
 * Phase 1
 * @author Ronil Mitra, Himashree Routhu
 * @version April 6, 2025
 */

public class Database implements DatabaseInterface {

    private boolean menu;

    private User user;

    public User introMenu() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Welcome to the Market Place!");
            System.out.println("1. Sign up");
            System.out.println("2. Log in");
            //System.out.println("3. Delete account");

            int choice = scanner.nextInt();
            scanner.nextLine();

            boolean loggedIn;
            if (choice == 1) {
                System.out.println("Enter your full name");
                String name = scanner.nextLine();

                System.out.println("Enter your email");
                String email = scanner.nextLine();

                System.out.println("Enter your username");
                String username = scanner.nextLine();

                System.out.println("Enter your password");
                String password = scanner.nextLine();

                if (signUp(new User(name, email, username, password))) {
                    menu = false;
                    loggedIn = true;
                    user = new User(name, email, username, password);
                } else {
                    menu = true;
                    loggedIn = false;
                }

            } else if (choice == 2) {
                System.out.println("Enter your username");
                String username = scanner.nextLine();

                System.out.println("Enter your password");
                String password = scanner.nextLine();

                if (logIn(new User(username, password))) {
                    menu = false;
                    loggedIn = true;

                    String name = "";
                    String email = "";

                    try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
                        String line;
                        while ((line = reader.readLine()) != null) {

                            String[] parts = line.split(",");

                            if (parts[2].equals(username)) {

                                name = parts[0];

                                email = parts[1];
                            }
                        }

                        user = new User(name, email, username, password);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    menu = true;
                    loggedIn = false;
                }
            } /*else if (choice == 3) {
                System.out.println("Enter your full name");
                String name = scanner.nextLine();

                System.out.println("Enter your email");
                String email = scanner.nextLine();

                System.out.println("Enter your username");
                String username = scanner.nextLine();

                System.out.println("Enter your password");
                String password = scanner.nextLine();

                if (deleteUser(new User(name, email, username, password))) {
                    menu = false;
                } else {
                    menu = true;
                }
            } */else {
                System.out.println("Invalid choice! Please try again");
            }
        } while (menu);

        return user;
    }

    public synchronized boolean logIn(User user) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (user.getUsername().equals(parts[2])) {
                    if (user.getPassword().equals(parts[3])) {
                        System.out.println("Successfully logged in!");
                        return true;
                    } else {
                        System.out.println("Incorrect password!");
                        return false;
                    }
                }
            }
            System.out.println("Username not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean signUp(User user) {
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
                    if (User.isValid(user)) {
                        writer.write(user.toString() + "\n");
                        System.out.println("Account successfully created!");
                        return true;
                    } else {
                        System.out.println("Account unable to be created.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public synchronized boolean deleteUser(User user) {
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
                }
            }

            reader.close();

            if (!found) {
                System.out.println("User not found");
                return false;
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter("userProfileDatabase.txt"));
            for (int i = 0; i < users.size(); i++) {
                writer.write(users.get(i) + "\n");
            }

            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public synchronized boolean itemForSale(Item item) {
        try (BufferedReader reader = new BufferedReader(new FileReader("listedItemsDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(item.getItemID())) {
                    System.out.println("Item is for sale");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Item is not listed");
        return false;
    }

    public synchronized boolean itemSold(Item item) {
        try (BufferedReader reader = new BufferedReader(new FileReader("soldItemsDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(item.getItemID())) {
                    System.out.println("Item was sold");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Item was not sold");
        return false;
    }

    public synchronized boolean userExists(String username) {
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

    public synchronized User searchUser(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(username)) {
                    return new User(parts[0], parts[1], parts[2], parts[3]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static synchronized ArrayList<String> listedItemSearch(String searchTerm) {
        ArrayList<String> matches = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("listedItemsDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains(searchTerm.toLowerCase())) {
                    matches.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }

    public static synchronized ArrayList<String> soldItemSearch(String searchTerm) {
        ArrayList<String> matches = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("soldItemsDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains(searchTerm.toLowerCase())) {
                    matches.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }
}
