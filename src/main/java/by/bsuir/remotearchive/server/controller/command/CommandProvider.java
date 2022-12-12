package main.java.by.bsuir.remotearchive.server.controller.command;

import main.java.by.bsuir.remotearchive.server.controller.command.impl.*;

public class CommandProvider {
    private static final CommandProvider instance = new CommandProvider();

    private CommandProvider() {
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public Command getCommand(String name) {
        return switch (name.toUpperCase()) {
            case "AUTH" -> new Authenticate();
            case "DISCONNECT" -> new Disconnect();
            case "CREATE" -> new Create();
            case "EDIT" -> new Edit();
            case "VIEW" -> new View();
            default -> throw new IllegalArgumentException("Unexpected value: '%s'".formatted(name));
        };
    }
}