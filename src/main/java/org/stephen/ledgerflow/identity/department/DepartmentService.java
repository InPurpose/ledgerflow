package org.stephen.ledgerflow.identity.department;

import org.springframework.stereotype.Service;
import org.stephen.ledgerflow.common.exception.DepartmentNotFoundException;
import org.stephen.ledgerflow.common.exception.DuplicateDepartmentCodeException;
import org.stephen.ledgerflow.identity.department.api.CreateDepartmentRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public Department createDepartment(CreateDepartmentRequest request) {

        if (departmentRepository.existsByCode(request.code())){
            throw new DuplicateDepartmentCodeException(request.code());
        }

        Department department = new Department(
                request.name(),
                request.code()
        );

        return departmentRepository.save(department);
    }

    public Department getDepartment(UUID id){

        Department department = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));

        return department;
    }
}
