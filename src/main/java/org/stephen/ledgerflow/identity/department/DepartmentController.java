package org.stephen.ledgerflow.identity.department;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.stephen.ledgerflow.identity.department.api.CreateDepartmentRequest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/api/v1/departments")
    public List<Department> getDepartments() {
        return departmentService.getDepartments();
    }

    @PostMapping("/api/v1/departments")
    @ResponseStatus(HttpStatus.CREATED)
    public Department save(
            @Valid
            @RequestBody
            CreateDepartmentRequest request
    )
    {

        return departmentService.createDepartment(request);
    }

}
