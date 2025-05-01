package Messaging;

import user.User;

import java.util.ArrayList;

/**
 * Creates class Messaging that implements MessagingInterface
 * Implemented by the Messaging class
 *
 * Phase 1
 * @author Ronil Mitra
 * @version April 6, 2025
 */

public interface MessagingInterface {
    void sendMessage(String message, String recipient);
    ArrayList<String> receiveMessage(String username);
}
