package main.java.by.bsuir.remotearchive.client.multithreading;

import java.io.*;
import java.net.Socket;

/**
 * Runnable loop that reads data from socket and prints it to output
 */
public class RunnableSocketReadingLoop implements Runnable {
    private final BufferedReader socketReader;
    private final PrintWriter responseWriter;

    public RunnableSocketReadingLoop(Socket socket, OutputStream outputStream) throws IOException {
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        responseWriter = new PrintWriter(outputStream, true);
    }

    @Override
    public void run() {
        String response = "";

        try {
            while (response != null) {
                response = socketReader.readLine();
                responseWriter.println(response);
            }
        } catch (IOException e) {
            // Ignore
        }
    }
}
