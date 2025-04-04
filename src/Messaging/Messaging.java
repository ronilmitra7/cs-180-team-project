package Messaging;

import Database.Database;
import user.User;

import java.io.*;
import java.util.ArrayList;

public class Messaging implements MessagingInterface {
    private final User sender;

    public Messaging(User sender) {
        this.sender = sender;
    }

    public void sendMessage(String message, String recipient) {
        if (message.isEmpty()) {
            System.out.println("You can't send an empty message");
        }

        if (!Database.userExists(recipient)) {
            System.out.printf("User %s does not exist. Message failed to send.", recipient);
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("messagesDatabase.txt", true))) {
            writer.printf("%s,%s,%s", message, recipient, sender.getUsername());
            System.out.printf("Message sent to %s%n", recipient);

        } catch (IOException e) {
            System.out.println("Failed to send message");
        }
    }

    public String receiveMessage(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("messagesDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(username)) {
                    return String.format("From %s: %s%n", username, parts[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Message not found";
    }
}
