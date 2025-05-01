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
 * A framework to run public test cases for the Messaging class
 *
 *
 * Phase 2
 * @author Ronil Mitra
 * @version April 14, 2025
 */

@RunWith(Enclosed.class)
public class MessagingTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MessagingTest.TestCase.class);
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

                writer.printf("%s,%s,%s\n", "Test", recipient.getUsername(), sender.getUsername());
                writer.printf("%s,%s,%s\n", "Test1", recipient.getUsername(), sender.getUsername());
                writer.printf("%s,%s,%s\n", "Test2", recipient.getUsername(), sender.getUsername());
                writer.printf("%s,%s,%s\n", "Test3", recipient.getUsername(), sender.getUsername());
                writer.printf("%s,%s,%s\n", "Test4", recipient.getUsername(), sender.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            ArrayList<String> messages = messaging.receiveMessage(sender.getUsername());

            try (PrintWriter writer = new PrintWriter(new FileWriter("messagesDatabase.txt"))) {
                for (String line : contents) {
                    writer.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Assert.fail("An IOException occurred");
            }

            Assert.assertEquals("Ensure receiveMessage() returns an ArrayList of the correct length",
                    5, messages.size());
            Assert.assertTrue("Ensure receiveMessage() returns the correct result",
                    messages.contains("From sender: Test"));

        }
    }
}
