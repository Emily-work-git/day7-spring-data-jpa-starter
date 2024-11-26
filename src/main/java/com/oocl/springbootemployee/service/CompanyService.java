package com.oocl.springbootemployee.service;

import com.oocl.springbootemployee.exception.CompanyNotFoundException;
import com.oocl.springbootemployee.model.Company;
import com.oocl.springbootemployee.model.Employee;
import com.oocl.springbootemployee.repository.CompanyInMemoryRepository;
import com.oocl.springbootemployee.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public List<Company> findAll(int pageIndex, int pageSize) {
        final Page<Company> pageResult = companyRepository.findAll(PageRequest.of(pageIndex, pageSize));
        return pageResult.getContent();
    }

    public Company findById(Integer id) {
        return companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }


    public List<Employee> getEmployeesByCompanyId(Integer id) {
        Company company = companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
        return company.getEmployees();
    }

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public Company update(Integer id, Company company) {
        final var companyNeedToUpdate = companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);

        var nameToUpdate = company.getName() == null ? companyNeedToUpdate.getName() : company.getName();
        var employeesToUpdate = company.getEmployees() == null ? companyNeedToUpdate.getEmployees() : company.getEmployees();

        final var companyToUpdate = new Company(id, nameToUpdate, employeesToUpdate);
        return companyRepository.save(companyToUpdate);
    }
    public void delete(Integer companyId){
        companyRepository.deleteById(companyId);
    }
}
