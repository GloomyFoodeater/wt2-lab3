package main.java.by.bsuir.remotearchive.server.controller.command.impl;

import main.java.by.bsuir.remotearchive.server.controller.command.Command;
import main.java.by.bsuir.remotearchive.server.entity.GuestRole;
import main.java.by.bsuir.remotearchive.server.service.ServiceFactory;

public class Create implements Command {
    private final int argumentsCount = 5;

    @Override
    public String execute(Object caller, String request) {
        String[] arguments;
        int group;

        // Role validation
        if (ServiceFactory.getInstance().getAuthenticateService().getGuestRole(caller) != GuestRole.ADMIN) {
            return "You must be admin to execute this";
        }

        // Arguments validation
        arguments = request.split(" ");
        if (arguments.length != argumentsCount) {
            return "CREATE command should contain %d arguments".formatted(argumentsCount - 1);
        }
        try {
            group = Integer.parseInt(arguments[4]);
            if (group <= 0)
                return "Group must be positive integer";
        } catch (NumberFormatException e) {
            return "Group must be positive integer";
        }

        ServiceFactory.getInstance().getStudentsService().appendInfo(
                arguments[2],
                arguments[1],
                arguments[3],
                group);
        return request + " was successful";
    }
}
