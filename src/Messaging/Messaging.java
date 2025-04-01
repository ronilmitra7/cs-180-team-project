package src.Messaging;

import src.user.User;

import java.util.ArrayList;

public class Messaging implements MessagingInterface {
    private User sender;

    public Messaging(User sender) {
        this.sender = sender;
    }

    public void sendMessage(String message, String recipient) {
        if (message.isEmpty()) {
            System.out.println("Can't send an empty message");
        }
    }

    public void receiveMessage(String username) {

    }
}
