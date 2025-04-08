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
            System.out.println("5. Delete your Account");

            String choice = scanner.nextLine();
        } while (true);
        
    }

    public static void main(String[] args) {
        Client client = new Client();
        Thread thread = new Thread(client);
        thread.start();
    }
}
