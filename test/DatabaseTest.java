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
 * A framework to run public test cases for the Database class
 *
 *
 * Phase 2
 * @author Ronil Mitra
 * @version April 14, 2025
 */

@RunWith(Enclosed.class)
public class DatabaseTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(DatabaseTest.TestCase.class);
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
        public void loginTest() {
            User user = new User("username", "12345");
            Database database = new Database();
            ArrayList<String> contents = new ArrayList<>();
            boolean userExistsInFile = false;

            try (PrintWriter writer = new PrintWriter(new FileWriter("userProfileDatabase.txt", true))) {
                writer.println(user.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            boolean loginResult = database.logIn(user);

            try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                    line = line.trim();
                    String[] parts = line.split(",");
                    if (parts[2].equals(user.getUsername()) && parts[3].equals(user.getPassword())) {
                        userExistsInFile = true;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            contents.remove(contents.size() - 1);

            try (PrintWriter writer = new PrintWriter(new FileWriter("userProfileDatabase.txt"))) {
                for (String line : contents) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertTrue("Ensure you log in users correctly!", loginResult && userExistsInFile);
        }

        @Test
        public void signUpTest() {
            User user = new User("username", "12345");
            Database database = new Database();
            ArrayList<String> contents = new ArrayList<>();

            boolean signUpResult = database.signUp(user);
            boolean userExistsInFile = false;

            try (BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
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

            contents.remove(contents.size() - 1);

            try (PrintWriter writer = new PrintWriter(new FileWriter("userProfileDatabase.txt"))) {
                for (String line : contents) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertTrue("Ensure you Sign Up users properly!", signUpResult && userExistsInFile);
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
        public void itemForSaleTest() {
            User user = new User("username", "12345");
            Item item = new Item("Item-1", "name", 10.0, user);
            Item item2 = new Item("Item-2", "name", 10.0, user);
            Database database = new Database();
            ArrayList<String> contents = new ArrayList<>();

            try (PrintWriter writer = new PrintWriter(new FileWriter("listedItemsDatabase.txt", true))) {
                writer.println(item.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            try (BufferedReader reader = new BufferedReader(new FileReader("listedItemsDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            boolean itemListed = database.itemForSale(item);
            boolean itemTwoListed = database.itemForSale(item2);

            contents.remove(contents.size() - 1);

            try (PrintWriter writer = new PrintWriter(new FileWriter("listedItemsDatabase.txt"))) {
                for (String line : contents) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertTrue("Ensure the item is correctly listed for sale", itemListed);
            Assert.assertFalse("Ensure the item is correctly listed for sale", itemTwoListed);
        }

        @Test
        public void itemSoldTest() {
            User user = new User("username", "12345");
            Item item = new Item("Item-1", "name", 10.0, user);
            Item item2 = new Item("Item-2", "name", 10.0, user);
            Database database = new Database();
            ArrayList<String> contents = new ArrayList<>();

            try (PrintWriter writer = new PrintWriter(new FileWriter("soldItemsDatabase.txt", true))) {
                writer.println(item.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            try (BufferedReader reader = new BufferedReader(new FileReader("soldItemsDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            boolean itemSold = database.itemSold(item);
            boolean itemTwoSold = database.itemSold(item2);

            contents.remove(contents.size() - 1);

            try (PrintWriter writer = new PrintWriter(new FileWriter("soldItemsDatabase.txt"))) {
                for (String line : contents) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertTrue("Ensure the item is correctly marked as sold", itemSold);
            Assert.assertFalse("Ensure the item is correctly marked as sold", itemTwoSold);
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
        public void searchUserTest() {
            User user = new User("username", "12345");
            Database database = new Database();
            ArrayList<String> contents = new ArrayList<>();

            try (PrintWriter writer = new PrintWriter(new FileWriter("userProfileDatabase.txt", true));
                 BufferedReader reader = new BufferedReader(new FileReader("userProfileDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                }

                writer.println(user.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            User testUser = database.searchUser("username");

            try (PrintWriter writer = new PrintWriter(new FileWriter("userProfileDatabase.txt"))) {
                for (String line : contents) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertEquals("Ensure searchUser returns the correct value!",
                    user.toString(), testUser.toString());
            Assert.assertNull("Ensure unknown usernames return null!",
                    database.searchUser("username1"));
        }

        @Test
        public void listedItemSearchTest() {
            User user = new User("username", "12345");
            Item item = new Item("Item-1", "Item1", 10.0, user);
            Item item2 = new Item("Item-2", "Item2", 10.0, user);
            Item item3 = new Item("Item-3", "Item3", 10.0, user);
            Item testItem = new Item("test", "test", 10.0, user);
            ArrayList<String> contents = new ArrayList<>();

            try (PrintWriter writer = new PrintWriter(new FileWriter("listedItemsDatabase.txt", true));
                 BufferedReader reader = new BufferedReader(new FileReader("listedItemsDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                }

                writer.println(item.toString());
                writer.println(item2.toString());
                writer.println(item3.toString());
                writer.println(testItem.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            ArrayList<String> searchResults = Database.listedItemSearch("Item");

            try (PrintWriter writer = new PrintWriter(new FileWriter("listedItemsDatabase.txt"))) {
                for (String line : contents) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            boolean correctResult = searchResults.contains(item.toString()) && searchResults.contains(item2.toString())
                    && searchResults.contains(item3.toString());
            boolean incorrectResult = searchResults.contains(testItem.toString());


            Assert.assertEquals("Ensure listedItemSearch() returns the correct number of matching items!",
                    3, searchResults.size());
            Assert.assertTrue("Ensure listedItemSearch() returns the correct all the matching items!",
                    correctResult);
            Assert.assertFalse("Ensure listedItemSearch() returns the correct all the matching items!",
                    incorrectResult);
        }

        @Test
        public void soldItemSearchTest() {
            User user = new User("username", "12345");
            Item item = new Item("Item-1", "Item1", 10.0, user);
            Item item2 = new Item("Item-2", "Item2", 10.0, user);
            Item item3 = new Item("Item-3", "Item3", 10.0, user);
            Item testItem = new Item("test", "test", 10.0, user);
            ArrayList<String> contents = new ArrayList<>();

            try (PrintWriter writer = new PrintWriter(new FileWriter("soldItemsDatabase.txt", true));
                 BufferedReader reader = new BufferedReader(new FileReader("soldItemsDatabase.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.add(line);
                }

                writer.println(item.toString());
                writer.println(item2.toString());
                writer.println(item3.toString());
                writer.println(testItem.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            ArrayList<String> searchResults = Database.soldItemSearch("Item");

            try (PrintWriter writer = new PrintWriter(new FileWriter("soldItemsDatabase.txt"))) {
                for (String line : contents) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            boolean correctResult = searchResults.contains(item.toString()) && searchResults.contains(item2.toString())
                    && searchResults.contains(item3.toString());
            boolean incorrectResult = searchResults.contains(testItem.toString());


            Assert.assertEquals("Ensure soldItemSearch() returns the correct number of matching items!",
                    3, searchResults.size());
            Assert.assertTrue("Ensure soldItemSearch() returns the correct all the matching items!",
                    correctResult);
            Assert.assertFalse("Ensure soldItemSearch() returns the correct all the matching items!",
                    incorrectResult);
        }
    }
}
