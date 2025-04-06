# Team Project

### Instructions

You can compile each class using the terminal and you can run the local test cases to test the functionality of each class.

### Submissions
Phase 1 submitted by Ronil Mitra



### Class Descriptions

- **Messaging.java**: this class implements MessagingInterface. It has one field variable of type User. The constructor takes in a User as a paremeter and initializes the field variable to the parameter. There are two methods, one to send messages and one to receive messages. The method for sending messages takes in a message as a paramater of type String, and the recipient's username, and it sends the message to the recipient, if they exist. The method for receiving messages takes in a username as a parameter, and looks for messages sent by that user to the field variable and returns them, if they exist.

- **MessagingInterface.java**: this interface creates the methods sending and receiving messages. Implemented by the Messaging class.

- **Database.java**: this class implements DatabaseInterface. This class serves as the primary interface for user management. It contains methods for user registration, login, deleting accounts, user search, checking if items are for sale or sold, checking if users exist, and search matches in the listed and sold items file based on an inputted search term.

- **DatabaseInterface.java**: this interface creates the intro menu method. Implemented by Database.java.

- **Marketplace.java**: this class implements MarketplaceInterface. This class has one field variable of type user. The constructor takes in a User user as a parameter and initializes the field variable to the parameter. There are two methods, one for buying an item and one for listing an item. The method for buying an item takes in an Item item as a parameter. It checks if the item is for sale, and if it is, it checks if the User field variable has enough money to buy the item. If they do, the buyer and seller's balances are updated, and the item is moved from the listed item database to the sold item database. The method for listing items takes in an item name and price as a parameter, generates a unique item ID, checks if the item is already for sale or was already sold, and then adds the item to the listed items database.

- **MarketplaceInterface.java**: this interface creates the methods for buying and listing items. Implemented by Marketplace.java.

- **User.java**: this class implements UserInterface. It has field variables for username, email, name, password, and balance. When a new User object is created, its balance is initially set to 500. There are three constructors, one no parameter constructor, one that takes in a username and password as parameters, and one that takes in name, email, username, and password as parameters. Each of the constructors initialize the field variables to their respective parameters. There are getters and setters for each of the field variables, a method for checking if the User is valid, and a toString(). The method for checking if the User is valid returns false if the username or password is longer than 11 characters or if it contains a space, and it returns true otherwise.

- **UserInterface.java**: this interface creates the accessor and mutator methods in User.java.

- **Item.java**: this class implements ItemInterface. It has field variables for the item ID, item name, price, and seller. Both the field variables for ID and seller are final variables. The constructor takes in an item ID, name, price, and seller as parameters and initializes the field variables to their respective parameters. This class contains getters for all field variables, and setters for methods that aren't final.

- **ItemInterface.java**: this interface creates the accessor and mutator methods in Item.java.

- **RunLocalTest.java**: this framework runs public test cases. Contains test cases for each method of each class, and additionally contains class declaration tests. 