package ru.mikhailova;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.domain.Department;
import ru.mikhailova.domain.Employee;
import ru.mikhailova.domain.Organization;
import ru.mikhailova.domain.Task;
import ru.mikhailova.repository.EmployeeRepository;
import ru.mikhailova.repository.TaskRepository;

import java.util.List;

@Component
@Profile("local")
@RequiredArgsConstructor
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Organization organization = new Organization();
        organization.setName("Better Call Saul");

        Department department = new Department();
        department.setManager("Los Pollos Hermanos");
        department.setManager("Gustavo Fring");

        Employee firstEmployee = new Employee();
        firstEmployee.setFirstName("Michael");
        firstEmployee.setLastName("Ehrmantraut");
        firstEmployee.setJob("Head of Security");
        employeeRepository.save(firstEmployee);

        Employee secondEmployee = new Employee();
        secondEmployee.setFirstName("Nacho");
        secondEmployee.setLastName("Varga");
        secondEmployee.setJob("Aid");
        employeeRepository.save(secondEmployee);

        Employee thirdEmployee = new Employee();
        thirdEmployee.setFirstName("Tairus");
        employeeRepository.save(thirdEmployee);

        Task firstTask  = new Task();
        firstTask.setSubject("Department protection");
        firstTask.setExecutors(employeeRepository.findAllById(List.of(2L, 3L)));
        firstTask.setAuthor(employeeRepository.findById(1L).orElseThrow());
        taskRepository.save(firstTask);

    }
}
