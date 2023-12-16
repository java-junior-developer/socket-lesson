package study.socket.common;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Objects;

public class ConnectionService implements AutoCloseable {


    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public ConnectionService(Socket socket) throws IOException {
        this.socket = Objects.requireNonNull(socket, "socket is null");
        this.output = new ObjectOutputStream(socket.getOutputStream());
        this.input = new ObjectInputStream(socket.getInputStream());

    }

    public void writeMessage(ClientInput input) throws IOException {
        if (input.getMessage() != null) {
            input.getMessage().setSendAt(LocalDate.now());
            output.writeObject(input.getMessage());
            output.flush();
        } else {
            output.writeObject(input.getFile());
            output.flush();
        }
    }

    public ClientInput readMessage() throws IOException, ClassNotFoundException {
        ClientInput output = new ClientInput();
        if (Message.class.equals(input.readObject())) {

            output.setMessage((Message) input.readObject());
        } else {
            output.setFile((SendFile) input.readObject());
        }
        return output;
    }


        @Override
        public void close () throws Exception {
            try {
                input.close();
                output.close();
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("ошибка во время закрытия ресурсов");
            }
        }
    }
