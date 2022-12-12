package main.java.by.bsuir.remotearchive.server.entity;

public class StudentInfo {
    private static int counter = 0;

    public StudentInfo() {
        id = counter;
        counter++;
    }

    private final int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private int group;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
