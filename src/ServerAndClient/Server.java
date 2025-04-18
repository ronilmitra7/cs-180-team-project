package ServerAndClient;
import Database.Database;
import user.User;
import Messaging.Messaging;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
                        String searchedName = (String) ois.readObject();
                        System.out.println(searchedName);
                        User searchedUser = database.searchUser(searchedName);
                        System.out.println(searchedUser);
                        String response = "";
                        if (searchedUser != null) {
                            String username = searchedUser.getUsername();
                            ArrayList<String> itemUserSells = listedItemSearch(username);
                            response += "You have found the user: " + username + "\n";
                            response += "This user is selling: \n";
                            //response.concat(String.format("You have found the user: %s\n", username));
                            //response.concat("This user is selling: \n");
                            if (itemUserSells.isEmpty()) {
                                response += "nothing \n";
                            } else {
                                for (int i = 0; i < itemUserSells.size(); i++) {
                                    response += itemUserSells.get(i) + "\n";
                                    //response.concat(String.format("%s\n", itemUserSells.get(i)));
                                }
                            }
                        } else {
                            response = "Can't find the user.";
                        }
                        oos.writeObject(response);
                        oos.flush();
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
                        double balance = user.getBalance();
                        oos.writeObject(balance);
                        oos.flush();
                        break;

                    case "6":
                        //delete account

                        String username = (String) ois.readObject();

                        System.out.println(username);

                        if (user.getUsername().equals(username)) {
                            boolean deleteSuccess = deleteUser(user);
                            if (deleteSuccess) {

                                String response = "Account successfully deleted";

                                oos.writeObject(response);

                                oos.flush();

                                System.out.println("Write: " + response);
                            } else {
                                oos.writeObject("Failed to delete account");
                                oos.flush();
                            }
                        }
                        else {
                            String message = "Incorrect username";
                            oos.writeObject(message);
                        }

                        oos.flush();
                        break;

                    default:
                        break;
                }

                String selection;

                try {
                    selection = (String) ois.readObject();
                    if (selection.equals("2")) {
                        break;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
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
