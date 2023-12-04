package study.soket.server;

import study.socket.common.ConnectionService;
import study.socket.common.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;




public class Server implements Runnable{
    private int port;
    private static int requestCount = 0;
    private static Map<Message, Integer> commandCount = new HashMap<Message, Integer>();
    public Server(int port) {
        this.port = port;

    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен. Ожидание клиента...");

        while (true) {
            //Socket socket = serverSocket.accept();
            System.out.println("Клиент подключен.");

            // Увеличение счетчика запросов
            requestCount++;

            // Получение потоков для чтения и записи данных
            try (ConnectionService service = new ConnectionService(serverSocket.accept())){
                Message message = service.readMessage();
                System.out.println(message.getText()); // Чтение команды от клиента
                String response = processCommand(message);
                service.writeMessage(new Message(response));
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Ошибка подключение клиента");
            }


        }
    } catch (IOException ex) {
        System.out.println("Ошибка ввода/вывода: " + ex.getMessage());
    }
}

    private  String processCommand(Message message) throws IOException {
        String response = null;
        switch (message.getText()) {
            case "/help":
                response = "Список доступных запросов:\n" +
                        "/help - список доступных запросов и их описание\n" +
                        "/ping - время ответа сервера\n" +
                        "/requests - количество успешно обработанных запросов\n" +
                        "/popular - название самого популярного запроса";
                break;
            case "/ping":
                String hostname = "127.0.0.1";
                int port = 2222;
                try {
                    long startTime = System.currentTimeMillis();

                    Socket socket = new Socket(hostname, port);

                    long endTime = System.currentTimeMillis();

                    long pingTime = endTime - startTime;

                    response="Ping time: " + pingTime + "ms";

                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error during ping: " + e.getMessage());
                }
                break;
                
            case "/requests":
                response = "Количество успешно обработанных запросов: " + requestCount;
                break;
            case "/popular":
                if (commandCount.isEmpty()) {
                    response = "Нет данных о популярных запросах.";
                } else {
                    Map.Entry<Message, Integer> mostPopular = Collections.max(commandCount.entrySet(), Map.Entry.comparingByValue());
                    String a = mostPopular.getKey().getText();
                    response = "Самый популярный запрос: " + a;
                }
                break;
            default:
                response = "Ошибка: Неизвестная команда.";
                break;
        }

        // Увеличение счетчика использования команды
        if (commandCount.containsKey(message)) {
            commandCount.put(message, commandCount.get(message) + 1);
        } else {
            commandCount.put(message, 1);
        }

        return response;
    }
    }





