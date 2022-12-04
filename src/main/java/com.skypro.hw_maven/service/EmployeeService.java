package com.skypro.hw_maven.service;

import com.skypro.hw_maven.model.Employee;
import com.skypro.hw_maven.record.EmployeeRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {
    private final HashMap<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (employeeRequest.getFirstName() == null || employeeRequest.getLastName() == null) {
            throw new IllegalArgumentException("Должны быть заполнены имя и фамилия");
        }
        Employee employee = new Employee(employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getDepartment(),
                employeeRequest.getSalary());

        this.employees.put(employee.getId(), employee);
        return employee;
    }

    public int getSalarySum() {
        return employees.values().stream()
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public Employee getIdMinSalary() {
        int min = employees.values().stream()
                .mapToInt(Employee::getSalary)
                .min()
                .getAsInt();
        int id = -1;
        for (Employee e : employees.values()) {
            if (e.getSalary() == min) {
                id = e.getId();
            }
        }
        return employees.get(id);
    }


    public Employee getIdMaxSalary() {
        int max = employees.values().stream()
                .mapToInt(Employee::getSalary)
                .max()
                .getAsInt();
        int id = -1;
        for (Employee e : employees.values()) {
            if (e.getSalary() == max) {
                id = e.getId();
            }
        }
        return employees.get(id);
    }
    public Map<Integer, Employee> getHighlyPaidEmployee() {
        double avg = employees.values().stream()
                .mapToInt(Employee::getSalary)
                .average()
                .getAsDouble();
        Map<Integer, Employee> employeeHighlyPaid = new HashMap<>();
        for (Employee e : employees.values()) {
            if (e.getSalary() > avg) {
                employeeHighlyPaid.put(e.getId(),e);

            }
        }
        return employeeHighlyPaid;
    }

}

