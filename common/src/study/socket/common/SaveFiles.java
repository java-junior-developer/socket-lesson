package study.socket.common;
import java.io.File;

public class SaveFiles {
    private String fileInfo;
    private File file;

    public SaveFiles(String fileInfo, File file) {
        this.fileInfo = fileInfo;
        this.file = file;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}