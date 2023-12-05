package study.socket.common.commands;

public class Ping extends Commands  {


    public Ping(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute() {
        setCount(getCount()+1);
        System.out.println("Время ответа сервера 2 сек");
        setGoodTransaction(getGoodTransaction()+1);
    }
}
