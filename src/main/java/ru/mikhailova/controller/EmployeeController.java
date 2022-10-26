package ru.mikhailova.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mikhailova.domain.Employee;
import ru.mikhailova.dto.EmployeeDto;
import ru.mikhailova.mapper.EmployeeMapper;
import ru.mikhailova.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;

    @ApiOperation("Получение списка сотрудников")
    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeService.getAll();
        return employees.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
