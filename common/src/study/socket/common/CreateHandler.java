package study.socket.common;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArraySet;

public class CreateHandler implements Runnable {
    private ConnectionService conn;
    CopyOnWriteArraySet<ConnectionService> copyOnWriteArraySet;
    CopyOnWriteArraySet<SendFile> copyOnWriteArraySetF;


    public CreateHandler(Socket socket, CopyOnWriteArraySet<ConnectionService> copyOnWriteArraySetM, CopyOnWriteArraySet<SendFile> copyOnWriteArraySetF) throws IOException {
        this.conn = new ConnectionService(socket);
        this.copyOnWriteArraySet = copyOnWriteArraySetM;
        this.copyOnWriteArraySetF = copyOnWriteArraySetF;


    }

    public ConnectionService getConnectionService() {
        return conn;
    }

    public void setConnectionService(ConnectionService connectionService) {
        this.conn = connectionService;
    }

    @Override
    public void run() {
        try {
            ClientInput client = new ClientInput();
            try {
                client = conn.readMessage();
            } catch (IOException e) {
                System.out.println("не удалось прочитать сообщение");
                ;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (client.getMessage() != null) {
                Message message = client.getMessage();
                if ("exit".equals(message.getText())) {
                    return;
                } else {
                    System.out.println(message.getText());
                }
                getMessageToAllClients(copyOnWriteArraySet,copyOnWriteArraySetF, client);
            } else {
                getMessageToAllClients(copyOnWriteArraySet,copyOnWriteArraySetF, client);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void getMessageToAllClients(CopyOnWriteArraySet<ConnectionService> copyOnWriteArraySet, CopyOnWriteArraySet<SendFile> copyOnWriteArraySetF, ClientInput client) throws IOException {
        if (client.getFile() != null) {
            if ("set".equalsIgnoreCase(client.getFile().getAction())) {
                copyOnWriteArraySetF.add(client.getFile());
                for (ConnectionService con : copyOnWriteArraySet) {

                    con.writeMessage(client);

                }
            } else {
                for (SendFile send : copyOnWriteArraySetF) {
                    ClientInput clientInput = new ClientInput();
                    clientInput.setFile(send);
                    conn.writeMessage(clientInput);
                }

            }
        } else {
            for (ConnectionService con : copyOnWriteArraySet) {
                con.writeMessage(client);
            }
        }

    }
}

