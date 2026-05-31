package ch6;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Artyom Zheltyshev on 26.04.2026
 */
public class SingleThreadWebServer {

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            handleRequest(connection);
        }
    }

    private static void handleRequest(Socket connection) {
        // some logic of request processing
    }

}
