# Team Project

### Instructions

You can compile each class using the terminal and you can run the local test cases to test the functionality of each class. For the Server and Client, run Server.java first and then run Client.java and then complete the intro panel, where you either log in or sign up. After that, it will give you choices and you can test the different operations on the menu. After running the Server and Client successfully, server console should print whether the user's action is successful or not.

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
  
- **Server.java**: this class extends Database and implements Runnable, and is thread safe. There is a constructor for this class, which takes in a Socket socket as a parameter and initializes the field variable to the respective parameter. There are two methods, a run method and the main method, both returning nothing. The run method establishes a connection with the client and handles user input. It initializes a ServerSocket, waits for client connection, reads the initial user selection and processes client requests based on their choice, and includes a switch statement to handle different user commands. The main method creates an instance of of the Server and runs the server logic using the start() method. 

- **ServerInterface.java**: this interface creates the run method implemented in Server.java. 

- **Client.java**: this class implements Runnable and connects to a server over a network and allows the user to perform many different actions, including searching for users, buying/listing items, messaging, viewing your balance, and deleting your account. There are no constructors in this class, and there are two methods, the run method and the main method, both of which return nothing. The run method prompts the user with a many of various different actions, and establishes a Socket to connect to localhost at port 4242. It reads the user's choice and sends the request to the server. All the menu logic is inside a loop so that the user can perform multiple operations unless they choose to exit. The main method creates a Client object and runs the client logic using the start() method. 

- **ClientInterface.java**: this interface creates the run method implemented in Client.java.

- **RunLocalTest.java**: this framework runs public test cases. Contains test cases for each method of each class, and additionally contains class declaration tests. 

- **ClientTest.java**: this framework runs public test cases for the client class. Contains the class declaration test for client.

- **DatabaseTest.java**: this framework runs public test cases for the database class. Contains test cases for each method, and additionally contains the class declaration test.

- **ItemTest.java**: this framework runs public test cases for the item class. Contains test cases for each method, and additionally contains the class declaration test.

- **MarketplaceTest.java**: this framework runs public test cases for the marketplace class. Contains test cases for each method, and additionally contains the class declaration test.

- **MessagingTest.java**: this framework runs public test cases for the messaging class. Contains test cases for each method, and additionally contains the class declaration test.

- **ServerTest.java**: this framework runs public test cases for the server class. Contains the class declaration test for server.

- **UserTest.java**: this framework runs public test cases for the user class. Contains test cases for each method, and additionally contains the class declaration test.
