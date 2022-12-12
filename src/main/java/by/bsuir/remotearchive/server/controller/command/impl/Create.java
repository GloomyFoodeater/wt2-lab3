package main.java.by.bsuir.remotearchive.server.controller.command.impl;

import main.java.by.bsuir.remotearchive.server.controller.command.Command;
import main.java.by.bsuir.remotearchive.server.entity.GuestRole;
import main.java.by.bsuir.remotearchive.server.service.ServiceFactory;

public class Create implements Command {
    private final int argumentsCount = 5;

    @Override
    public String execute(Object caller, String request) {
        var arguments = request.split(" ");
        if (arguments.length != argumentsCount) {
            throw new IllegalArgumentException(String.format("CREATE command should contain %d argument(s)", argumentsCount));
        }

        if (ServiceFactory.getInstance().getAuthenticateService().getGuestRole(caller) != GuestRole.ADMIN) {
            return "You must be admin to execute this";
        }
        try {
            ServiceFactory.getInstance().getStudentsService().appendInfo(
                    arguments[1],
                    arguments[2],
                    arguments[3],
                    Integer.parseInt(arguments[4]));
        } catch (Exception e) {
            throw new IllegalArgumentException("CREATE command failed with given arguments");
        }
        return request + " was successful";
    }
}
