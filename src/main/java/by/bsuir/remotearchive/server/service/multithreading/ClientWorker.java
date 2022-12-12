package main.java.by.bsuir.remotearchive.server.service.multithreading;

import main.java.by.bsuir.remotearchive.server.controller.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWorker extends Thread {
    private final Socket socket;
    private final PrintWriter logger;
    private final PrintWriter socketWriter;
    private final BufferedReader socketReader;

    public ClientWorker(Socket socket, PrintWriter logger) throws IOException {
        this.socket = socket;
        this.logger = logger;
        this.socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socketWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        String helpMessage;
        Controller controller;
        String request;
        String response;

        // Send help message
        helpMessage = "Available commands: " +
                "AUTH GUEST|USER|ADMIN, " +
                "DISCONNECT, " +
                "VIEW, " +
                "CREATE <firstName> <lastName>, " +
                "EDIT <id> <firstName> <lastName>";
        socketWriter.println(helpMessage);
        logger.println("Send help message to %s:%d".formatted(socket.getInetAddress(), socket.getPort()));

        controller = Controller.getInstance();
        try {
            while (true) {
                request = socketReader.readLine();
                try {
                    response = controller.executeTask(this, request);
                    socketWriter.println(response);
                } catch (IllegalArgumentException e) {
                    socketWriter.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
