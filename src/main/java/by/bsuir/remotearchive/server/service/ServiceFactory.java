package main.java.by.bsuir.remotearchive.server.service;

import main.java.by.bsuir.remotearchive.server.service.impl.AuthenticateServiceImpl;
import main.java.by.bsuir.remotearchive.server.service.impl.StudentsServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public AuthenticateService getAuthenticateService() {
        return AuthenticateServiceImpl.getInstance();
    }

    public StudentsService getStudentsService(){
        return StudentsServiceImpl.getInstance();
    }
}
