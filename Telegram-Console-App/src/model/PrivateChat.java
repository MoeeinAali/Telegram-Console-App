package model;

import controller.Messenger;

public class PrivateChat extends Chat {
    public PrivateChat(User owner, String id, String name) {
        super(owner, id, name);
        this.setStatus(ChatStatus.PRIVATE_CHAT);
    }

    public String getName() {
        User currentUser = Messenger.getCurrentUser();
        if (super.getMembers().size() == 1) {
            return super.getName();
        } else if (super.getMembers().get(0).equals(currentUser)) {
            return super.getMembers().get(1).getName();
        } else {
            return super.getMembers().get(0).getName();
        }
    }

    @Override
    public String getId() {
        User currentUser = Messenger.getCurrentUser();
        if (super.getMembers().size() == 1) {
            return super.getId();
        } else if (super.getMembers().get(0).equals(currentUser)) {
            return super.getMembers().get(1).getId();
        } else {
            return super.getMembers().get(0).getId();
        }
    }

}
