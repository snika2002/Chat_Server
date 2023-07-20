package Client;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    private static Socket client;
    public static BufferedReader messageIn;
    static BufferedWriter messageOut;
    static BufferedReader reader;
    private static String name;

    private static String message;

    public Client(String addr, int port) throws IOException {
        this.client = new Socket(addr, port);
        reader = new BufferedReader(new InputStreamReader(System.in));
        messageIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
        messageOut = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        GetName();
        System.out.println("Введите сообщение: ");
        ReadMsg readMsg = new ReadMsg();
        WriteMsg writeMsg = new WriteMsg();
        readMsg.start();
        writeMsg.start();
    }

    private static void GetName() throws IOException {
        System.out.println("Введите имя:");
        String name = reader.readLine();
        SendMessage(name);
        message = messageIn.readLine();
        System.out.println(message);
    }

    private static void SendMessage(String message) throws IOException {
        messageOut.write(message + "\n");
        messageOut.flush();
    }

    private static void GetMessage() throws IOException {
        message = messageIn.readLine();
        if(message != null)
            System.out.println(message);
    }

    private class WriteMsg extends Thread {

        @Override
        public void run() {
            String message;
            while (true) {
                try {
                    message = reader.readLine();
                    if(message != null)
                    {
                        SendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ReadMsg extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    GetMessage();
                }
            } catch (IOException e) {

            }
        }
    }
}
