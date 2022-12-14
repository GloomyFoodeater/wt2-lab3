package main.java.by.bsuir.remotearchive.client.multithreading;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Worker extends Thread {
    private final InetAddress serverIp;
    private final int serverPort;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public Worker(InetAddress serverIp,
                  int serverPort,
                  InputStream inputStream,
                  OutputStream outputStream) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        Socket socket;
        Runnable socketWritingLoop;
        Runnable socketReadingLoop;
        Thread socketWritingThread;

        try {
            // Connect to server
            socket = new Socket(serverIp, serverPort);

            // Create runnable tasks
            socketWritingLoop = new RunnableSocketWritingLoop(socket, inputStream);
            socketReadingLoop = new RunnableSocketReadingLoop(socket, outputStream);

            // Start tasks in separate threads
            socketWritingThread = new Thread(socketWritingLoop);
            socketWritingThread.start();
            socketReadingLoop.run();

            // Shutdown to stop writing and reading
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
