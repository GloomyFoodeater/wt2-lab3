package main.java.by.bsuir.remotearchive.server.service.impl;

import main.java.by.bsuir.remotearchive.server.entity.GuestRole;
import main.java.by.bsuir.remotearchive.server.service.AuthenticateService;

import java.util.HashMap;
import java.util.Map;

public class AuthenticateServiceImpl implements AuthenticateService {
    private static final AuthenticateServiceImpl instance = new AuthenticateServiceImpl();

    private final Map<Object, GuestRole> users = new HashMap<>();

    private AuthenticateServiceImpl() {
    }

    public static AuthenticateService getInstance() {
        return instance;
    }

    @Override
    public GuestRole getGuestRole(Object user) {
        GuestRole result;
        if (!users.containsKey(user)) {
            users.put(user, GuestRole.GUEST);
            result = GuestRole.GUEST;
        } else {
            result = users.get(user);
        }
        return result;
    }

    @Override
    public void setGuestRole(Object user, GuestRole role) {
        users.put(user, role);
    }
}
