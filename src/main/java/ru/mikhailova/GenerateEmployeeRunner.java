package ru.mikhailova;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mikhailova.domain.Department;
import ru.mikhailova.domain.Employee;
import ru.mikhailova.domain.Organization;
import ru.mikhailova.repository.EmployeeRepository;

import java.util.List;
import java.util.Random;

@Component
@Profile("local")
@ConditionalOnProperty(value = "generate.employee", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class GenerateEmployeeRunner implements ApplicationRunner {
    private final EmployeeRepository employeeRepository;
    private final List<String> employeesLastName = List.of("Michael", "Nacho", "Tairus");
    private final List<String> employeesFirstName = List.of("Ehrmantraut", "Varga");
    private final List<String> employeesJob = List.of("Head of Security", "Security", "Aid");

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        Organization organization = new Organization();
        organization.setName("Better Call Saul");

        Department department = new Department();
        department.setName("Los Pollos Hermanos");
        department.setManager("Gustavo Fring");
        department.setOrganization(organization);

        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee();
            int randomLastNameIndex = new Random().nextInt(employeesLastName.size());
            employee.setLastName(employeesLastName.get(randomLastNameIndex));

            int randomFirstNameIndex = new Random().nextInt(employeesFirstName.size());
            employee.setFirstName(employeesFirstName.get(randomFirstNameIndex));

            int randomJobIndex = new Random().nextInt(employeesJob.size());
            employee.setJob(employeesJob.get(randomJobIndex));
            employee.setDepartment(department);
            log.info("Generate employee: {}", employee);
            employeeRepository.save(employee);
        }
    }
}
