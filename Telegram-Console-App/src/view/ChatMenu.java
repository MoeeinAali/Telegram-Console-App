package view;


import controller.Commands;
import controller.Messenger;
import model.*;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ChatMenu {
    private Chat chat;
    private User currentUser;

    public ChatMenu(Chat chat, User currentUser) {
        this.chat = chat;
        this.currentUser = currentUser;
    }

    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            if (Commands.SEND_MESSAGE.getMatcher(input).matches()) {
                System.out.println(sendMessage(Commands.SEND_MESSAGE.getMatcher(input)));
            } else if (Commands.ADD_MEMBER.getMatcher(input).matches()) {
                System.out.println(addMember(Commands.ADD_MEMBER.getMatcher(input)));
            } else if (Commands.SHOW_ALL_MESSAGES.getMatcher(input).matches()) {
                System.out.println(showMessage());
            } else if (Commands.SHOW_ALL_MEMBERS.getMatcher(input).matches()) {
                System.out.println(showMembers());
            } else if (Commands.BACK.getMatcher(input).matches()) {
                MenuStatus.setMenuStatus(MenuStatus.MESSENGER_MENU);
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private String sendMessage(Matcher matcher) {
        matcher.matches();
        String messageContent = matcher.group("message");

        if (chat.getStatus().equals(ChatStatus.CHANNEL_CHAT)) {
            if (!chat.getOwner().equals(currentUser)) {
                return "You don't have access to send a message!";
            }
        }

        Message newMessage = new Message(currentUser, messageContent);
        chat.addMessage(newMessage);

        for (User member : chat.getMembers()) {
            member.getChats().remove(chat);
            member.addChat(chat);
        }
        return "Message has been sent successfully!";
    }

    private String addMember(Matcher matcher) {
        matcher.matches();
        String userId = matcher.group("id");
        User user = Messenger.getUserById(userId);

        if (chat.getStatus().equals(ChatStatus.PRIVATE_CHAT)) {
            return "Invalid command!";
        }
        if (!chat.getOwner().equals(currentUser)) {
            return "You don't have access to add a member!";
        }
        if (user == null) {
            return "No user with this id exists!";
        }

        for (User member : chat.getMembers()) {
            if (member.getId().equals(userId)) {
                return "This user is already in the chat!";
            }
        }

        chat.addMember(user);
        user.addChat(chat);
        if (chat.getStatus().equals(ChatStatus.GROUP_CHAT)) {
            String messageContent = user.getName() + " has been added to the group!";
            chat.addMessage(new Message(currentUser, messageContent));
            for (User member : chat.getMembers()) {
                member.getChats().remove(chat);
                member.addChat(chat);
            }
        }
        return "User has been added successfully!";
    }

    private String showMessage() {
        StringBuilder result = new StringBuilder("Messages:");
        for (Message message : chat.getMessages()) {
            result.append("\n")
                    .append(message.getOwner().getName())
                    .append("(")
                    .append(message.getOwner().getId())
                    .append("): \"")
                    .append(message.getContent())
                    .append("\"");
        }
        return result.toString();
    }

    private String showMembers() {
        if (chat instanceof PrivateChat) {
            return "Invalid command!";
        }
        StringBuilder result = new StringBuilder("Members:");
        for (User member : chat.getMembers()) {
            result.append("\n")
                    .append("name: ")
                    .append(member.getName())
                    .append(", id: ")
                    .append(member.getId());
            if (chat.getOwner().equals(member)) {
                result.append(" *owner");
            }
        }
        return result.toString();
    }
}
