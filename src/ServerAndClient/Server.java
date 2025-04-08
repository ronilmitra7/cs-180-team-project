package ServerAndClient;
import Database.Database;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Database implements Runnable, ServerInterface {
    ServerSocket ss;
    Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public void run() {
        try {
            ss = new ServerSocket(4242);
            System.out.println("Waiting for connection...");
            socket = ss.accept();
            System.out.println("Accepted connection from client");
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        Thread t = new Thread(server);
        t.start();
    }
}
