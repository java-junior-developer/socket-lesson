package study.socket.client;

import study.socket.common.ConnectionService;
import study.socket.common.Message;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client {
    private String ip;
    private int port;
    private Scanner scanner;
    private Socket socket;
    private ConnectionService connection;

    public Client(String ip, int port) throws IOException {
        this.ip = ip;
        this.port = port;
        scanner = new Scanner(System.in);
        socket = new Socket(ip, port);
    }

    public void run() throws Exception {
        System.out.println("Введите имя:");
        String name = scanner.nextLine();
        while (name.trim().length() <= 2) {
            System.out.println("Имя д.б. больше 2-х символов!");
            name = scanner.nextLine();
        }
        connection = new ConnectionService(socket);
        String message;
        String choice;
        File file;
        String fileInfo;
        String listFiles;
        Integer selectFile;

        Thread reader = new Thread(new Reader());
        reader.setDaemon(true);
        reader.start();

        while (true) {
            Thread.sleep(1000);
            System.out.println("Для отправки сообщения введите 1.");
            System.out.println("Для отправки файла введите 2.");
            System.out.println("Список загруженных на сервер файлов 3.");
            System.out.println("Выбор файла для загрузки 4.");
            System.out.println("Для выхода введите exit.");
            choice = scanner.nextLine();
            if (choice.equals("1")) {
                System.out.println("Введите сообщение:");
                message = scanner.nextLine();
                connection.sendMessage(
                        Message.
                                getMessage(
                                        name = name,
                                        message = message,
                                        file = null,
                                        fileInfo = null,
                                        listFiles = null,
                                        selectFile = null
                                )
                );
            } else if (choice.equals("2")) {
                System.out.println("Введите описание файла:");
                fileInfo = scanner.nextLine();
                System.out.println("Введите путь к файлу:");
                message = scanner.nextLine();
                file = Path.of(message).toFile();
                if (file.length()/(1024*1024) > 1) {
                    System.out.println("Файл больше 1 Мб");
                    continue;
                }
                connection.sendMessage(
                        Message.
                                getMessage(
                                        name = name,
                                        message = "",
                                        file = file,
                                        fileInfo = fileInfo,
                                        listFiles = null,
                                        selectFile = null
                                )
                );
            } else if (choice.equals("3")) {
                connection.sendMessage(
                        Message.
                                getMessage(
                                        name = name,
                                        message = choice,
                                        file = null,
                                        fileInfo = null,
                                        listFiles = null,
                                        selectFile = null
                                )
                );
            } else if (choice.equals("4")) {
                System.out.println("Введите номер файла:");
                selectFile = scanner.nextInt();
                connection.sendMessage(
                        Message.
                                getMessage(
                                        name = name,
                                        message = "",
                                        file = null,
                                        fileInfo = null,
                                        listFiles = null,
                                        selectFile = selectFile
                                )
                );
            } else if (choice.equals("exit")) {
                System.out.println("Отключение...");
                connection.close();
                break;
            }
        }
    }

    class Reader implements Runnable {

        @Override
        public void run() {
            Message fromServer = null;
            while (true) {
                try {
                    fromServer = connection.readMessage();
                    if (fromServer.getSelectFile() != null && fromServer.getFile() != null) {
                        Path path = Paths.get("./" + "file_from_server_" + fromServer.getFile().getName());
                        byte[] fileContent = Files.readAllBytes(fromServer.getFile().toPath());
                        Files.write(path, fileContent);
                        System.out.println("Файл загружен с сервера: " + path);
                    } else if (fromServer.getFile() != null) {
                        System.out.println("На сервер загружен новый файл. " + fromServer.getFileInfo() + " имя файла: " + fromServer.getFile().getName());
                    } else if (fromServer.getListFiles() != null) {
                        System.out.println("Список доступных файлов: " + fromServer.getListFiles());
                    } else {
                        System.out.println("Сообщение от пользователя: " + fromServer.getSender() + ". Текст сообщения: " + fromServer.getText());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}