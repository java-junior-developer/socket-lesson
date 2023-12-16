package study.soket.server;

import study.socket.common.ConnectionService;
import study.socket.common.CreateHandler;
import study.socket.common.SendFile;

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
    private CopyOnWriteArraySet<SendFile> copyOnWriteArraySetF;


    public Server(int port) {
        this.port = port;
        this.myExecutorService = new MyExecutorService(0, 8, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100));
        this.copyOnWriteArraySet =new CopyOnWriteArraySet<>();
        this.copyOnWriteArraySetF =new CopyOnWriteArraySet<>();
    }

    public void run() {
        ServerSocket SVsocket = null;
        try {
            SVsocket = new ServerSocket(2222);
        } catch (IOException e) {
            System.out.println("не запустилось на сервере");;
        }
        System.out.println("Server running");
        try {

            while (true) {

                Socket socket = null;
                try {
                    socket = SVsocket.accept();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    myExecutorService.execute(new CreateHandler(socket, copyOnWriteArraySet,copyOnWriteArraySetF));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        }
    }

  

