package study.soket.server;

import study.socket.common.ConnectionService;
import study.socket.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    private int port;

    public Server(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер запущен");
            while (true) {
                // throws IOException
                try (ConnectionService service = new ConnectionService(serverSocket.accept())){
                    Message message = service.readMessage();
                    System.out.println(message.getText());
                    service.writeMessage(new Message("from server"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Ошибка подключение клиента");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // скорее всего порт уже занят
            System.out.println("Ошибка создания serverSocket.");
        }
    }
}
