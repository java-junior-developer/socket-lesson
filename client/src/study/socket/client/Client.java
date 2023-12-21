package study.socket.client;

import study.socket.common.ConnectionService;
import study.socket.common.Message;
import study.socket.common.SendFile;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Runnable {
    private InetSocketAddress inetSocketAddress;
    private ConnectionService connectionService;

    public Client(InetSocketAddress inetSocketAddress, ConnectionService connectionService) {
        this.inetSocketAddress = inetSocketAddress;
        try {
            this.connectionService = new ConnectionService(new Socket(inetSocketAddress.getAddress(), inetSocketAddress.getPort()));
        } catch (IOException e) {
            System.out.println("не удалось создать соединение");
            
        }

    }

    @Override
    public void run() {
        Thread thread1 = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while ((line = reader.readLine()) != "exit") {
                    if (line.endsWith(".txt")) {
                       Message msg = new Message(line);
                        connectionService.writeMessage(msg);
                        System.out.println(checkMassage(msg));
                    }
                }
            } catch (IOException e) {
                System.out.println("не удалось прочитать");
                try {
                    connectionService.close();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {
            while(true) {

                try {

                    String line = null;
                    try {
                        Message message = connectionService.readMessage();

                    } catch (IOException e) {
                        System.out.println("не удалось прочитать файл");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    System.out.println("соединение сброшено");
                }

            }
        });
        thread1.start();
        thread2.start();


    }
    private String checkMassage(Message message) throws IOException {
        if (!message.getText().endsWith(".txt") && message != null) {
            return message.getText();
        } else if (message.getText().endsWith(".txt")) {
            SendFile messageFile = new SendFile(message.getText());
            if ("set".equalsIgnoreCase(messageFile.getAction())) {
              return messageFile.getPath() + " " + messageFile.getDescription();
            }else{
                return messageFile.getPath() + " " + messageFile.getDescription();
            }

        }

        return null;
    }
}
