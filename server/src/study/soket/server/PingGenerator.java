package study.soket.server;

import study.socket.common.Message;

import java.io.IOException;

public class PingGenerator implements MessageGenerator{
    public Message generate(Server server) {
        server.setCount(server.getCount()+1);
        return new Message("Время ответа сервера " + (System.currentTimeMillis() - server.getTimeIn()) + " (millis)");
    }
}
