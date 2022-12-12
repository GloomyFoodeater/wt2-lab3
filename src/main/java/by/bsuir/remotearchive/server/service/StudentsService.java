package main.java.by.bsuir.remotearchive.server.service;

import main.java.by.bsuir.remotearchive.server.entity.StudentInfo;

import java.util.List;

public interface StudentsService {
    void appendInfo(String firstName, String lastName, String patronymic, int group);

    void editInfo(int id, String firstName, String lastName, String patronymic, int group);

    List<StudentInfo> getAll();

    boolean contains(int id);
}
