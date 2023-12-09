package study.soket.server;

import study.socket.common.Message;

public class HelpGenerator implements MessageGenerator{
    @Override
    public Message generate(Server server) {
        server.setCount(server.getCount()+1);
        return new Message("""
                Сервер может обрабатывать следующие запросы:
                /help - список доступных запросов и их описание
                /ping - время ответа сервера
                /requests - количество успешно обработанных запросов
                /popular - название самого популярного запроса
                """);
    }
}
