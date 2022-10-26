package ru.mikhailova.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import ru.mikhailova.domain.Employee;
import ru.mikhailova.dto.EmployeeDto;

@Component
public class EmployeeMapper {
    private final ModelMapper mapper;

    public EmployeeMapper() {
        mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public EmployeeDto mapToDto(Employee employee) {
        return mapper.map(employee, EmployeeDto.class);
    }
}
