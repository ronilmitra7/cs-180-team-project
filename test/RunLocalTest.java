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

import javax.swing.*;
import java.io.*;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

//TODO: make sure there is an assert for each method except for accessors and mutators
/**
 * A framework to run public test cases for the group project.
 *
 *
 * Phase 1
 * @version Nov 2, 2024
 */

@RunWith(Enclosed.class)
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
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
        public void DatabaseClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = Database.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `Database` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `Database` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `Database` extends `Object`!",
                    Object.class, superclass);
            Assert.assertTrue("Ensure that `Database` implements `DatabaseInterface`!",
                    Arrays.asList(superinterfaces).contains(DatabaseInterface.class));

        }

        @Test
        public void MessagingClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = Messaging.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `Messaging` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `Messaging` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `Messaging` extends `Object`!",
                    Object.class, superclass);
            Assert.assertTrue("Ensure that `Messaging` implements `MessagingInterface`!",
                    Arrays.asList(superinterfaces).contains(MessagingInterface.class));

        }

        @Test
        public void UserClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = User.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `User` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `User` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `User` extends `Object`!",
                    Object.class, superclass);
            Assert.assertTrue("Ensure that `User` implements `UserInterface`!",
                    Arrays.asList(superinterfaces).contains(UserInterface.class));

        }

        @Test
        public void ItemClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = Item.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `Item` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `Item` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `Item` extends `Object`!",
                    Object.class, superclass);
            Assert.assertTrue("Ensure that `Item` implements `ItemInterface`!",
                    Arrays.asList(superinterfaces).contains(ItemInterface.class));

        }

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
        public void loginTest() {
            User user = new User("username", "12345");
            Database database = new Database();

            boolean loginResult = database.logIn(user);
            boolean userExistsInFile = false;

            try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    String[] parts = line.split(",");
                    if (parts[2].equals(user.getUsername()) && parts[3].equals(user.getPassword())) {
                        userExistsInFile = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Assert.assertTrue("Ensure you log in users correctly!", loginResult && userExistsInFile);
        }

        @Test
        public void signUpTest() {
            User user = new User("username", "12345");
            Database database = new Database();

            boolean signUpResult = database.signUp(user);
            boolean userExistsInFile = false;

            try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    String[] parts = line.split(",");

                    if (parts[2].equals(user.getUsername()) && parts[3].equals(user.getPassword())) {
                        userExistsInFile = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Assert.assertTrue("Ensure you Sign Up users properly!", signUpResult && userExistsInFile);
        }

        @Test
        public void userExistsTest() {
            User user = new User("username", "12345");
            Database database = new Database();
            ArrayList<String> contents = new ArrayList<>();
            boolean userExistsResults = false;

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("userProfileDatabase.txt",
                        true));

                writer.write(user.toString());
                writer.close();

                userExistsResults = database.userExists(user.getUsername());

                BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"));

                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                }

                reader.close();

                if (!contents.isEmpty()) {
                    contents.remove(contents.size() - 1);
                }
                PrintWriter pw = new PrintWriter(new FileWriter("userProfileDatabase.txt"));
                for (String content : contents) {
                    pw.println(content);
                }

                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertTrue("Ensure userExists returns the correct value!", userExistsResults);
        }

        @Test
        public void deleteUserTest() {
            User user = new User("username", "12345");
            Database database = new Database();
            boolean deleteUserResult;
            boolean found = false;

            try (PrintWriter writer = new PrintWriter(new FileWriter("userProfileDatabase.txt", true))) {
                writer.println(user.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            deleteUserResult = database.deleteUser(user);

            try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.equals(user.toString())) {
                        found = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertTrue("Ensure users are deleted from the file properly!",
                    deleteUserResult && !found);
        }

        @Test
        public void sendMessageTest() {
            User sender = new User("sender", "12345");
            User recipient = new User("recipient", "12345");
            Messaging messaging = new Messaging(sender);
            ArrayList<String> contents = new ArrayList<>();
            ArrayList<String> users = new ArrayList<>();
            boolean found = false;

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("userProfileDatabase.txt", true))) {
               writer.write(recipient.toString());
               writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            messaging.sendMessage("Test", recipient.getUsername());

            try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    users.add(line);
                }

                users.remove(users.size() - 1);

                PrintWriter writer = new PrintWriter(new FileWriter("userProfileDatabase.txt"));
                for (String user : users) {
                    writer.println(user);
                }

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            try (BufferedReader reader = new BufferedReader(new FileReader("messagesDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                }

                reader.close();

                String[] parts = contents.get(contents.size() - 1).split(",");
                if (parts[0].equals("Test") && parts[1].equals(recipient.getUsername())
                        && parts[2].equals(sender.getUsername())) {
                    found = true;
                }
                if (!contents.isEmpty()) {
                    contents.remove(contents.size() - 1);
                }

                PrintWriter writer = new PrintWriter(new FileWriter("messagesDatabase.txt"));
                for (String content : contents) {
                    writer.println(content);
                }

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertTrue("Ensure the message is written to the file!", found);
        }

        @Test
        public void receiveMessageTest() {
            User sender = new User("sender", "12345");
            User recipient = new User("recipient", "12345");
            Messaging messaging = new Messaging(recipient);
            ArrayList<String> contents = new ArrayList<>();

            try (PrintWriter writer = new PrintWriter(new FileWriter("messagesDatabase.txt", true));
                BufferedReader reader = new BufferedReader(new FileReader("messagesDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                }

                writer.printf("%s,%s,%s%n", "Test", recipient.getUsername(), sender.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            String message = messaging.receiveMessage(sender.getUsername());

            try (PrintWriter writer = new PrintWriter(new FileWriter("messagesDatabase.txt"))) {
                for (String line : contents) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertEquals("Ensure the correct message is received",
                    "From sender: Test", message);

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

        @Test
        public void itemForSaleTest() {

        }

        @Test
        public void itemSoldTest() {

        }

        @Test
        public void searchUserTest() {

        }

        @Test
        public void listedItemSearchTest() {

        }

        @Test
        public void soldItemSearchTest() {

        }

        @Test
        public void getItemIDTest() {

        }

        @Test
        public void getItemNameTest() {

        }

        @Test
        public void setItemNameTest() {

        }

        @Test
        public void getPriceTest() {

        }

        @Test
        public void setPriceTest() {

        }

        @Test
        public void getSellerTest() {

        }

        @Test
        public void itemToStringTest() {
            User user = new User("name", "email", "username", "password");
            boolean forSale = false;
            Item item = new Item("123", "TestItem", 12.34, user);
            boolean test = false;
            if (item.toString().equals(String.format("%s,%s,%s,%s", 123, "TestItem", user, 12.34))) {
                test = true;
            }
            Assert.assertTrue("Ensure your toString method returns the correct result", test);
        }

        @Test
        public void getUserNameTest() {

        }

        @Test
        public void setUserNameTest() {

        }

        @Test
        public void getEmailTest() {

        }

        @Test
        public void setEmailTest() {

        }

        @Test
        public void getUsernameTest() {

        }

        @Test
        public void setUsernameTest() {

        }

        @Test
        public void getPasswordTest() {

        }

        @Test
        public void setPasswordTest() {

        }

        @Test
        public void getBalanceTest() {

        }

        @Test
        public void setBalanceTest() {

        }

        @Test
        public void isValidTest() {

        }

        @Test
        public void userToStringTest() {

        }
    }
}
