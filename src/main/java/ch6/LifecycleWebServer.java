package ch6;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by Artyom Zheltyshev on 30.04.2026
 */
public class LifecycleWebServer {
    public static final int NTHREADS = 100;
    private final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);

    public void start() throws IOException {
        try (ServerSocket socket = new ServerSocket(80)) {
            while (!exec.isShutdown()) {
                try {
                    Socket connection = socket.accept();
                    exec.execute(() -> handleRequest(connection));
                } catch (RejectedExecutionException e) {
                    if (!exec.isShutdown()) {
                        log("task submission rejected", e);
                    }
                }
            }
        }
    }

    public void stop() {
        exec.shutdown();
    }

    private void handleRequest(Socket connection) {
        try {
            Request req = readRequest(connection);
            if (isShutdownRequest(req)) {
                stop();
            } else {
                dispatchRequest(req);
            }
        } catch (IOException e) {
            log("An error occurred while processing request", e);
        }
    }

    private Request readRequest(Socket connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        return new Request(body);
    }

    private boolean isShutdownRequest(Request req) {
        return "shutdown".equals(req.getBody());
    }

    private void dispatchRequest(Request req) {

    }

    private void log(String message, Throwable throwable) {
        System.out.println(message);
        throwable.printStackTrace();
    }

    private class Request {
        private final String body;

        public Request(String body) {
            this.body = body;
        }

        public String getBody() {
            return body;
        }
    }
}
