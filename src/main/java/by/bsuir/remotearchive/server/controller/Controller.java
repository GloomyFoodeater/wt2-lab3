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

        // Prepare to get command
        delimiterPos = request.indexOf(' ');
        if (delimiterPos == -1) delimiterPos = request.length();
        commandName = request.substring(0, delimiterPos);
        commandProvider = CommandProvider.getInstance();

        // Get and execute command
        executionCommand = commandProvider.getCommand(commandName);
        response = executionCommand.execute(caller, request);

        return response;
    }
}
