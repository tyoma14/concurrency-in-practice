package ch6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Artyom Zheltyshev on 26.04.2026
 */
public class ThreadPerTaskWebServer {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            Runnable task = () -> handleRequest(connection);
            Thread thread = new Thread(task);
            thread.start();
        }
    }

    private static void handleRequest(Socket connection) {
        // some logic of request processing
    }

}
