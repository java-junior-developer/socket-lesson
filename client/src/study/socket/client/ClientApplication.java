package study.socket.client;


import java.io.IOException;
import java.net.InetSocketAddress;

public class ClientApplication {
    public static void main(String[] args) throws Exception {

        try {
            new Client("127.0.0.1", 8090).run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
