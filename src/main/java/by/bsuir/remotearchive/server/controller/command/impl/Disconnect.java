package main.java.by.bsuir.remotearchive.server.controller.command.impl;

import main.java.by.bsuir.remotearchive.server.controller.command.Command;
import main.java.by.bsuir.remotearchive.server.entity.GuestRole;
import main.java.by.bsuir.remotearchive.server.multithreading.ClientWorker;
import main.java.by.bsuir.remotearchive.server.service.ServiceFactory;

public class Disconnect implements Command {
    private final int argumentsCount = 1;

    @Override
    public String execute(Object caller, String request) {
        String[] arguments;

        // Argument validation
        arguments = request.split(" ");
        if (arguments.length != argumentsCount) {
            throw new IllegalArgumentException("DISCONNECT command should not contain arguments");
        }

        // Request execution
        ServiceFactory.getInstance().getAuthenticateService().setGuestRole(caller, GuestRole.GUEST);
        ((ClientWorker) caller).disconnect();

        return "Disconnect";
    }
}
