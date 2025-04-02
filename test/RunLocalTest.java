package test;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * A framework to run public test cases for the group project.
 * Make sure there's an assert for each method
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
                    JComponent.class, superclass);
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
                    JComponent.class, superclass);
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
                    JComponent.class, superclass);
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
                    JComponent.class, superclass);
            Assert.assertTrue("Ensure that `Item` implements `ItemInterface`!",
                    Arrays.asList(superinterfaces).contains(ItemInterface.class));

        }
    }
}
