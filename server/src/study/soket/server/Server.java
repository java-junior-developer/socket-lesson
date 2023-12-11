package study.soket.server;

import study.socket.common.ConnectionService;
import study.socket.common.Message;
import study.socket.common.commands.AllComands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

public class Server implements Runnable {
    int port;
    MyExecutorService myExecutorService;

    public Server(int port) {
        this.port = port;
        this.myExecutorService = new MyExecutorService (1,8,100,TimeUnit.MILLISECONDS,new ArrayBlockingQueue<>(100));
    }

    AllComands commands = new AllComands();
    @Override
    public void run() {
        try (ServerSocket SVsocket = new ServerSocket(2222)) {
            System.out.println("Server running");
            while (true) {
                try {

                    Socket socket = SVsocket.accept();
                    ConnectionService conn = new ConnectionService(socket);

                    Message message = conn.readMessage();
                    if (commands.getCommands().keySet().contains(message.getText())) {

                        commands.getCommands().get(message.getText()).execute();

                    }else{

                    System.out.println(message);
                    conn.writeMessage(new Message("from server"));}
                    conn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Соединение сорвалось");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Сервер не запустился");
        }
    }
}
