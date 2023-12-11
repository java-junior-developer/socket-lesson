package study.soket.server;

import study.socket.common.ConnectionService;
import study.socket.common.Message;
import study.socket.common.commands.AllComands;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {
    int port;
    MyExecutorService myExecutorService;
    CopyOnWriteArraySet copyOnWriteArraySet;

    public Server(int port) {
        this.port = port;
        this.myExecutorService = new MyExecutorService(0, 8, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
        this.copyOnWriteArraySet =new CopyOnWriteArraySet<>();
    }

    AllComands commands = new AllComands();

    @Override
    public void run() {
        try (ServerSocket SVsocket = new ServerSocket(2222)) {
            System.out.println("Server running");
            while (true) {
                try {
                    Socket socket = SVsocket.accept();
                    myExecutorService.execute(() -> handler(socket));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handler(Socket socket) {
            ConnectionService conn = null;
            try {
                conn = new ConnectionService(socket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Message message = null;
            try {
                message = conn.readMessage();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (commands.getCommands().keySet().contains(message.getText())) {

                commands.getCommands().get(message.getText()).execute();

            } else {

                System.out.println(message.getText());
                try {
                    conn.writeMessage(new Message("from server"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
