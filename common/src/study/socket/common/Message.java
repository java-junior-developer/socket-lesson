package study.socket.common;


import java.io.Serializable;
import java.time.ZonedDateTime;

public class Message implements Serializable {
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
