package src.Marketplace;

import src.user.User;

public interface ItemInterface {
    String getItemID();
    void setItemID(String itemID);
    String getName();
    void setName(String name);
    double getPrice();
    void setPrice(double price);
    User getSeller();
    void setSeller(User seller);
    boolean isForSale();
    void setForSale(boolean forSale);

}
