package study.soket.server;

import com.sun.jdi.ClassNotPreparedException;

import java.io.IOException;

public class ServerApplication {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        try {
            server.run();
        } catch (ClassNotPreparedException e) {
            e.printStackTrace();
        }
    }
}
