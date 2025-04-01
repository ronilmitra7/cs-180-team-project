package src.Messaging;

import src.Database.Database;
import src.user.User;

import java.util.ArrayList;

public class Messaging implements MessagingInterface {
    private User sender;

    public Messaging(User sender) {
        this.sender = sender;
    }

    public void sendMessage(String message, String recipient) {
        Database db = new Database();
        if (message.isEmpty()) {
            System.out.println("You can't send an empty message");
        }

        if (!db.userExists(recipient)) {
            System.out.println("User " + recipient + " does not exist. Message failed to send.");
        }
    }

    public void receiveMessage(String username) {

    }
}
