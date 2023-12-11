package study.socket.client;

import study.socket.common.ConnectionService;
import study.socket.common.Message;

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
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        Thread thread1 = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                String line;
                while ((line = reader.readLine()) != "exit") {
                    Message message = new Message(line);
                    connectionService.writeMessage(message);
                    connectionService.close();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        Thread thread2 = new Thread(() -> {


            try (BufferedWriter bw = new BufferedWriter(new FileWriter("***.txt"))) {

                String line = null;
                try {
                    line = connectionService.readMessage().getText();
                    System.out.println(line);
                } catch (IOException e) {
                    bw.write(line);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });
        thread1.start();
        thread2.start();


    }
}
