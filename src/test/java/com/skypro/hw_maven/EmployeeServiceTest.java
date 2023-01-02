package com.skypro.hw_maven;

import com.skypro.hw_maven.exceptions.InvalidEmployeeRequestExceptions;
import com.skypro.hw_maven.model.Employee;
import com.skypro.hw_maven.record.EmployeeRequest;
import com.skypro.hw_maven.service.EmployeeService;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeServiceTest {
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() {
        this.employeeService = new EmployeeService();
        Stream.of(
                        new EmployeeRequest("firstF", "firstL", 1, 4000),
                        new EmployeeRequest("secondF", "secondL", 2, 5000),
                        new EmployeeRequest("thirdF", "thirdL", 3, 6000),
                        new EmployeeRequest("forthF", "forthL", 1, 7000),
                        new EmployeeRequest("fifthF", "fifthL", 2, 8000)
                )
                .forEach(employeeService::addEmployee);
    }

    @Test
    public void AddEmployee() {
        EmployeeRequest request = new EmployeeRequest(
                "One", "One", 1, 1500);
        Employee result = employeeService.addEmployee(request);
        assertEquals(request.getFirstName(), result.getFirstName());
        assertEquals(request.getLastName(), result.getLastName());
        assertEquals(request.getDepartment(), result.getDepartment());
        assertEquals(request.getSalary(), result.getSalary());
        Assertions.assertThat(employeeService.getAllEmployees())
                .contains(result);
    }
    @Test
    public void addEmployeeException() {
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEmployeeRequestExceptions.class,
                ()-> employeeService
                        .addEmployee(new EmployeeRequest("f1",
                                "fl",
                                1,
                                1000)));
        org.junit.jupiter.api.Assertions.assertThrows(InvalidEmployeeRequestExceptions.class,
                ()-> employeeService
                        .addEmployee(new EmployeeRequest(null,
                                "fl",
                                1,
                                1000)));
    }

    @Test
    public void capitalizationAddedEmployee(){
        Employee emp1 = employeeService
                .addEmployee(new EmployeeRequest("firstF",
                        "Fl",
                        1,
                        1000));
        Assertions.assertThat(emp1.getFirstName())
                .isEqualTo("FirstF");
    }

    @Test
    public void listEmployee() {
        Collection<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertThat(employees).hasSize(5);
        Assertions.assertThat(employees)
                .first()
                .extracting(Employee::getFirstName,
                        Employee::getLastName,
                        Employee::getDepartment,
                        Employee::getSalary)
                .containsExactly("FirstF", "FirstL", 1, 4000);
        Assertions.assertThat(employees)
                .last()
                .extracting(Employee::getFirstName,
                        Employee::getLastName,
                        Employee::getDepartment,
                        Employee::getSalary)
                .containsExactly("FifthF", "FifthL", 2, 8000);
    }

    @Test
    public void sumOfSalaries() {
        int sum = employeeService.getSalarySum();
        Assertions.assertThat(sum)
                .isEqualTo(30000);
    }

    @Test
    public void employeeWithMaxSalary() {
        Employee employee = employeeService.getIdMaxSalary();
        Assertions.assertThat(employee)
                .isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("FifthF");
    }

    @Test
    public void employeeWithMinSalary() {
        Employee employee = employeeService.getIdMinSalary();
        Assertions.assertThat(employee)
                .isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("FirstF");
    }

    @Test
    public void HighlyPaidListEmployee() {
        Collection<Employee> employees = employeeService.getHighlyPaidEmployee().values();
        Assertions.assertThat(employees).hasSize(2);
        Assertions.assertThat(employees)
                .first()
                .extracting(Employee::getFirstName,
                        Employee::getLastName,
                        Employee::getSalary)
                .containsExactly("ForthF", "ForthL", 7000);
        Assertions.assertThat(employees)
                .last()
                .extracting(Employee::getFirstName,
                        Employee::getLastName,
                        Employee::getSalary)
                .containsExactly("FifthF", "FifthL", 8000);
    }

    @Test
    public void removeEmployee(){
        Collection<Employee> employees = employeeService.getAllEmployees();
        employeeService.removeEmployee(employeeService.getAllEmployees().iterator().next().getId());
        Assertions.assertThat(employees).hasSize(4);
        Assertions.assertThat(employees)
                .first()
                .extracting(Employee::getFirstName,
                        Employee::getLastName)
                .containsExactly("SecondF","SecondL");

    }
}
