package src.Messaging;

import src.user.User;

import java.util.ArrayList;

public class Messaging implements MessagingInterface {
    private User sender;
    private User recepient;
    private String messageContent;
    private ArrayList<String> inboxMessages;
    private ArrayList<String> sentMessages;

    public Messaging(User sender) {
        this.inboxMessages = new ArrayList<>();
        this.sentMessages = new ArrayList<>();
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecepient() {
        return recepient;
    }

    public void setRecepient(User recepient) {
        this.recepient = recepient;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public ArrayList<String> getInboxMessages() {
        return inboxMessages;
    }

    public void setInboxMessages(ArrayList<String> inboxMessages) {
        this.inboxMessages = inboxMessages;
    }

    public ArrayList<String> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(ArrayList<String> sentMessages) {
        this.sentMessages = sentMessages;
    }
}
