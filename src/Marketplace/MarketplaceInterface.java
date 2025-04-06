package Marketplace;

/**
 * Creates an interface MarketplaceInterface
 * Implemented by the Marketplace class
 *
 * Phase 1
 * @author Ronil Mitra
 * @version April 6, 2025
 */

public interface MarketplaceInterface {
    void buyItem(Item item);
    void listItem(String name, double price);
}
