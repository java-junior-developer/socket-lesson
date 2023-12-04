package study.socket.client;

import study.socket.common.ConnectionService;
import study.socket.common.Message;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

// При создании клиента в конструктор передается
// экземпляр InetSocketAddress,
// который хранит IP сервера и порт.
public class Client implements Runnable, Serializable {
    private InetSocketAddress remoteAddress;

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

            try (Socket socket = new Socket(remoteAddress.getHostName(), remoteAddress.getPort())){

            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try (Socket socket = new Socket()){
                socket.connect(remoteAddress);
                try (ConnectionService service = new ConnectionService(socket)){
                    service.writeMessage(new Message(text));
                    Message message = service.readMessage();
                    System.out.println(message.getText());
                } catch (IOException e){
                    System.out.println("Сервер перестал отвечать");
                }
            } catch (IOException e){
                System.out.println("Сервер не доступен");
            }
        }
    }

}
