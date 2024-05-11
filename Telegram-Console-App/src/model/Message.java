package model;

public class Message {
    private User owner;
    private String text;

    public Message(User owner, String text) {
        this.owner = owner;
        this.text = text;
    }

    public String getContent() {
        return this.text;
    }

    public User getOwner() {
        return this.owner;
    }

}
