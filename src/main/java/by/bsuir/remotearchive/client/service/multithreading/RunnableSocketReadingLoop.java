package main.java.by.bsuir.remotearchive.client.service.multithreading;

import java.io.*;
import java.net.Socket;

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
            do {
                responseWriter.println(response);
                response = socketReader.readLine();
            } while (response != null);

        } catch (IOException e) {
            // Ignore
        }
    }
}
