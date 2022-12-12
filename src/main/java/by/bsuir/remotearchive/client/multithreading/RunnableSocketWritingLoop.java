package main.java.by.bsuir.remotearchive.client.multithreading;

import java.io.*;
import java.net.Socket;

public class RunnableSocketWritingLoop implements Runnable {

    private final PrintWriter socketWriter;
    private final BufferedReader requestReader;

    public RunnableSocketWritingLoop(Socket socket, InputStream inputStream) throws IOException {
        socketWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        requestReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void run() {
        String request;
        try {
            do {
                request = requestReader.readLine();
                socketWriter.println(request);
            } while (true);
        } catch (IOException e) {
            // Ignore
        }
    }
}
