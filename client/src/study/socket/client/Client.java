package study.socket.client;

import study.socket.common.ConnectionService;
import study.socket.common.CreateFile;
import study.socket.common.InputResult;
import study.socket.common.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.nio.file.*;
import java.util.Scanner;


public class Client implements Runnable {
    private InetSocketAddress remoteAddress;
    private InputResult result = new InputResult();


    public Client(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;

    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Thread thread1 =new Thread(()-> {
            while (true) {

                result.setMessage(null);
                result.setFile(null);
                System.out.println("Введите текст путь к файлу или /exit");
                String text = scanner.nextLine();
                if (text.equals("/exit")) break;
                if (text.endsWith(".txt")) {

                    System.out.println("Введите колличество символов для описания файла");
                    int len = scanner.nextInt();
                    System.out.println("Введите размер файла в Mb");
                    int size = scanner.nextInt();
                    byte [] array = null;
                    try {
                        array = Files.readAllBytes(Paths.get(text));
                    } catch (IOException e) {
                        System.out.println("файл не прочитать");
                    }
                    result.setFile(new CreateFile(new File(text), text, len, size, array));

                } else {
                    result.setMessage(new Message(text));
                }
            }
        });

        Thread thread2 = new Thread(()->{
            try (Socket socket = new Socket()) {
                socket.connect(remoteAddress);
                try (ConnectionService service = new ConnectionService(socket)) {
                    service.writeInputResult(result);
                    InputResult result1 = service.readInputResult();
                    System.out.println(result1.getMessage().getText());
                }

            } catch (IOException e) {
                System.out.println("Сервер не отвечает");
            }

        });
    }
}