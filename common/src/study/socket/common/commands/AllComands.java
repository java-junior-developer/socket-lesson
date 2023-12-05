package study.socket.common.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllComands {
   private Map<String,Commands> commands;


    public  AllComands() {
        this.commands = new HashMap<>();
        commands.put("help",new Help("help","список доступных запросов и их описание"));
        commands.put("ping",new Ping("ping","время ответа сервера"));
        commands.put("request",new Request("requests","количество успешно обработанных запросов"));
        commands.put("popular",new Popular("popular","название самого популярного запроса"));

    }

    public int size() {
        return commands.size();
    }

    public Map<String, Commands> getCommands() {
        return commands;
    }
}
