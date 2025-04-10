package ServerAndClient;
import Database.Database;
import user.User;
import Messaging.Messaging;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Database implements Runnable, ServerInterface {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            User user = (User) ois.readObject();
            Database database = new Database();
            Messaging messaging = new Messaging(user);

            do {
                String choice = (String) ois.readObject();

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
                        String username;
                        String message;

                        try {
                            username = (String) ois.readObject();
                            message = (String) ois.readObject();
                            messaging.sendMessage(message, username);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;

                    case "5":
                        //check balance
                        break;

                    case "6":
                        //delete account
                        break;

                    default:
                        break;
                }

            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(4242);

            while (true) {
                System.out.println("Waiting for connection...");
                Socket socket = ss.accept();
                System.out.println("Connection accepted");

                Thread thread = new Thread(new Server(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
