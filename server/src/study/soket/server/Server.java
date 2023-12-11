package study.soket.server;
import study.socket.common.ConnectionService;
import study.socket.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server implements Runnable{
    private int port;
    private Handler handler;
    private Map<String,MessageGenerator> generators;

    public Server(int port) {
        this.port = port;
        this.handler = new Handler(new DefaultGenerator());
       // generators.put("/help",new HelpGenerator());
       // generators.put("/ping",new PingGenerator());
       // generators.put("/popular",new PopularGenerator());
        //generators.put("/defualt",new DefaultGenerator());
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен");

            while (true) {
                // throws IOException
                try (ConnectionService service = new ConnectionService(serverSocket.accept())) {
                    Message message = service.readMessage();
                    handler.setGenerator(generators
                            .getOrDefault(message.getText(),
                                    generators.get("/defualt")));
                    if(("/help").equals(message.getText())){
                        handler.setGenerator(new HelpGenerator());
                    }else if(("/ping").equals(message.getText())){
                        handler.setGenerator(new PingGenerator());}
                    else {
                        handler.setGenerator(new DefaultGenerator());}
                    handler.execute();
                    System.out.println(message.getText());
                    service.writeMessage(new Message("from server"));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Ошибка подключение клиента");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            // скорее всего порт уже занят
            System.out.println("Ошибка создания serverSocket.");
        }

    }
}
