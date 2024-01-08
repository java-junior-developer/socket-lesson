package study.socket.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.util.Objects;

public class ConnectionService implements AutoCloseable {
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String sender;

    public ConnectionService(Socket socket) throws IOException {
        this.socket = Objects.requireNonNull(socket, "socet is null");
        outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        inputStream = new ObjectInputStream(this.socket.getInputStream());

    }
    public void setSender(String senders) {
        this.sender = senders;
    }

    public String getSender() {
        return sender;
    }

    public void sendMessage(Message message) throws IOException {
        message.setDateTime();
        outputStream.writeObject(message);
        outputStream.flush();
    }
    public Message readMessage() throws IOException{
        try {
            return (Message) inputStream.readObject();
        }catch (ClassNotFoundException  | IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Ошибка во время закрытия ресурсов");
        }
    }
}
