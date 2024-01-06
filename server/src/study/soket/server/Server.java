package study.soket.server;

import study.socket.common.ConnectionService;
import study.socket.common.Message;
import study.socket.common.SaveFiles;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
    private static final int PORT = 8090;
    private CopyOnWriteArraySet<ConnectionService> senders = new CopyOnWriteArraySet<>();
    private LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<>();
    private ArrayList<SaveFiles> files = new ArrayList<SaveFiles>();

    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started");
        new Thread(new Writer()).start();

        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new Reader(new ConnectionService(socket))).start();
        }
    }

    class Reader implements Runnable {
        ConnectionService connection;

        public Reader(ConnectionService connection) {
            this.connection = connection;
            senders.add(connection);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Message message = connection.readMessage();
                    if (message.getText().equals("exit")) {
                        senders.remove(connection);
                        break;
                    }
                    messages.put(message);
                    connection.setSender(message.getSender());
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Writer implements Runnable {

        @Override
        public void run() {
            Message messageOut = null;
            while (true) {
                try {
                    messageOut = messages.take();
                    String sender = messageOut.getSender();
                    for (ConnectionService c : senders) {
                        //if (!Objects.requireNonNull(sender).equals(c.getSender())) {
                        if (messageOut.getFile() != null) {
                            SaveFiles file = new SaveFiles(messageOut.getFileInfo(), messageOut.getFile());
                            files.add(file);
                            Path path = Paths.get("./SaveFiles/" + "file_from_client_" + messageOut.getSender() + "_" + file.getFile().getName());
                            byte[] fileContent = Files.readAllBytes(file.getFile().toPath());
                            Files.write(path, fileContent);
                            Message saveFiles = new Message(sender, "", file.getFile(), file.getFileInfo(), null, null);
                            try {
                                c.sendMessage(saveFiles);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (messageOut.getText().equals("3")) {
                            StringBuilder str = new StringBuilder();
                            int count = 0;
                            for (SaveFiles f : files) {
                                str.append("\n");
                                str.append(count + ". Описание файла: " + f.getFileInfo() + ". Файл: " + f.getFile().getName());
                                str.append("\n");
                                count++;
                            }
                            Message messageFiles = new Message(sender, "", null, null, str.toString(), null);
                            try {
                                c.sendMessage(messageFiles);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else if (messageOut.getSelectFile() != null) {
                            Message messageFileOut = new Message(sender, "", files.get(messageOut.getSelectFile()).getFile(), null, null, messageOut.getSelectFile());
                            try {
                                c.sendMessage(messageFileOut);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                c.sendMessage(messageOut);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        // }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}