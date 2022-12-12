package main.java.by.bsuir.remotearchive.server.controller;

import main.java.by.bsuir.remotearchive.server.controller.command.Command;
import main.java.by.bsuir.remotearchive.server.controller.command.CommandProvider;

public class Controller {
    private static final Controller instance = new Controller();

    private Controller() {
    }


    public static Controller getInstance() {
        return instance;
    }

    public String executeTask(Object caller, String request) {
        String commandName;
        Command executionCommand;
        String response;
        CommandProvider commandProvider;
        int delimiterPos;

        // Get command by request
        delimiterPos = request.indexOf(' ');
        if (delimiterPos == -1) delimiterPos = request.length();
        commandName = request.substring(0, delimiterPos);
        commandProvider = CommandProvider.getInstance();
        executionCommand = commandProvider.getCommand(commandName);

        // Execute command and return response
        response = executionCommand.execute(caller, request);
        return response;
    }
}
