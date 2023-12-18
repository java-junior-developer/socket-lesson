package study.socket.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SendFile extends Message {
    private String path;
    private int size =5;
    private int description=10;
    private byte[] array;
    private File file;
    private String action;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getDescription() {
        return description;
    }

    public void setDescription(int description) {
        this.description = description;
    }

    public byte[] getArray() {
        return array;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public SendFile(String path) throws IOException {
        super(path);
        String[] parts = this.text.split(" ");
        this.path=parts[1];
        this.action = parts[0];
        file = new File(this.path);
        if (file.isFile()){
            if (file.getUsableSpace()<size&& file.getName().length()<description){
                array= Files.readAllBytes(Paths.get(path)); {
                }
            }
        }
    }
}
