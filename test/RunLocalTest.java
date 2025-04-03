package test;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.Assert;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import src.Database.Database;
import src.Database.DatabaseInterface;
import src.Marketplace.Item;
import src.Marketplace.ItemInterface;
import src.Messaging.Messaging;
import src.Messaging.MessagingInterface;
import src.user.User;
import src.user.UserInterface;

import javax.swing.*;
import java.io.*;

import java.lang.reflect.Modifier;
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

            boolean loginResult = Database.logIn(user);
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

            boolean signUpResult = Database.signUp(user);
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
        public void sendMessageTest() {
            User user = new User("username", "12345");
            User user1 = new User("username1", "12345");
        }

        @Test
        public void receiveMessageTest() {
            User user = new User("username", "12345");
            User user1 = new User("username1", "12345");
        }

    }
}
