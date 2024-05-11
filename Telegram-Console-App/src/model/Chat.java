package model;

import java.util.ArrayList;

public class Chat {
    private ChatStatus status = ChatStatus.SIMPLE_CHAT;
    private ArrayList<User> members = new ArrayList<User>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private User owner;
    private String id;
    private String name;

    public Chat(User owner, String id, String name) {
        this.owner = owner;
        this.id = id;
        this.name = name;
    }

    public User getOwner() {
        return this.owner;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<User> getMembers() {
        return this.members;
    }

    public ArrayList<Message> getMessages() {
        return this.messages;
    }

    public void addMember(User user) {
        this.members.add(user);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public ChatStatus getStatus() {
        return status;
    }

    public void setStatus(ChatStatus status) {
        this.status = status;
    }

}
