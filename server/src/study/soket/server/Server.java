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
        int requestCont =0;
        String popular = "";
        long responseTime;

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер запущен");
            while (true) {
                // throws IOException
                try (ConnectionService service = new ConnectionService(serverSocket.accept())){
                    Message message = service.readMessage();
                    responseTime = System.currentTimeMillis();
                    if (message.getText().equals("/help")) {
                        System.out.println("Список доступных запросов:\n/ping - время ответа сервера" +
                                "\n/" + "requests - количество успешно обработанных запросов" +
                                "\n/" + "popular - название самого популярного запроса");
                    } else if (message.getText().equals("/ping")) {
                        System.out.println("время ответа сервера: " + (System.currentTimeMillis() - responseTime));
                    } else if (message.getText().equals("/requests")) {
                        System.out.println("количество успешно обработанных запросов "+ requestCont);
                    } else if (message.getText().equals("/popular")) {
                        System.out.println("название самого популярного запроса " + popular);
                    } else {
                        System.out.println("Сервер не может обработать запрос");
                    }
                    requestCont++;
                    if (requestCont==1) {
                        popular= message.getText();
                    }
                    //System.out.println(message.getText());
                    service.writeMessage(new Message("from server"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Ошибка подключение клиента");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // скорее всего порт уже занят
            System.out.println("Ошибка создания serverSocket.");
        }

    }
}

