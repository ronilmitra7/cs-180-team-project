package src.Database;

import src.user.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Database implements DatabaseInterface{
    private User user;
    private boolean menu;

    /*
    Basic implementation of logging in
    TODO: make restrictions for username and password
     */
    public void introMenu() {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Welcome to the Market Place!");
            System.out.println("1. Sign up");
            System.out.println("2. Log in");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Enter your full name");
                String name = scanner.nextLine();

                System.out.println("Enter your email");
                String email = scanner.nextLine();

                System.out.println("Enter your username");
                String username = scanner.nextLine();

                System.out.println("Enter your password");
                String password = scanner.nextLine();

                User newUser = new User(name, email, username, password);
            } else if (choice == 2) {

            } else {
                System.out.println("Invalid choice! Please try again");
            }
        } while (menu);
    }

    public static synchronized boolean logIn(User user) {
        return false;
    }

    public static synchronized boolean signUp(User user) {
        Scanner scanner = new Scanner(System.in);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userProfileDatabase.txt", true))) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
