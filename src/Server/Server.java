package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    //Список клиентов
    public static LinkedList<Client> clients = new LinkedList<>();
    //Сокет сервера
    private static ServerSocket server;
    //Порт для подключения
    private static int port = 4141;
    public static void main(String[] args) {
        try {
            server = new ServerSocket(port);
            System.out.println("Сервер запущен");
            while (true) {
                Socket client = server.accept();
                clients.add(new Client(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
