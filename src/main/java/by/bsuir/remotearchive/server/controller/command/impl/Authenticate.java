package main.java.by.bsuir.remotearchive.server.controller.command.impl;

import main.java.by.bsuir.remotearchive.server.controller.command.Command;
import main.java.by.bsuir.remotearchive.server.entity.GuestRole;
import main.java.by.bsuir.remotearchive.server.service.ServiceFactory;

public class Authenticate implements Command {

    private final int argumentsCount = 2;

    @Override
    public String execute(Object caller, String request) {
        String[] arguments;
        GuestRole role;

        // Arguments validation
        arguments = request.split(" ");
        if (arguments.length != argumentsCount) {
            return "AUTH command should contain %d arguments".formatted(argumentsCount - 1);
        }
        try {
            role = GuestRole.valueOf(arguments[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Role was invalid";
        }

        // Request execution
        ServiceFactory.getInstance().getAuthenticateService().setGuestRole(caller, role);

        return "Authorized as %s".formatted(arguments[1].toUpperCase());
    }
}
