package com.example.linkedout.services;

import com.example.linkedout.models.dtos.CompanyDTO;
import com.example.linkedout.models.entities.Company;
import com.example.linkedout.repositories.CompanyRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    public CompanyService(CompanyRepository companyRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
    }

    public void addCompany(CompanyDTO companyDTO) {
        Company company = modelMapper.map(companyDTO, Company.class);

        companyRepository.save(company);
    }

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository
                .findAll()
                .stream()
                .map(company -> modelMapper.map(company, CompanyDTO.class))
                .collect(Collectors.toList());
    }

    public Company findCompanyByName(String companyName) {
        return companyRepository.findByName(companyName);
    }

    public CompanyDTO getCompanyById(String id) {
        Company company = companyRepository.findById(id).orElseThrow();

        return modelMapper.map(company, CompanyDTO.class);
    }

    @Transactional
    public void deleteCompany(String id) {
        Company company = companyRepository.findById(id).orElseThrow();

        company.getEmployees().clear();
        companyRepository.delete(company);
    }
}
