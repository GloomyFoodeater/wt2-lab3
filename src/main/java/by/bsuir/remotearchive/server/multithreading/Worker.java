package main.java.by.bsuir.remotearchive.server.multithreading;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Worker extends Thread {
    public static final int PORT = 5556;
    private static final int BACKLOG = 15;
    private final PrintWriter logger;

    public Worker(OutputStream outputStream) {
        logger = new PrintWriter(outputStream, true);
    }

    @Override
    public void run() {
        Socket clientSocket;
        Thread clientWorker;

        // Start listening
        try (var listenSocket = new ServerSocket(PORT, BACKLOG, null)) {
            logger.println("LISTEN on %s:%d".formatted(listenSocket.getInetAddress(), PORT));

            // Handle clients
            while (true) {
                try {
                    // Accept connection
                    clientSocket = listenSocket.accept();

                    // Handle connection in another thread
                    clientWorker = new ClientWorker(clientSocket, logger);
                    clientWorker.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
