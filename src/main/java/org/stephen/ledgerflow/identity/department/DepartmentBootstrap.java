package org.stephen.ledgerflow.identity.department;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class DepartmentBootstrap implements CommandLineRunner {
    private final DepartmentRepository departmentRepository;

    public DepartmentBootstrap(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void run(String... args){

        long departmentCount = departmentRepository.count();

        if (departmentCount == 0){
            departmentRepository.save(new Department("Engineering","ENG"));
        }

        List<Department> allDepartments = departmentRepository.findAll();
        allDepartments.forEach(System.out::println);

    }
}
