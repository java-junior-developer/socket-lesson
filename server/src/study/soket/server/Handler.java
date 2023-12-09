package study.soket.server;

import study.socket.common.Message;

import java.util.Objects;

public class Handler {
    private MessageGenerator generator;
    private Server server;


    public Handler(MessageGenerator generator, Server server) {
        setGenerator(generator);
        this.server = server;
    }

    public void setGenerator(MessageGenerator generator) {
        this.generator = Objects.requireNonNull(generator);
    }

    public Message execute() {
        return generator.generate(server);
    }
}
