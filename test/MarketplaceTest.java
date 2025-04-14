import ServerAndClient.Client;
import ServerAndClient.ClientInterface;
import ServerAndClient.Server;
import ServerAndClient.ServerInterface;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import Database.DatabaseInterface;
import Marketplace.Item;
import Marketplace.ItemInterface;
import Marketplace.Marketplace;
import Marketplace.MarketplaceInterface;
import Messaging.Messaging;
import Messaging.MessagingInterface;
import user.User;
import user.UserInterface;
import Database.Database;

import java.io.*;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A framework to run public test cases for the Marketplace class
 *
 *
 * Phase 2
 * @author Ronil Mitra
 * @version April 14, 2025
 */

@RunWith(Enclosed.class)
public class MarketplaceTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MarketplaceTest.TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Tests ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }

        }

    }

    @Nested
    class TestCase {
        @Test
        public void MarketplaceClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = Marketplace.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `Marketplace` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `Marketplace` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `Marketplace` extends `Object`!",
                    Object.class, superclass);
            Assert.assertTrue("Ensure that `Marketplace` implements `MarketplaceInterface`!",
                    Arrays.asList(superinterfaces).contains(MarketplaceInterface.class));

        }

        @Test
        public void buyItemTest() {
            User buyer = new User("buyer", "12345");
            User seller = new User("seller", "12345");
            Marketplace marketplace = new Marketplace(buyer);
            Item item = new Item("Item-1", "name", 10.0, seller);
            ArrayList<String> items = new ArrayList<>();
            ArrayList<String> users = new ArrayList<>();

            try (PrintWriter writer = new PrintWriter(new FileWriter("userProfileDatabase.txt", true));
                 BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    users.add(line);
                }

                writer.println(seller.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter("listedItemsDatabase.txt", true));
                 BufferedReader reader = new BufferedReader(new FileReader("soldItemsDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    items.add(line);
                }
                writer.println("Item-1,name,10.00,seller");
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            marketplace.buyItem(item);

            boolean foundInListedItems = false;
            boolean foundInSoldItems = false;

            try (BufferedReader listedItemReader = new BufferedReader(new FileReader("listedItemsDatabase.txt"));
                 BufferedReader soldItemReader = new BufferedReader(new FileReader("soldItemsDatabase.txt"))) {
                String line;
                while ((line = listedItemReader.readLine()) != null) {
                    if (line.equals(item.toString())) {
                        foundInListedItems = true;
                    }
                }

                while ((line = soldItemReader.readLine()) != null) {
                    if (line.equals(item.toString())) {
                        foundInSoldItems = true;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter("soldItemsDatabase.txt"))) {
                for (String line : items) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter("userProfileDatabase.txt"))) {
                for (String user : users) {
                    writer.println(user);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertFalse("Ensure that the item is not listed after being bought", foundInListedItems);
            Assert.assertTrue("Ensure that the item is bought", foundInSoldItems);
            Assert.assertEquals("Ensure that the buyer's balance is updated correctly",
                    490, buyer.getBalance(), 0.001);
            Assert.assertEquals("Ensure that the seller's balance is updated correctly",
                    510, seller.getBalance(), 0.001);
        }

        @Test
        public void listItemTest() {
            User user = new User("username", "12345");
            Marketplace marketplace = new Marketplace(user);
            ArrayList<String> contents = new ArrayList<>();
            boolean found = false;

            marketplace.listItem("Item", 10.0);

            try (BufferedReader reader = new BufferedReader(new FileReader("listedItemsDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                    String[] parts = line.split(",");
                    String itemName = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    String username = parts[3];
                    if (itemName.equals("Item") && price == 10.0 && username.equals("username")) {
                        found = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            contents.remove(contents.size() - 1);

            try (PrintWriter writer = new PrintWriter(new FileWriter("listedItemsDatabase.txt"))) {
                for (String line : contents) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertTrue("Ensure the item is listed correctly", found);
        }
    }
}