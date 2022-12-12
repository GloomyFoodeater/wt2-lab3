package main.java.by.bsuir.remotearchive.server.service;

import main.java.by.bsuir.remotearchive.server.entity.GuestRole;

public interface AuthenticateService {
    GuestRole getGuestRole(Object user);
    void setGuestRole(Object user, GuestRole role);
}
