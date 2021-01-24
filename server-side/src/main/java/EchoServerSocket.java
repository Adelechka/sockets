import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class EchoServerSocket {
    ServerSocket server;
    List<Connection> clients = new LinkedList<>();

    public void start(int port) {
        try {
            server = new ServerSocket(port);
            while (true) {
                System.out.println("waiting for client");
                Socket socket = server.accept();
                clients.add(new Connection(socket, this));
                System.out.println("client is found. waiting for another client");
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