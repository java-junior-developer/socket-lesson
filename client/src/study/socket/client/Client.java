package study.socket.client;

import study.socket.common.ConnectionService;
import study.socket.common.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private InetSocketAddress remoteAddress;

    /*2. Клиент:
2.1. запрашивает текст сообщения (запрос) у пользователя
2.2. устанавливает соединение с сервером
2.3. создает экземпляр сообщения
2.4. отправляет сообщение на сервер
2.5. получает ответ
2.6. выводит полученный ответ в консоль
2.7. закрывает соединение с сервером
И так до тех пор, пока пользователь не введет '/exit'*/

    public Client(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
                System.out.println("Введите текст или /exit");
                String text = scanner.nextLine();
                if (text.equals("/exit")) break;

                try (Socket socket = new Socket()) {
                    socket.connect(remoteAddress);
                    try (ConnectionService service = new ConnectionService(socket)) {
                        service.writeMessage(new Message(text));

                        Message message = service.readMessage();
                        System.out.println(message);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Сервер перестал отвечать");
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Сервер не доступен");
                }



        }

    }
}
