package Server;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    //Сокет клиента
    private Socket client;
    //Для получения сообщений
    private BufferedReader messageIn;
    //Для отправки сообщений
    private BufferedWriter messageOut;
    //Имя клиента
    public String clientName;
    //Сообщение
    private String message;

    //Конструктор. Запускает поток
    public Client(Socket client) throws IOException {
        this.client = client;
        messageIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
        messageOut = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        start();
    }

    //Тело потока
    public void run() {
        try {
            getClientName();
            while (true) {
                message = GetMessage();
                for(Client c : Server.clients){
                    if (c != this) {
                        message = this.clientName + ": " + message;
                        c.SendMessage(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Получение имени клиента
    public void getClientName() throws IOException {
        System.out.println("Введите имя пользователя: ");
        clientName = messageIn.readLine();
        String message = clientName + " присоединился к чату";
        System.out.println(message);
        for(Client c_s : Server.clients){
            c_s.SendMessage(message);
        }
    }

    //Отправка сообщений клиенту
    public void SendMessage(String message) throws IOException {
        messageOut.write(message + "\n");
        messageOut.flush();
    }

    //Получение сообщения о клиента
    public String GetMessage() throws IOException {
        message = messageIn.readLine();
        if (message != null){
            System.out.println(clientName + ": " + message);
        }
        return message;
    }
}
