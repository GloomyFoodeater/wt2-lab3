package main.java.by.bsuir.remotearchive.server.dao;

import main.java.by.bsuir.remotearchive.server.dao.impl.StudentInfoDAOImpl;

public class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private DAOFactory() {}

    public static DAOFactory getInstance(){
        return instance;
    }

    public StudentInfoDAO getCaseDAO() {
        return StudentInfoDAOImpl.getInstance();
    }

}