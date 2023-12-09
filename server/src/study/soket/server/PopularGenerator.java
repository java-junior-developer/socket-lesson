package study.soket.server;

import study.socket.common.Message;

import java.util.*;
import java.util.stream.Collectors;

public class PopularGenerator implements MessageGenerator{
    Map<String, Integer> popularAnswer = new HashMap<>();

    @Override
    public Message generate(Server server) {
        server.setCount(server.getCount()+1);
       for (Message message : server.getMessages()){
           popularAnswer.put(message.getText(),
                   popularAnswer.containsKey(message.getText()) ? popularAnswer.get(message.getText())+1 : 1);
       }
       int frequency = 0;
       for (Map.Entry<String, Integer> entry : popularAnswer.entrySet()){
           frequency = entry.getValue() > frequency ? entry.getValue() : frequency;
       }
       String text = null;
       for (Map.Entry<String, Integer> entry : popularAnswer.entrySet()){
            if(frequency == entry.getValue()){
                text = entry.getKey();
            }
        }

        return new Message("название самого популярного запроса - " + text);
    }
}
