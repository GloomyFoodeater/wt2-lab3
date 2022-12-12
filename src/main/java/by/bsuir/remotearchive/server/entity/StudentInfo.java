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
    private String thirdName;
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

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}
