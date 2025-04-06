package user;

import Marketplace.Item;

import java.util.ArrayList;

/**
 * Creates an interface UserInterface
 * Implemented by the User class
 *
 * Phase 1
 * @version April 6, 2025
 */

public interface UserInterface {
    String getName();
    void setName(String name);

    String getEmail();
    void setEmail(String email);

    String getUsername();
    void setUsername(String username);

    String getPassword();
    void setPassword(String password);

    double getBalance();
    void setBalance(double balance);
}
