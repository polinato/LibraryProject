package com.ua.tpm.libraryproject.dbconn.tables.positions;

public class Position {

    private String name;
    private float salary;
    private String responsibilities;
    private String requirements;

    public Position(String name) {

        this.setName(name);
        this.setSalary(15000);
        this.setResponsibilities("Обов'язки");
        this.setRequirements("Вимоги");
    }

    public Position() {
        this("Посада");
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public float getSalary() {
        return salary;
    }
    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getResponsibilities() {
        return responsibilities;
    }
    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getRequirements() {
        return requirements;
    }
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
}
