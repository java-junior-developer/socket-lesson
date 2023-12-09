package study.socket.client;


import java.net.InetSocketAddress;

public class ClientApplication {
    public static void main(String[] args) {
        InetSocketAddress remoteAddress = new InetSocketAddress("127.0.0.1", 4000);
        Client client = new Client(remoteAddress);
        client.run();
    }
}
