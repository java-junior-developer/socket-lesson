package study.socket.common.commands;

public class Request extends Commands{
    AllComands arr=new AllComands();

    public Request(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute() {
        setCount(getCount()+1);
        int result =0;
        for(String i:arr.getCommands().keySet()){
            result+=arr.getCommands().get(i).getCount();
        }


        System.out.println("Количество успешно обработанных запросов = " + result);
        setGoodTransaction(getGoodTransaction()+1);
    }
}
