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
 * A framework to run public test cases for the Item class
 *
 *
 * Phase 2
 * @author Ronil Mitra
 * @version April 14, 2025
 */

@RunWith(Enclosed.class)
public class ItemTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ItemTest.TestCase.class);
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
        public void getItemIDTest() {
            User user = new User("username", "12345");
            Item item = new Item("Item-1", "Item", 10.0, user);
            Assert.assertEquals("Ensure getItemID() returns the correct result!",
                    "Item-1", item.getItemID());
        }

        @Test
        public void getItemNameTest() {
            User user = new User("username", "12345");
            Item item = new Item("Item-1", "Item", 10.0, user);
            Assert.assertEquals("Ensure getName() returns the correct result!",
                    "Item", item.getName());
        }

        @Test
        public void setItemNameTest() {
            User user = new User("username", "12345");
            Item item = new Item("Item-1", "Item", 10.0, user);
            item.setName("Name");
            Assert.assertEquals("Ensure setName() sets the field correctly!", "Name", item.getName());
        }

        @Test
        public void getPriceTest() {
            User user = new User("username", "12345");
            Item item = new Item("Item-1", "Item", 10.0, user);
            Assert.assertEquals("Ensure getPrice() returns the correct result!",
                    10.0, item.getPrice(), 0.001);
        }

        @Test
        public void setPriceTest() {
            User user = new User("username", "12345");
            Item item = new Item("Item-1", "Item", 10.0, user);
            item.setPrice(20.0);
            Assert.assertEquals("Ensure setPrice() sets the field variable correctly!",
                    20.0, item.getPrice(), 0.001);
        }

        @Test
        public void getSellerTest() {
            User user = new User("username", "12345");
            Item item = new Item("Item-1", "Item", 10.0, user);
            Assert.assertEquals("Ensure getSeller() returns the correct result!",
                    user.toString(), item.getSeller().toString());
        }

        @Test
        public void itemToStringTest() {
            User user = new User("name", "email", "username", "password");
            Item item = new Item("123", "TestItem", 12.34, user);

            Assert.assertEquals("Ensure your toString() method returns the correct result",
                    "123,TestItem,12.34,username", item.toString());
        }
    }
}
