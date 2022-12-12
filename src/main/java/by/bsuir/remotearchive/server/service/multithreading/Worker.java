package main.java.by.bsuir.remotearchive.server.service.multithreading;

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
            logger.println("Start listening on port %d".formatted(PORT));

            // Handle clients
            while (true) {
                try {
                    // Accept connection
                    clientSocket = listenSocket.accept();
                    logger.println("New connection: %s:%d".formatted(
                            clientSocket.getInetAddress(),
                            clientSocket.getPort()));

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
