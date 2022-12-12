package main.java.by.bsuir.remotearchive.server.dao;

import main.java.by.bsuir.remotearchive.server.entity.StudentInfo;

import java.util.List;

public interface StudentInfoDAO {
    boolean contains(int id);

    List<StudentInfo> getAll();

    void add(StudentInfo studentInfo);

    void setById(int id, StudentInfo studentInfo);

    void update();
}