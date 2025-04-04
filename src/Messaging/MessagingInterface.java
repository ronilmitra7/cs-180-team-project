package Messaging;

import user.User;

import java.util.ArrayList;

public interface MessagingInterface {
    void sendMessage(String message, String recipient);
    String receiveMessage(String username);
}
