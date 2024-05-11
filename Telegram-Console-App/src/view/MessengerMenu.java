package view;

import controller.Commands;
import controller.Messenger;
import model.*;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MessengerMenu {
    public static Chat enteredChat;
    private Chat chat;
    private User currentUser;

    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            if (Commands.SHOW_ALL_CHANNELS.getMatcher(input).matches())
                System.out.println(showAllChannels());
            else if (Commands.CREATE_CHANNEL.getMatcher(input).matches())
                System.out.println(createChannel(Commands.CREATE_CHANNEL.getMatcher(input)));
            else if (Commands.JOIN_CHANNEL.getMatcher(input).matches())
                System.out.println(joinChannel(Commands.JOIN_CHANNEL.getMatcher(input)));
            else if (Commands.SHOW_MY_CHATS.getMatcher(input).matches())
                System.out.println(showChats());
            else if (Commands.CREATE_GROUP.getMatcher(input).matches())
                System.out.println(createGroup(Commands.CREATE_GROUP.getMatcher(input)));
            else if (Commands.START_PRIVATE_CHAT.getMatcher(input).matches())
                System.out.println(startPrivateChat(Commands.START_PRIVATE_CHAT.getMatcher(input)));
            else if (Commands.ENTER_CHAT.getMatcher(input).matches()) {
                String result = enterChat(Commands.ENTER_CHAT.getMatcher(input));
                System.out.println(result);
                if (result.equals("You have successfully entered the chat!")) {
                    break;
                }
            } else if (Commands.LOGOUT.getMatcher(input).matches()) {
                System.out.println("Logged out");
                MenuStatus.setMenuStatus(MenuStatus.LOGIN_MENU);
                break;
            } else {
                System.out.println("Invalid command!");
            }
            ;
        }
    }

    private String showAllChannels() {
        int counter = 1;
        StringBuilder result = new StringBuilder("All channels:");
        for (Channel channel : Messenger.getChannels()) {
            result.append("\n")
                    .append(counter)
                    .append("- ").append(channel.getName())
                    .append(", id: ").append(channel.getId())
                    .append(", members: ")
                    .append(channel.getMembers().size());
            counter += 1;
        }
        return result.toString();
    }

    private String joinChannel(Matcher matcher) {
        matcher.matches();
        String channelId = matcher.group("id");
        Channel channel = Messenger.getChannelById(channelId);

        if (channel == null) {
            return "No channel with this id exists!";
        }
        if (channel.getMembers().contains(currentUser)) {
            return "You are already a member of this channel!";
        }
        currentUser.addChannel(channel);
        channel.addMember(currentUser);
        return "You have joined the channel successfully !";
    }

    public MessengerMenu(User currentUser) {
        this.currentUser = currentUser;
    }

    private String createChannel(Matcher matcher) {
        matcher.matches();
        String id = matcher.group("id");
        String name = matcher.group("name");

        if (!Commands.NAME.getMatcher(name).matches()) {
            return "Channel name's format is invalid!";
        }

        if (Messenger.getChannelById(id) != null) {
            return "A channel with this id already exists!";
        }

        Channel newChannel = new Channel(currentUser, id, name);
        newChannel.addMember(currentUser);
        Messenger.addChannel(newChannel);
        currentUser.addChannel(newChannel);
        String result = "Channel " + name + " has been created successfully!";
        return result;


    }


    private String enterChat(Matcher matcher) {
        matcher.matches();
        String chatType = matcher.group("chatType");
        String id = matcher.group("id");

        switch (chatType) {
            case "group":
                Group group = Messenger.getGroupById(id);
                if (group == null || !group.getMembers().contains(currentUser)) {
                    return "You have no group with this id!";
                } else {
                    chat = group;
                }
                break;
            case "channel":
                Channel channel = Messenger.getChannelById(id);
                if (channel == null || !channel.getMembers().contains(currentUser)) {
                    return "You have no channel with this id!";
                } else {
                    chat = channel;
                }
                break;
            case "private chat":
                PrivateChat pv = Messenger.getMemberById(id);
                if (pv == null || !pv.getMembers().contains(currentUser)) {
                    return "You have no private chat with this id!";
                } else {
                    chat = pv;
                }
                break;
            default:
                return "Invalid command!";
        }
        enteredChat = chat;
        MenuStatus.setMenuStatus(MenuStatus.CHAT_MENU);
        return "You have successfully entered the chat!";
    }

    private String startPrivateChat(Matcher matcher) {
        matcher.matches();
        String id = matcher.group("id");
        User contact = Messenger.getUserById(id);

        if (Messenger.getMemberById(id) != null) {
            return "You already have a private chat with this user!";
        }
        if (contact == null) {
            return "No user with this id exists!";
        }
        PrivateChat newPrivateChat = new PrivateChat(currentUser, id, contact.getName());
        if (currentUser.equals(contact)) {
            newPrivateChat.addMember(currentUser);
            currentUser.addChat(newPrivateChat);
        } else {
            newPrivateChat.addMember(currentUser);
            newPrivateChat.addMember(contact);
            currentUser.addPrivateChat(newPrivateChat);
            contact.addPrivateChat(newPrivateChat);
        }
        return "Private chat with " + contact.getName() + " has been started successfully!";
    }

    private String createGroup(Matcher matcher) {
        matcher.matches();
        String id = matcher.group("id");
        String name = matcher.group("name");

        if (!Commands.NAME.getMatcher(name).matches()) {
            return "Group name's format is invalid!";
        }
        if (Messenger.getGroupById(id) != null) {
            return "A group with this id already exists!";
        }

        Group newGroup = new Group(currentUser, id, name);
        newGroup.addMember(currentUser);
        Messenger.addGroup(newGroup);
        currentUser.addGroup(newGroup);

        return "Group " + name + " has been created successfully!";
    }

    private String showChats() {
        StringBuilder result = new StringBuilder();
        result.append("Chats:");
        int counter = 1;
        for (Chat chat : currentUser.getChats()) {
            result.append("\n")
                    .append(counter)
                    .append(". ")
                    .append(chat.getName())
                    .append(", id: ")
                    .append(chat.getId())
                    .append(chat.getStatus().toString());
            counter += 1;
        }
        return result.toString();
    }

}
