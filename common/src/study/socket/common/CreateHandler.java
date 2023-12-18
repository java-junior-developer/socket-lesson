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
        while (true) {
            Message message = null;
            try {

                message = conn.readMessage();
            } catch (IOException e) {
                System.out.println("Не удалось прочитать сообщение");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (message.getText() != null) {
                if ("exit".equals(message.getText())) {
                    return;
                } else if (!message.getText().endsWith(".txt")) {
                    System.out.println(message.getText());
                    try {
                        getMessageToAllClients(copyOnWriteArraySet, copyOnWriteArraySetF, message);
                    } catch (IOException e) {
                        System.out.println("не получилось прочитать данные с сервера");
                    }
                }

            } else {
                try {
                    getMessageToAllClients(copyOnWriteArraySet, copyOnWriteArraySetF, message);
                } catch (IOException e) {
                    System.out.println("не получилось прочитать данные с сервера");
                }
            }
        }
    }

    private void getMessageToAllClients(CopyOnWriteArraySet<ConnectionService> copyOnWriteArraySet, CopyOnWriteArraySet<SendFile> copyOnWriteArraySetF,Message message) throws IOException {
        if (message.getText().endsWith("txt")) {
            SendFile sendFile = new SendFile(message.getText());
            if ("set".equalsIgnoreCase(sendFile.getAction())) {
                copyOnWriteArraySetF.add(sendFile);
                for (ConnectionService con : copyOnWriteArraySet) {

                    con.writeMessage(message);

                }
            } else {
                for (SendFile send : copyOnWriteArraySetF) {
                   conn.writeMessage(send);
                }

            }
        } else {
            for (ConnectionService con : copyOnWriteArraySet) {
                con.writeMessage(message);
            }
        }

    }
}

