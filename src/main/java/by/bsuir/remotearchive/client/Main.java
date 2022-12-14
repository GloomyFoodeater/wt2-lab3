package main.java.by.bsuir.remotearchive.client;

import main.java.by.bsuir.remotearchive.client.multithreading.Worker;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static main.java.by.bsuir.remotearchive.server.multithreading.Worker.PORT;

public class Main {
    public static void main(String[] args) {
        try {
            var localHost = InetAddress.getLocalHost();
            var worker = new Worker(localHost, PORT, System.in, System.out);
            worker.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
