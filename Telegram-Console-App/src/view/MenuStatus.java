package view;

public enum MenuStatus {
    LOGIN_MENU,
    MESSENGER_MENU,
    CHAT_MENU,
    TERMINATE;

    private static MenuStatus status = MenuStatus.LOGIN_MENU;

    public static MenuStatus getMenuStatus() {
        return status;
    }

    public static void setMenuStatus(MenuStatus newStatus) {
        status = newStatus;
    }
}
