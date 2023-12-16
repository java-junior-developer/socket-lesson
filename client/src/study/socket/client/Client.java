package study.socket.client;

import study.socket.common.ClientInput;
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
            ;
        }

    }

    @Override
    public void run() {
        Thread thread1 = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while ((line = reader.readLine()) != "exit") {
                    if (line.endsWith(".txt")) {
                        ClientInput client = new ClientInput();
                        client.setFile(new SendFile(line));
                        connectionService.writeMessage(client);
                        connectionService.close();
                    } else {
                        ClientInput client = new ClientInput();
                        client.setMessage(new Message(line));
                        connectionService.writeMessage(client);
                        connectionService.close();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {


            try {

                String line = null;
                try {
                    ClientInput client = connectionService.readMessage();
                   if (client.getMessage()!=null){
                       line = client.getMessage().getText();
                   }else if ("set".equalsIgnoreCase(client.getFile().getAction())){
                       line = client.getFile().getPath() + " " + client.getFile().getDescription();
                   }
                    System.out.println(line);
                } catch (IOException e) {
                    System.out.println("не удалось прочитать файл");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                System.out.println("соединение сброшено");
            }


        });
        thread1.start();
        thread2.start();


    }
}
