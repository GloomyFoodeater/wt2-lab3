package main.java.by.bsuir.remotearchive.server.multithreading;

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
        Controller controller;
        String request;
        String response;

        // Send help message
        response = "Available commands: " +
                "AUTH GUEST|USER|ADMIN, " +
                "DISCONNECT, " +
                "VIEW, " +
                "CREATE <lastName> <firstName> <patronymic> <group>, " +
                "EDIT <id> <firstName> <lastName>";
        socketWriter.println(response);
        logger.println("To %s:%d: %s".formatted(socket.getInetAddress(), socket.getPort(), response));

        controller = Controller.getInstance();
        try {
            while (!socket.isClosed()) {

                // Request
                request = socketReader.readLine();
                logger.println("From %s:%d:\n %s".formatted(socket.getInetAddress(), socket.getPort(), request));

                // Response
                try {
                    response = controller.executeTask(this, request);
                } catch (IllegalArgumentException e) {
                    response = e.getMessage();
                }
                // Disconnect request
                if (!socket.isClosed())
                    socketWriter.println(response);
                logger.println("To %s:%d:\n %s".formatted(socket.getInetAddress(), socket.getPort(), response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
