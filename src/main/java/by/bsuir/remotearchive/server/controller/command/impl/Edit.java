package main.java.by.bsuir.remotearchive.server.controller.command.impl;

import main.java.by.bsuir.remotearchive.server.controller.command.Command;
import main.java.by.bsuir.remotearchive.server.entity.GuestRole;
import main.java.by.bsuir.remotearchive.server.service.ServiceFactory;

public class Edit implements Command {
    private final int argumentsCount = 6;

    @Override
    public String execute(Object caller, String request) {
        String[] arguments;
        int group;
        int id;

        // Role validation
        if (ServiceFactory.getInstance().getAuthenticateService().getGuestRole(caller) != GuestRole.ADMIN) {
            return "You must be admin to execute this command";
        }

        // Arguments validation
        arguments = request.split(" ");
        if (arguments.length != argumentsCount) {
            return "EDIT command should contain %d arguments".formatted(argumentsCount - 1);
        }
        try {
            id = Integer.parseInt(arguments[1]);
            if (id < 0)
                return "Id must be non-negative integer";
        } catch (NumberFormatException e) {
            return "Id must be non-negative integer";
        }
        try {
            group = Integer.parseInt(arguments[5]);
            if (group <= 0)
                return "Group must be positive integer";
        } catch (NumberFormatException e) {
            return "Group must be positive integer";
        }

        // Request execution
        if (!ServiceFactory.getInstance().getStudentsService().contains(id)) {
            return "No info found to edit";
        }
        ServiceFactory.getInstance().getStudentsService().editInfo(
                id,
                arguments[3],
                arguments[2],
                arguments[4],
                group);
        return "%s was successful!".formatted(request);
    }
}
