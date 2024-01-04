package study.socket.common;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArraySet;

public class ConnectionService implements AutoCloseable{
    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private InputResult result;
    private static CopyOnWriteArraySet <CreateFile> listOfFile;

    public ConnectionService(Socket socket) throws IOException {
        this.socket = Objects.requireNonNull(socket, "socket is null");
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void writeInputResult(InputResult result) throws IOException {
        if (result.getMessage()!=null){
            Message message = result.getMessage();
            message.setSentAt(ZonedDateTime.now());
            outputStream.writeObject(message);
            outputStream.flush();
        }else{
            CreateFile file =result.getFile();
            listOfFile.add(file);
            file = getAllFilesAtServer(file.getName());

        }

    }

    public InputResult readInputResult() throws IOException {
        try {
            result.setMessage(null);
            result.setFile(null);
            Object fileOrText= inputStream.readObject();
            if (Message.class==fileOrText.getClass()) {
                result.setMessage((Message) fileOrText);

            }else {
                result.setFile((CreateFile) fileOrText);




            }
        }  catch (ClassNotFoundException e) {
            System.out.println("сообщение не найдено");
            throw new RuntimeException();
        }
        return result;
    }
    public CreateFile getAllFilesAtServer(String name) throws IOException {
        for (CreateFile file1:listOfFile){
            System.out.println(file1.getName());
            if (name.equals(file1.getName())){
                return file1;
            }
        }
        CreateFile files =result.getFile();
        listOfFile.add(files);
        outputStream.writeObject(files);
        outputStream.flush();
        return null;
    }
    public String GetText(byte[] array){
        String text = new String(array, StandardCharsets.UTF_8);
        return text;
    }

    @Override
    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("Ошибка во время закрытия ресурсов");
        }
    }
}
