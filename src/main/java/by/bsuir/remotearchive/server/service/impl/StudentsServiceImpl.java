package main.java.by.bsuir.remotearchive.server.service.impl;

import main.java.by.bsuir.remotearchive.server.dao.DAOFactory;
import main.java.by.bsuir.remotearchive.server.entity.StudentInfo;
import main.java.by.bsuir.remotearchive.server.service.StudentsService;

public class StudentsServiceImpl implements StudentsService {

    private static final StudentsServiceImpl instance = new StudentsServiceImpl();

    private StudentsServiceImpl() {
    }

    public static StudentsServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void appendInfo(String firstName, String lastName, String patronymic, int group) {
        DAOFactory.getInstance().getCaseDAO().add(new StudentInfo(0, firstName, lastName, patronymic, group));
    }
}
