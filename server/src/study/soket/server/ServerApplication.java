package study.soket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication  {
    public static void main(String[] args) {
        Server server = new Server(2222);
        server.run();
    }
}
