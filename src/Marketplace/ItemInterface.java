package Marketplace;

import user.User;

/**
 * Creates class Item that implements ItemInterface
 * Implemented by the Item
 *
 * Phase 1
 * @version April 6, 2025
 */

public interface ItemInterface {
    String getItemID();

    String getName();
    void setName(String name);

    double getPrice();
    void setPrice(double price);

    User getSeller();

    String toString();
}
