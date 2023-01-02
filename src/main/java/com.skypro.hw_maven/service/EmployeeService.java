package com.skypro.hw_maven.service;

import com.skypro.hw_maven.exceptions.InvalidEmployeeRequestExceptions;
import com.skypro.hw_maven.model.Employee;
import com.skypro.hw_maven.record.EmployeeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmployeeService {
    private final HashMap<Integer, Employee> employees = new HashMap<>();

    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }

    public Employee addEmployee(EmployeeRequest employeeRequest) {
        if (!StringUtils.isAlpha(employeeRequest.getFirstName()) ||
                !StringUtils.isAlpha(employeeRequest.getLastName())) {
            throw new InvalidEmployeeRequestExceptions();
        }
        Employee employee = new Employee(
                StringUtils.capitalize(employeeRequest.getFirstName()),
                StringUtils.capitalize(employeeRequest.getLastName()),
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
        return employees.values().stream()
                .min((Comparator.comparingInt(Employee::getSalary)))
                .orElseThrow(InvalidEmployeeRequestExceptions::new);
    }


    public Employee getIdMaxSalary() {
        return employees.values().stream()
                .max(Comparator.comparingInt(Employee::getSalary))
                .orElseThrow(InvalidEmployeeRequestExceptions::new);
    }

    public Map<Integer, Employee> getHighlyPaidEmployee() {
        double avg = employees.values().stream()
                .mapToInt(Employee::getSalary)
                .average()
                .getAsDouble();
        Map<Integer, Employee> employeeHighlyPaid = new HashMap<>();
        for (Employee e : employees.values()) {
            if (e.getSalary() > avg) {
                employeeHighlyPaid.put(e.getId(), e);
            }
        }
        return employeeHighlyPaid;
    }

}

