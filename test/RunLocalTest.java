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
            //TODO
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
               writer.close();
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

        }

        @Test
        public void addItemDatabaseTest() {
            User user = new User("name", "email", "username", "password");
            boolean forSale = false;
            Item item = new Item("123", "TestItem", 12.34, user, forSale);
            boolean itemAdded = false;
            Database.addItemDatabase(item);


            try (BufferedReader bfr = new BufferedReader(new FileReader("itemDatabase.txt"))) {
                ArrayList<String> file = new ArrayList();


                for(String line = bfr.readLine(); line != null; line = bfr.readLine()) {
                    file.add(line);
                }


                for(int i = 0; i < file.size(); ++i) {
                    if (((String)file.get(i)).contains(item.toString())) {
                        itemAdded = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            Assert.assertTrue("Ensure you add your items into the database properly!", itemAdded);
        }


        @Test
        public void itemSearchTest() {
            User user = new User("name", "email", "username", "password");
            boolean forSale = false;
            Item item = new Item("123", "TestItem", 12.34, user, forSale);
            boolean itemFound = true;
            Database.addItemDatabase(item);
            File searchResults = Database.itemSearch("TestItem");


            try (BufferedReader bfr = new BufferedReader(new FileReader(searchResults))) {
                ArrayList<String> file = new ArrayList();


                for(String line = bfr.readLine(); line != null; line = bfr.readLine()) {
                    file.add(line);
                }


                for(int i = 0; i < file.size(); ++i) {
                    if (!((String)file.get(i)).contains("TestItem")) {
                        itemFound = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            Assert.assertTrue("Ensure you add your items into the database properly!", itemFound);
        }


        @Test
        public void itemToStringTest() {
            User user = new User("name", "email", "username", "password");
            boolean forSale = false;
            Item item = new Item("123", "TestItem", 12.34, user, forSale);
            boolean test = false;
            if (item.toString().equals(String.format("%s,%s,%s,%s", 123, "TestItem", user, 12.34))) {
                test = true;
            }
            Assert.assertTrue("Ensure your toString method returns the correct result", test);
        }
    }
}
