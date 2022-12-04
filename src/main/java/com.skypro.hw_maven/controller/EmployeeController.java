package com.example.hw1_maven.controller;

import com.skypro.hw_maven.model.Employee;
import com.skypro.hw_maven.record.EmployeeRequest;
import com.skypro.hw_maven.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public Collection<Employee> getAllEmployees(){
        return this.employeeService.getAllEmployees();
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest){
        return this.employeeService.addEmployee(employeeRequest);
    }
    @GetMapping("/employees/salary/sum")
    public int getSalarySum(){
        return  this.employeeService.getSalarySum();
    }
    @GetMapping("/employees/salary/min")
    public Employee getMinSalary(){
        return this.employeeService.getIdMinSalary();
    }
    @GetMapping("/employees/salary/max")
    public Employee getMaxSalary(){
        return this.employeeService.getIdMaxSalary();
    }
    @GetMapping("/employees/salary/high-salary")
    public Map<Integer,Employee> getHighlyPaidEmployee(){
        return this.employeeService.getHighlyPaidEmployee();
    }
}
