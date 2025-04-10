package ServerAndClient;
import Database.Database;
import user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
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

                switch (choice) {
                    case "1":
                        //search user
                        break;

                    case "2":
                        //buy item
                        break;

                    case "3":
                        //sell item
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
                        break;
                    case "6":
                        //delete your account
                        break;

                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (IOException e) {
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
