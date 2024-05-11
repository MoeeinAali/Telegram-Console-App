package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    NAME("[a-zA-Z0-9_]+"),
    PASSWORD_DIGIT("[\\d]"),
    PASSWORD_SMALL_LETTER("[a-z]"),
    PASSWORD_CAPITAL_LETTER("[A-Z]"),
    PASSWORD_SPECIAL_CHARACTER("[\\*\\.!@$%\\^&\\(\\){}\\[\\]:;\\<\\>,\\?\\/~_\\+\\-=|]"),
    REGISTER("register i (?<id>[\\S]+) u (?<username>[\\S]+) p (?<password>[\\S]+)"),
    LOGIN("login i (?<id>[\\S]+) p (?<password>[\\S]+)"),
    EXIT("exit"),
    SHOW_ALL_CHANNELS("show all channels"),
    CREATE_CHANNEL("create new channel i (?<id>[\\S]+) n (?<name>[\\S]+)"),
    JOIN_CHANNEL("join channel i (?<id>[\\S]+)"),
    SHOW_MY_CHATS("show my chats"),
    CREATE_GROUP("create new group i (?<id>[\\S]+) n (?<name>[\\S]+)"),
    START_PRIVATE_CHAT("start a new private chat with i (?<id>[\\S]+)"),
    LOGOUT("logout"),
    ENTER_CHAT("enter (?<chatType>.+) i (?<id>[\\S]+)"),
    SEND_MESSAGE("send a message c (?<message>.+)"),
    ADD_MEMBER("add member i (?<id>[\\S]+)"),
    SHOW_ALL_MESSAGES("show all messages"),
    SHOW_ALL_MEMBERS("show all members"),
    BACK("back");

    private String regex;

    private Commands(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern pattern = Pattern.compile(this.regex);
        return pattern.matcher(input);
    }
}
