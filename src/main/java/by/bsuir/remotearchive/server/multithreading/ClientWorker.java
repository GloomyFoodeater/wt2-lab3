package main.java.by.bsuir.remotearchive.server.multithreading;

import main.java.by.bsuir.remotearchive.server.controller.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientWorker extends Thread {

    private final Socket socket;
    private final PrintWriter logger;
    private final PrintWriter socketWriter;
    private final BufferedReader socketReader;

    private final String addr;

    private final int port;

    public ClientWorker(Socket socket, PrintWriter logger) throws IOException {
        this.socket = socket;
        addr = socket.getInetAddress().toString();
        port = socket.getPort();
        this.logger = logger;
        this.socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socketWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        Controller controller;
        String request;
        String response;
        boolean forcefullyDisconnect = false;

        logger.println("CONNECT to %s:%d".formatted(addr, port));

        // Send help message
        socketWriter.println("""
                Available commands:\s
                \tAUTH GUEST|USER|ADMIN
                \tDISCONNECT
                \tVIEW
                \tCREATE <lastName> <firstName> <patronymic> <group>
                \tEDIT <id> <firstName> <lastName>
                """);
        logger.println("HELP to %s:%d".formatted(addr, port));

        controller = Controller.getInstance();
        try {
            while (!socket.isClosed()) {

                // Request
                request = socketReader.readLine();
                logger.println("RECEIVE from %s:%d:\n %s".formatted(addr, port, request));

                // Response
                try {
                    response = controller.executeTask(this, request);

                    //If for disconnect request
                    if (!socket.isClosed()) {
                        socketWriter.println(response);
                        logger.println("SEND to %s:%d:\n %s".formatted(addr, port, response));
                    }
                } catch (IllegalArgumentException e) {
                    logger.println("INVALID from %s:%d:\n %s".formatted(addr, port, e.getMessage()));
                }
            }
        } catch (SocketException e) {
            forcefullyDisconnect = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.println("DISCONNECT%s from %s:%d".formatted(forcefullyDisconnect ? " forcefully" : "", addr, port));
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
