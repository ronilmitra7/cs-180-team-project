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
 * A framework to run public test cases for the User class
 *
 *
 * Phase 2
 * @author Ronil Mitra
 * @version April 14, 2025
 */

@RunWith(Enclosed.class)
public class UserTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(UserTest.TestCase.class);
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
        public void getUserNameTest() {
            User user = new User("name", "email", "username", "12345");
            Assert.assertEquals("Ensure getName() returns the correct result!", "name", user.getName());
        }

        @Test
        public void setUserNameTest() {
            User user = new User("name", "email", "username", "12345");
            user.setName("TestName");
            Assert.assertEquals("Ensure setName() sets the field variable correctly!",
                    "TestName", user.getName());
        }

        @Test
        public void getEmailTest() {
            User user = new User("name", "email", "username", "12345");
            Assert.assertEquals("Ensure getEmail() returns the correct result!", "email", user.getEmail());
        }

        @Test
        public void setEmailTest() {
            User user = new User("name", "email", "username", "12345");
            user.setEmail("TestEmail");
            Assert.assertEquals("Ensure setEmail() sets the field variable correctly!",
                    "TestEmail", user.getEmail());
        }

        @Test
        public void getUsernameTest() {
            User user = new User("name", "email", "username", "12345");
            Assert.assertEquals("Ensure getUsername() returns the correct result!", "username", user.getUsername());
        }

        @Test
        public void setUsernameTest() {
            User user = new User("name", "email", "username", "12345");
            user.setUsername("TestUsername");
            Assert.assertEquals("Ensure setUsername() sets the field variable correctly!",
                    "TestUsername", user.getUsername());
        }

        @Test
        public void getPasswordTest() {
            User user = new User("name", "email", "username", "12345");
            Assert.assertEquals("Ensure getPassword() returns the correct result!", "12345", user.getPassword());
        }

        @Test
        public void setPasswordTest() {
            User user = new User("name", "email", "username", "12345");
            user.setPassword("TestPassword");
            Assert.assertEquals("Ensure setPassword() sets the field variable correctly!",
                    "TestPassword", user.getPassword());
        }

        @Test
        public void getBalanceTest() {
            User user = new User("name", "email", "username", "12345");
            Assert.assertEquals("Ensure getBalance() returns the correct result!",
                    500.0, user.getBalance(), 0.001);
        }

        @Test
        public void setBalanceTest() {
            User user = new User("name", "email", "username", "12345");
            user.setBalance(100.0);
            Assert.assertEquals("Ensure setBalance() sets the balance correctly!", 100.0, user.getBalance(), 0.001);
        }

        @Test
        public void isValidTest() {
            User user = new User("username", "12345");
            User user1 = new User("user name", "12345");
            User user2 = new User("abcdefghijkl", "12345");
            User user3 = new User("username", "1234567891011");
            User user4 = new User("username", "12 345");

            Assert.assertTrue("Ensure isValid() returns the correct result!", User.isValid(user));
            Assert.assertFalse("Ensure isValid() returns the correct result!", User.isValid(user1));
            Assert.assertFalse("Ensure isValid() returns the correct result!", User.isValid(user2));
            Assert.assertFalse("Ensure isValid() returns the correct result!", User.isValid(user3));
            Assert.assertFalse("Ensure isValid() returns the correct result!", User.isValid(user4));
        }

        @Test
        public void userToStringTest() {
            User user = new User("name", "email", "username", "12345");
            Assert.assertEquals("Ensure toString() returns the correct value!",
                    "name,email,username,12345", user.toString());
        }
    }
}
