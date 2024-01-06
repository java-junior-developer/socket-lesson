package study.socket.common;

import java.time.ZonedDateTime;
import java.io.File;
import java.io.Serializable;


public class Message implements Serializable {
    private String sender;
    private String text;
    private ZonedDateTime dateTime;
    private File file;
    private String fileInfo;
    private String listFiles;
    private Integer selectFile;

    public Message(String sender, String text, File file, String fileInfo, String listFiles, Integer selectFile) {
        this.sender = sender;
        this.text = text;
        this.file = file;
        this.fileInfo = fileInfo;
        this.listFiles = listFiles;
        this.selectFile = selectFile;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDateTime() {
        dateTime = ZonedDateTime.now();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }

    public String getListFiles() {
        return listFiles;
    }

    public void setListFiles(String listFiles) {
        this.listFiles = listFiles;
    }

    public Integer getSelectFile() {
        return selectFile;
    }

    public void setSelectFile(Integer listFiles) {
        this.selectFile = selectFile;
    }



    @Override
    public String toString() {
        return "{" +
                "Отправитель='" + sender + '\'' +
                ", текст='" + text + '\'' +
                ", дата=" + dateTime +
                '}';
    }

    public static Message getMessage(String sender, String text, File file, String fileInfo, String listFiles, Integer selectFile) {
        return new Message(sender, text, file, fileInfo, listFiles, selectFile);
    }
}