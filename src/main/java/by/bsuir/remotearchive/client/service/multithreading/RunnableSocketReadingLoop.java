package main.java.by.bsuir.remotearchive.client.service.multithreading;

import java.io.*;
import java.net.Socket;

public class RunnableSocketReadingLoop implements Runnable {
    private final BufferedReader socketReader;
    private final Socket socket;
    private final PrintWriter responseWriter;

    public RunnableSocketReadingLoop(Socket socket, OutputStream outputStream) throws IOException {
        this.socket = socket;
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        responseWriter = new PrintWriter(outputStream);
    }

    @Override
    public void run() {
        String response;

        try {
            while (!socket.isInputShutdown()) {
                response = socketReader.readLine();
                if (response != null)
                    responseWriter.println(response);
            }
        } catch (IOException e) {
            // Ignore
        }
    }
}
