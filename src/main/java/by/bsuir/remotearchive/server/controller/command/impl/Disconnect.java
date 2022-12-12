package main.java.by.bsuir.remotearchive.server.controller.command.impl;

import main.java.by.bsuir.remotearchive.server.controller.command.Command;
import main.java.by.bsuir.remotearchive.server.entity.GuestRole;
import main.java.by.bsuir.remotearchive.server.service.ServiceFactory;
import main.java.by.bsuir.remotearchive.server.multithreading.ClientWorker;

public class Disconnect implements Command {
    @Override
    public String execute(Object caller, String request) {
        ServiceFactory.getInstance().getAuthenticateService().setGuestRole(caller, GuestRole.GUEST);
        ((ClientWorker)caller).disconnect();
        return "Disconnect";
    }
}
