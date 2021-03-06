import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Connection {
    private PrintWriter toClient;
    private Scanner fromClient;
    private Socket client;
    private EchoServerSocket server;

    public Connection(Socket client, EchoServerSocket server) {
        try {
            this.server = server;
            this.client = client;
            toClient = new PrintWriter(client.getOutputStream(), true);
            fromClient = new Scanner(new InputStreamReader(client.getInputStream()));
            new Thread(receiverMessagesTask).start();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void sendMessage(String message) {
        toClient.println(message);
    }

    private Runnable receiverMessagesTask = () -> {
        while (true) {
            String messageFromClient = fromClient.nextLine();
            if (messageFromClient != null && !messageFromClient.endsWith("'exit'")) {
                System.out.println(messageFromClient);
                server.sendAll(messageFromClient);
            } else if (messageFromClient.endsWith("'exit'")) {
                server.sendAll(messageFromClient.split(":")[0] + " left the chat");
                server.setCountUsers(server.getCountUsers() - 1);
                server.sendAll("there is " + server.getCountUsers() + " users in the chat");
                System.out.println("there is " + server.getCountUsers() + " users in the chat");
            }
        }
    };
}
