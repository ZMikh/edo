package ru.mikhailova.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.mikhailova.domain.Employee;
import ru.mikhailova.repository.EmployeeRepository;
import ru.mikhailova.service.EmployeeService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }
}
