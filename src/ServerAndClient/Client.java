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
                        try {
                            String enterSearchTerm = (String) ois.readObject();
                            System.out.println(enterSearchTerm);
                            oos.writeObject(scanner.nextLine());
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
                        break;

                    case "3":
                        //sell item
                        break;

                    case "4":
                        //message user
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
        } while (true);
        
    }

    public static void main(String[] args) {
        Client client = new Client();
        Thread thread = new Thread(client);
        thread.start();
    }
}
