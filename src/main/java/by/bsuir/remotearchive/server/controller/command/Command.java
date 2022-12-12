package main.java.by.bsuir.remotearchive.server.controller.command;

public interface Command {
    String execute(Object caller, String request);
}
