package main.java.by.bsuir.remotearchive.server.controller.command.impl;

import main.java.by.bsuir.remotearchive.server.controller.command.Command;
import main.java.by.bsuir.remotearchive.server.entity.GuestRole;
import main.java.by.bsuir.remotearchive.server.service.ServiceFactory;

public class Edit implements Command {
    private final int argumentsCount = 6;

    @Override
    public String execute(Object caller, String request) {
        var arguments = request.split(" ");
        if (arguments.length != argumentsCount) {
            throw new IllegalArgumentException(String.format("EDIT command should contain %d argument(s)", argumentsCount));
        }

        if (ServiceFactory.getInstance().getAuthenticateService().getGuestRole(caller) != GuestRole.ADMIN) {
            return "You must be admin to execute this command";
        }

        int id;
        try {
            id = Integer.parseInt(arguments[1]);
        } catch (NumberFormatException e) {
            return "Invalid id";
        }

        if (!ServiceFactory.getInstance().getStudentsService().contains(id)) {
            return "No info found to edit";
        }

        ServiceFactory.getInstance().getStudentsService().editInfo(
                id,
                arguments[2],
                arguments[3],
                arguments[4],
                Integer.parseInt(arguments[5]));
        return "%s was successful!".formatted(request);
    }
}
