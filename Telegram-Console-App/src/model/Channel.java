package model;

public class Channel extends Chat {

    public Channel(User owner, String id, String name) {
        super(owner, id, name);
        this.setStatus(ChatStatus.CHANNEL_CHAT);
    }
}
