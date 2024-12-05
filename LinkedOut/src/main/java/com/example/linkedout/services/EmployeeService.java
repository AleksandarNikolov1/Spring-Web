package com.example.linkedout.services;

import com.example.linkedout.models.dtos.EmployeeDTO;
import com.example.linkedout.models.entities.Company;
import com.example.linkedout.models.entities.Employee;
import com.example.linkedout.repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;


    public EmployeeService(EmployeeRepository employeeRepository, CompanyService companyService, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        Company company = companyService.findCompanyByName(employeeDTO.getCompanyName());

        employee.setCompany(company);

        employeeRepository.save(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository
                .findAll()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(String id) {
        Employee employee = employeeRepository.findById(id).orElseThrow();

        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);

        employeeDTO.setCompanyName(employee.getCompany().getName());

        return employeeDTO;
    }

    @Transactional
    public void deleteEmployee(String id){
        Employee employee = employeeRepository.findById(id).orElseThrow();

        employeeRepository.delete(employee);
    }
}
