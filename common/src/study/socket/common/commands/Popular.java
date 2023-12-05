package study.socket.common.commands;

public class Popular extends Commands{
    AllComands arr=new AllComands();

    public Popular(String name, String description) {
        super(name, description);
    }

    @Override
    public void execute() {
        setCount(getCount()+1);
        int result =0;
        String x=null;
        for(String i:arr.getCommands().keySet()){
            if(result<arr.getCommands().get(i).getGoodTransaction()){

                result=arr.getCommands().get(i).getGoodTransaction();
                x=i;
            }
        }
        System.out.println(arr.getCommands().get(x).getName());
        setGoodTransaction(getGoodTransaction()+1);
    }
}
