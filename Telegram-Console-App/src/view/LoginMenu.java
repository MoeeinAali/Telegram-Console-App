package view;

import controller.Commands;
import controller.Messenger;
import model.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {
    public void run(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            if (Commands.REGISTER.getMatcher(input).matches())
                System.out.println(register(Commands.REGISTER.getMatcher(input)));
            else if (Commands.LOGIN.getMatcher(input).matches()) {
                String result = login(Commands.LOGIN.getMatcher(input));
                System.out.println(result);
                if (result.equals("User successfully logged in!")) {
                    break;
                }
            } else if (Commands.EXIT.getMatcher(input).matches()) {
                MenuStatus.setMenuStatus(MenuStatus.TERMINATE);
                break;
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private String register(Matcher matcher) {
        matcher.matches();
        String id = matcher.group("id");
        String username = matcher.group("username");
        String password = matcher.group("password");

        if (!Commands.NAME.getMatcher(username).matches()) {
            return "Username's format is invalid!";
        }
        if (password.length() < 8 || password.length() > 32 || !Commands.PASSWORD_DIGIT.getMatcher(password).find() ||
                !Commands.PASSWORD_CAPITAL_LETTER.getMatcher(password).find() || !Commands.PASSWORD_SMALL_LETTER.getMatcher(password).find() ||
                !Commands.PASSWORD_SPECIAL_CHARACTER.getMatcher(password).find()) {
            return "Your Password is weak!";
        }
        if (Messenger.getUserById(id) != null) {
            return "A user with this ID already exists!";
        }

        Messenger.addUser(new User(id, username, password));
        return "User has been created successfully!";
    }

}
