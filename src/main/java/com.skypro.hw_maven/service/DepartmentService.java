package com.skypro.hw_maven.service;

import com.skypro.hw_maven.exceptions.InvalidEmployeeRequestExceptions;
import com.skypro.hw_maven.model.Employee;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Collection<Employee> getDepartmentEmployees(int department) {
        return getDepartmentEmployeesStream(department)
                .collect(Collectors.toList());
    }

    public int getSumOfSalariesByDepartment(int department) {
        return getDepartmentEmployeesStream(department)
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public int getMaxSalariesInDepartment(int department){
        return getDepartmentEmployeesStream(department)
                .mapToInt(Employee::getSalary)
                .max()
                .orElseThrow(InvalidEmployeeRequestExceptions::new);
    }

    public int getMinSalariesInDepartment(int department){
        return getDepartmentEmployeesStream(department)
                .mapToInt(Employee::getSalary)
                .min()
                .orElseThrow(InvalidEmployeeRequestExceptions::new);
    }

    public Map<Integer, List<Employee>> getEmployeesGroupByDepartment(){
        return employeeService.getAllEmployees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    private Stream<Employee> getDepartmentEmployeesStream(int department) {
        return employeeService.getAllEmployees().stream()
                .filter(e -> e.getDepartment() == department);
    }
}
