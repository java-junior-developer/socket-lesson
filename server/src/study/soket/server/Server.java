package study.soket.server;

import study.socket.common.ConnectionService;
import study.socket.common.InputResult;
import study.socket.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(port);){
            System.out.println("Сервер запущен");
            executorService.execute(new ConnectServer(serverSocket));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

