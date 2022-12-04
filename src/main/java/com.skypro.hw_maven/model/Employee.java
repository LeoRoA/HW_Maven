package com.skypro.hw_maven.model;

import java.util.Objects;

public class Employee {
    private static int counter;
    private final int id;

    private final String firstName;
    private final String lastName;
    private final int department;
    private final int salary;

    public Employee(String firstName, String lastName, int department, int salary) {
        this.id = counter++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDepartment() {
        return department;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;
        return getId() == employee.getId() && getDepartment() == employee.getDepartment() && getSalary() == employee.getSalary() && getFirstName().equals(employee.getFirstName()) && getLastName().equals(employee.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getDepartment(), getSalary());
    }

    @Override
    public String toString() {
        return "id: " + id +
                firstName + ' ' +
                lastName +
                ", отдел" + department +
                ", ЗП = " + salary;
    }
}
