package model;

import java.util.ArrayList;

public class User {
    private String id;
    private String name;
    private String password;
    private ArrayList<Chat> chats = new ArrayList<Chat>();

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Group getGroupById(String id) {
        for (Chat chat : chats) {
            if (chat.getStatus().equals(ChatStatus.GROUP_CHAT)) {
                if (chat.getId().equals(id)) {
                    return (Group) chat;
                }
            }
        }
        return (Group) null;
    }

    public Channel getChannelById(String id) {
        for (Chat chat : chats) {
            if (chat.getStatus().equals(ChatStatus.CHANNEL_CHAT)) {
                if (chat.getId().equals(id)) {
                    return (Channel) chat;
                }
            }
        }
        return (Channel) null;
    }

    public PrivateChat getPrivateChatById(String id) {
        for (Chat chat : chats) {
            if (chat.getStatus().equals(ChatStatus.PRIVATE_CHAT)) {
                if (chat.getId().equals(id)) {
                    return (PrivateChat) chat;
                }
            }
        }
        return (PrivateChat) null;
    }

    public String getPassword() {
        return this.password;
    }

    public ArrayList<Chat> getChats() {
        return this.chats;
    }

    public void addChat(Chat chat) {
        this.chats.add(chat);
    }

    public void addChannel(Channel channel) {
        this.chats.add(channel);
    }

    public void addPrivateChat(PrivateChat pv) {
        this.chats.add(pv);
    }

    public void addGroup(Group group) {
        this.chats.add(group);
    }


}
