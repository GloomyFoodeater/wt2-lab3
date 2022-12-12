package main.java.by.bsuir.remotearchive.client.multithreading;

import java.io.*;
import java.net.Socket;

/**
 * Runnable loop that reads data from given stream and prints it to socket
 */
public class RunnableSocketWritingLoop implements Runnable {

    private final PrintWriter socketWriter;
    private final Socket socket;
    private final BufferedReader requestReader;

    public RunnableSocketWritingLoop(Socket socket, InputStream inputStream) throws IOException {
        this.socket = socket;
        socketWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        requestReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void run() {
        String request;
        try {
            while (!socket.isClosed()) {
                request = requestReader.readLine();
                socketWriter.println(request);
            }
        } catch (IOException e) {
            // Ignore
        }
    }
}