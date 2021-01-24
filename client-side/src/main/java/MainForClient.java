import com.beust.jcommander.JCommander;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MainForClient {
    public static void main(String[] args) {
        try {
            Args argv = new Args();
            JCommander.newBuilder().addObject(argv).build().parse(args);
            Socket socket = new Socket(argv.host, argv.port);
            SocketClient client = new SocketClient(socket);

            Scanner scanner = new Scanner(System.in);
            System.out.println("enter your name...");
            String name = scanner.nextLine();
            System.out.println("welcome to the chat, " + name + "!");
            client.sendMessage(name + " joined the chat!");
            while (true) {
                System.out.println("enter your message:");
                String message = scanner.nextLine();
                client.sendMessage(name + ": '" + message + "'");
                System.out.println(name + ", your message is sent with text: '" + message + "'");
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
