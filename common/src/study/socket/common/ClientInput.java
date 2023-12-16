package study.socket.common;

public class ClientInput {
    private SendFile file;
    private Message message;

    public SendFile getFile() {
        return file;
    }

    public void setFile(SendFile file) {
        this.file = file;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
