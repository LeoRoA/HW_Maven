package com.skypro.hw_maven;

import com.skypro.hw_maven.exceptions.InvalidEmployeeRequestExceptions;
import com.skypro.hw_maven.model.Employee;
import com.skypro.hw_maven.service.DepartmentService;
import com.skypro.hw_maven.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    private final List<Employee> employees = List.of(
            new Employee("firstF", "firstL", 1, 4000),
            new Employee("secondF", "secondL", 2, 5000),
            new Employee("thirdF", "thirdL", 3, 6000),
            new Employee("forthF", "forthL", 1, 7000),
            new Employee("fifthF", "fifthL", 2, 8000));
    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        Mockito.when(employeeService.getAllEmployees())
                .thenReturn(employees);
    }

    @Test
    void getEmployeesByDepartment() {
        Collection<Employee> employeesByDep = this.departmentService.getDepartmentEmployees(1);
        Assertions.assertThat(employeesByDep)
                .hasSize(2)
                .contains(
                        employees.get(0),
                        employees.get(3));
    }

    @Test
    void sumOfSalariesByDepartment() {
        int sum = departmentService.getSumOfSalariesByDepartment(2);
        Assertions.assertThat(sum)
                .isEqualTo(13000);
    }

    @Test
    void maxSalaryInDepartment() {
        int maxSalary = departmentService.getMaxSalariesInDepartment(1);
        Assertions.assertThat(maxSalary)
                .isEqualTo(7000);
    }

    @Test
    void minSalaryInDepartment() {
        int minSalary = departmentService.getMinSalariesInDepartment(2);
        Assertions.assertThat(minSalary)
                .isEqualTo(5000);
    }

    @Test
    void groupedEmployees() {
        Map<Integer, List<Employee>> groupedEmployees = departmentService
                .getEmployeesGroupByDepartment();
        Assertions.assertThat(groupedEmployees)
                .hasSize(3)
                .containsEntry(1, List.of(employees.get(0), employees.get(3)))
                .containsEntry(2, List.of(employees.get(1), employees.get(4)))
                .containsEntry(3, List.of(employees.get(2)));
    }

    @Test
    void WhenNoEmployeesThenGroupByDepartmentReturnEmptyMap() {
        Mockito.when(employeeService.getAllEmployees())
                .thenReturn(List.of());
        Map<Integer, List<Employee>> groupedEmployees = departmentService
                .getEmployeesGroupByDepartment();
        Assertions.assertThat(groupedEmployees)
                .isEmpty();
    }

    @Test
    void WhenNoEmployeesThenMaxSalariesInDepartmentThrowsException() {
        Mockito.when(employeeService.getAllEmployees())
                .thenReturn(List.of());
        Assertions.assertThatThrownBy(() -> departmentService
                        .getMaxSalariesInDepartment(0))
                .isInstanceOf(InvalidEmployeeRequestExceptions.class);
    }
}
