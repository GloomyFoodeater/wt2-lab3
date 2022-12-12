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

        arguments = request.split(" ");
        if (arguments.length != argumentsCount) {
            throw new IllegalArgumentException(String.format("AUTH command should contain %d argument(s)", argumentsCount));
        }
        try {
            role = GuestRole.valueOf(arguments[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Role was invalid");
        }
        ServiceFactory.getInstance().getAuthenticateService().setGuestRole(caller, role);

        return "Authorized as %s".formatted(arguments[1].toUpperCase());
    }
}
