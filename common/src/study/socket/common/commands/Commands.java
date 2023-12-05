package study.socket.common.commands;

public abstract class Commands implements Command {
    String name;
    private String description;
    private int count =0;
    int goodTransaction=0;

    public Commands( String name,String description) {
        this.name = name;
        this.description = description;
    }

    public Commands() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getGoodTransaction() {
        return goodTransaction;
    }

    public void setGoodTransaction(int goodTransaction) {
        this.goodTransaction = goodTransaction;
    }


}
