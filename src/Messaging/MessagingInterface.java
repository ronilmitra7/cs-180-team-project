package src.Messaging;

import src.user.User;

import java.util.ArrayList;

public interface MessagingInterface {
    public User getSender();
    public void setSender(User sender);

    public User getRecepient();
    public void setRecepient(User recepient);

    public String getMessageContent();
    public void setMessageContent(String messageContent);

    public ArrayList<String> getInboxMessages();
    public void setInboxMessages(ArrayList<String> inboxMessages);

    public ArrayList<String> getSentMessages();
    public void setSentMessages(ArrayList<String> sentMessages);
}
