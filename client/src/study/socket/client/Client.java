package study.socket.client;

import study.socket.common.ConnectionService;
import study.socket.common.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {
    private InetSocketAddress remoteAddress;

    public Client(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите тест или /exit");
            String text = scanner.nextLine();
            if (text.equals("/exit")) break;
//            try (Socket socket = new Socket(remoteAddress.getHostName(), remoteAddress.getPort())){
//
//
//            } catch (UnknownHostException e) {
//                throw new RuntimeException(e);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }

            try (Socket socket = new Socket()) {
                socket.connect(remoteAddress);
                try (ConnectionService service = new ConnectionService(socket)) {
                    service.writeMessage(new Message(text));
                    Message message=service.readMessage();
                    System.out.println(message.getText());
                } catch (IOException e){
                    System.out.println("Сервер перестал отвечать");
                }

            } catch (Exception e) {
                System.out.println("Сервер не доступен");
            }





//       try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//           ConnectionService connectionService = new ConnectionService
//                   (new Socket(remoteAddress.getHostName(), remoteAddress.getPort()));{
//           String line = null;
//           while ((line=reader.readLine())!="exit"){
//               Message message = new Message(line);
//
//           }
//       } catch (IOException e) {
//           throw new RuntimeException(e);
//        }
        }

    }
}
