import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class EchoServerSocket {
    private ServerSocket server;
    private List<Connection> clients = new LinkedList<>();
    private int countUsers = 0;

    public int getCountUsers() {
        return countUsers;
    }

    public void setCountUsers(int countUsers) {
        this.countUsers = countUsers;
    }

    public void start(int port) {
        try {
            server = new ServerSocket(port);
            while (true) {
                System.out.println("there is " + countUsers + " users in the chat");
                Socket socket = server.accept();
                clients.add(new Connection(socket, this));
                countUsers++;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void sendAll(String message) {
        for (Connection client : clients) {
            client.sendMessage(message);
        }
    }
}