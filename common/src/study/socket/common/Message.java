package study.socket.common;


import java.io.Serializable;
import java.time.LocalDate;

public class Message implements Serializable {
    private static final long serialVersionUID =1L;
    String text;
    LocalDate sendAt;

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDate sendAt) {
        this.sendAt = sendAt;
    }
}
