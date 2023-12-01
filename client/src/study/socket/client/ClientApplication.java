package study.socket.client;


import java.net.InetSocketAddress;

public class ClientApplication {
    public static void main(String[] args) {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 2222);
        new Client(address).run();

        // Client client = new Client(address);
        // client.run();
    }
}
