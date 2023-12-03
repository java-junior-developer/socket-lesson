package study.socket.common;


import java.time.ZonedDateTime;

public class Message {
    private String text;
    private ZonedDateTime sentAt;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public ZonedDateTime getSenTAt() {
        return sentAt;
    }

    public void setSentAt(ZonedDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
