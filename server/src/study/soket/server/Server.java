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
    private int port;
    private MyExecutorService myExecutorService;
    private CopyOnWriteArraySet<ConnectionService> copyOnWriteArraySet;

    public Server(int port) {
        this.port = port;
        this.myExecutorService = new MyExecutorService(0, 8, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
        this.copyOnWriteArraySet =new CopyOnWriteArraySet<>();
    }

    public void run() {
        try (ServerSocket SVsocket = new ServerSocket(2222)) {
            System.out.println("Server running");
            while (true) {

                Socket socket = null;
                try {
                    socket = SVsocket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    myExecutorService.execute(new CreateHandler(socket, copyOnWriteArraySet));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

  
}
