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
 * A framework to run public test cases for the Server class
 *
 *
 * Phase 2
 * @author Ronil Mitra
 * @version April 14, 2025
 */

@RunWith(Enclosed.class)
public class ServerTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ServerTest.TestCase.class);
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
        public void ServerClassDeclarationTest() {
            Class<?> clazz;
            int modifiers;
            Class<?> superclass;
            Class<?>[] superinterfaces;

            clazz = Server.class;

            modifiers = clazz.getModifiers();

            superclass = clazz.getSuperclass();

            superinterfaces = clazz.getInterfaces();

            Assert.assertTrue("Ensure that `Server` is `public`!",
                    Modifier.isPublic(modifiers));
            Assert.assertFalse("Ensure that `Server` is NOT `abstract`!",
                    Modifier.isAbstract(modifiers));
            Assert.assertEquals("Ensure that `Server` extends `Database`!",
                    Database.class, superclass);
            Assert.assertTrue("Ensure that `Server` implements `ServerInterface`!",
                    Arrays.asList(superinterfaces).contains(ServerInterface.class));
            Assert.assertTrue("Ensure that `Server` implements `Runnable`!",
                    Arrays.asList(superinterfaces).contains(Runnable.class));

        }
    }
}
