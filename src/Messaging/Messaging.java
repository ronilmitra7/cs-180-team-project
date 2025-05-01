package Messaging;

import Database.Database;
import user.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Creates class Messaging that implements MessagingInterface
 * Contains methods for sending and receiving messages
 *
 * Phase 1
 * @author Ronil Mitra
 * @version April 6, 2025
 */

public class Messaging implements MessagingInterface {
    private final User sender;

    public Messaging(User sender) {
        this.sender = sender;
    }

    public void sendMessage(String message, String recipient) {
        Database database = new Database();

        if (message.isEmpty()) {
            System.out.println("You can't send an empty message");
        } else if (!database.userExists(recipient)) {
            System.out.printf("User %s does not exist. Message failed to send.", recipient);
        } else {

            try (PrintWriter writer = new PrintWriter(new FileWriter("messagesDatabase.txt", true))) {
                writer.printf("%s,%s,%s\n", message, recipient, sender.getUsername());
                System.out.printf("Message sent to %s%n", recipient);

            } catch (IOException e) {
                System.out.println("Failed to send message");
            }
        }
    }

    public ArrayList<String> receiveMessage(String username) {
        ArrayList<String> messages = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("messagesDatabase.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals(username)) {
                    messages.add(String.format("From %s: %s%n", parts[2], parts[0]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
