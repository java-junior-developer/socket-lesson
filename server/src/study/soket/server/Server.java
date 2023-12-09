package study.soket.server;

import study.socket.common.ConnectionService;
import study.socket.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements Runnable{
    private int port;
    private Handler handler;
    private Map<String, MessageGenerator> generators = new HashMap<>();
    private List<Message> messages = new ArrayList<>();
    private long timeIn;
    private long count = 0;

    public Server(int port) {
        this.port = port;
        this.handler = new Handler(new DefaultGenerator(), this);
        addGenerators();

    }
    private void addGenerators(){
        generators.put("/help", new HelpGenerator());
        generators.put("/ping", new PingGenerator());
        generators.put("/request", new RequestGenerator());
        generators.put("/popular", new PopularGenerator());
        generators.put("/default", new DefaultGenerator());
    }

    public List<Message> getMessages() {
        return messages;
    }

    public long getTimeIn() {
        return timeIn;
    }

    public long getCount() {
        return count;
    }

    protected void setCount(long count) {
        this.count = count;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Сервер запущен");
            while (true) {
                // throws IOException
               try (ConnectionService service = new ConnectionService(serverSocket.accept())) {
                   Message message = service.readMessage();
                   timeIn = System.currentTimeMillis();
                   messages.add(message);
                   System.out.println(message);

                   handler.setGenerator(
                           generators
                                   .getOrDefault(message.getText()
                                           , generators.get("/default")));
                   Message out = handler.execute();
                   service.writeMessage(out);


               } catch (IOException e) {
                   System.out.println(e.getMessage());
                   System.out.println("Ошибка подключения клиента");
                   count--;
               }
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
            // скорее всего порт занят
            System.out.println("Ошибка создания serverSocket");
        }

    }
}
