package com.ua.tpm.libraryproject.dbconn.requests.staff;

public class Staff {

    private String firstName;
    private String secondName;
    private String position;
    private float salary;

    public Staff(String firstName, String secondName) {

        this.setFirstName(firstName);
        this.setSecondName(secondName);
        this.setPosition("");
        this.setSalary(0);
    }

    public Staff() {
        this("","");
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public float getSalary() {
        return salary;
    }
    public void setSalary(float salary) {
        this.salary = salary;
    }
}
