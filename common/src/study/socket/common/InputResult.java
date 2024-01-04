package study.socket.common;

public class InputResult {
    private Message message;
    private CreateFile file;

    public InputResult() {
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public CreateFile getFile() {
        return file;
    }

    public void setFile(CreateFile file) {
        this.file = file;
    }
}
