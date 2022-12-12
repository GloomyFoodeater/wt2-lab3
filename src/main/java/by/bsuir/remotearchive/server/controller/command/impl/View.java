package main.java.by.bsuir.remotearchive.server.controller.command.impl;

import main.java.by.bsuir.remotearchive.server.controller.command.Command;
import main.java.by.bsuir.remotearchive.server.entity.GuestRole;
import main.java.by.bsuir.remotearchive.server.entity.StudentInfo;
import main.java.by.bsuir.remotearchive.server.service.ServiceFactory;

import java.util.List;

public class View implements Command {

    private static final int argumentsCount = 1;

    @Override
    public String execute(Object caller, String request) {
        String[] arguments;

        // Role validation
        if (ServiceFactory.getInstance().getAuthenticateService().getGuestRole(caller) == GuestRole.GUEST) {
            return "You must be authorized to view students";
        }

        // Arguments validation
        arguments = request.split(" ");
        if (arguments.length != argumentsCount) {
            return "VIEW command should not contain arguments";
        }

        // Request execution
        List<StudentInfo> studentInfos = ServiceFactory.getInstance().getStudentsService().getAll();
        StringBuilder result = new StringBuilder("%-4s | %-15s | %-15s | %-15s | %-6s\n".formatted(
                "id",
                "last name",
                "first name",
                "patronymic",
                "group"));
        for (var studentInfo : studentInfos) {
            result.append("%-4d | %-15s | %-15s | %-15s | %06d\n".formatted(
                    studentInfo.getId(),
                    studentInfo.getLastName(),
                    studentInfo.getFirstName(),
                    studentInfo.getPatronymic(),
                    studentInfo.getGroup()));
        }

        return result.toString();
    }
}
