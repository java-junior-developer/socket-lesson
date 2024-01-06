package study.soket.server;

import study.socket.common.ConnectionService;
import study.socket.common.InputResult;
import study.socket.common.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CopyOnWriteArraySet;

public class ConnectServer implements Runnable{
    private ConnectionService service;
    private Server server;
    private CopyOnWriteArraySet<ConnectionService> copyOnWriteArraySet;



    public ConnectServer(ConnectionService service, Server server)
    {
        this.service = service;
        this.server = server;
    }

    public ConnectServer(ConnectionService connectionService) {
    }

    @Override
    public void run() {
        while (true) {
            try {
                InputResult result = service.readInputResult();
                if (result.getMessage() != null) {
                    System.out.println(result.getMessage().getText());
                    result.setMessage(new Message("from server"));
                    service.writeInputResult(result);
                } else if (result.getFile() != null) {
                    result.setMessage(new Message("Загружен новый файл " +
                            result.getFile().getFile().getName() + " " + result.getFile().getLen()));
                    for (ConnectionService con : copyOnWriteArraySet) {
                        con.writeInputResult(result);
                    }
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Ошибка подключение клиента");
            }

        }
    }
}
