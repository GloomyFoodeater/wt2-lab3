package main.java.by.bsuir.remotearchive.client.service.multithreading;

import java.io.*;
import java.net.Socket;

public class RunnableSocketWritingLoop implements Runnable {

    private final PrintWriter socketWriter;
    private final BufferedReader requestReader;

    public RunnableSocketWritingLoop(Socket socket, InputStream inputStream) throws IOException {
        socketWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        requestReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void run() {
        String request;

        do {
            try {
                request = requestReader.readLine();
                socketWriter.println(request);
                socketWriter.flush();
            } catch (IOException e) {

                // Forcefully quit
                request = "quit";
            }
        } while (!request.equals("quit"));
    }
}
