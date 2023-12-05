package study.socket.client;

import study.socket.common.ConnectionService;
import study.socket.common.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client implements Runnable {
    InetSocketAddress inetSocketAddress;

    public Client(InetSocketAddress inetSocketAddress) {
        this.inetSocketAddress = inetSocketAddress;
    }

    @Override
    public void run() {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            ConnectionService connectionService =new ConnectionService(new Socket(inetSocketAddress.getAddress(), inetSocketAddress.getPort()));
        ){
            String line;
            while ((line = reader.readLine()) != "exit"){
                Message message = new Message(line);
                connectionService.writeMessage(message);
                message= connectionService.readMessage();
                System.out.println(message);
                connectionService.close();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
