package study.socket.client;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientApplication {
    public static void main(String[] args) {

            InetSocketAddress address = new InetSocketAddress("127.0.0.1",2222);
            new Client(address).run();

            // Client client = new Client(address);
            // client.run();

    }
}
