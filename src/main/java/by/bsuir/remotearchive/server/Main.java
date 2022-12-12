package main.java.by.bsuir.remotearchive.server;

import main.java.by.bsuir.remotearchive.server.service.multithreading.Worker;

public class Main {
    public static void main(String[] args) {
        var worker = new Worker(System.out);
        worker.start();
    }
}
