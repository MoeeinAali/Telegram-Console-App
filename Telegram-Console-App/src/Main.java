import controller.Messenger;
import view.LoginMenu;
import view.MenuStatus;
import view.MessengerMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            switch (MenuStatus.getMenuStatus()) {
                case LOGIN_MENU:
                    LoginMenu loginMenu = new LoginMenu();
                    loginMenu.run(scanner);
                    break;
                case MESSENGER_MENU:
                    MessengerMenu messengerMenu = new MessengerMenu(Messenger.getCurrentUser());
                    messengerMenu.run(scanner);
                    break;
                case CHAT_MENU:
                    ChatMenu chatMenu = new ChatMenu(MessengerMenu.enteredChat, Messenger.getCurrentUser());
                    chatMenu.run(scanner);
                    break;
                case TERMINATE:
                    break;
            }
        }
    }
}