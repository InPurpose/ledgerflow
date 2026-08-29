package org.stephen.ledgerflow.identity.department;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.stephen.ledgerflow.identity.department.api.CreateDepartmentRequest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.stephen.ledgerflow.identity.department.api.DepartmentResponse;

@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/api/v1/departments")
    public List<DepartmentResponse> getDepartments() {
        return departmentService.getDepartments().stream().map(DepartmentResponse::from).toList();
    }

    @PostMapping("/api/v1/departments")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse createDepartment(
            @Valid
            @RequestBody
            CreateDepartmentRequest request
    )
    {
        return DepartmentResponse.from(departmentService.createDepartment(request));
    }

    @GetMapping("/api/v1/departments/{id}")
    public DepartmentResponse getDepartment(@PathVariable UUID id) {
        return DepartmentResponse.from(departmentService.getDepartment(id));
    }

}
