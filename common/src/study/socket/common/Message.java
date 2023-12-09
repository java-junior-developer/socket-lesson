package study.socket.common;


import java.io.Serializable;
import java.time.ZonedDateTime;

public class Message implements Serializable {
//     лиент и сервер общаютс€ по средствам передачи сообщений.
//    —ообщение хранит: текст сообщени€ и дату и врем€ отправки сообщени€ с временной зоной.
private String text;
private ZonedDateTime sentAt;

    public Message(String text) {
        this.text = text;
    }


    public String getText() {
        return text;
    }

    public ZonedDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(ZonedDateTime sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public String toString() {
        return getText() + " " + getSentAt();
    }
}
