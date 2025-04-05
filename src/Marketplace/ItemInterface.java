package Marketplace;

import user.User;

public interface ItemInterface {
    String getItemID();

    String getName();
    void setName(String name);

    double getPrice();
    void setPrice(double price);

    User getSeller();

    String toString();
}
