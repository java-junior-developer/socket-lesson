package study.socket.common.commands;

public class Help extends Commands{
    AllComands arr= new AllComands();
    public Help(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute() {
        setCount(getCount()+1);
        for(String i:arr.getCommands().keySet()) {
            System.out.println(arr.getCommands().get(i).getName() + ": " +
                    arr.getCommands().get(i).getDescription());
        }
        setGoodTransaction(getGoodTransaction()+1);
    }
    }

