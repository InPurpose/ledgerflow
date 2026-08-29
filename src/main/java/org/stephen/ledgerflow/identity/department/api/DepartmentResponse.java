package org.stephen.ledgerflow.identity.department.api;

import org.stephen.ledgerflow.identity.department.Department;

import java.time.Instant;
import java.util.UUID;

public record DepartmentResponse(
        UUID id,
        String name,
        String code,
        Instant createdAt
) {
    public static DepartmentResponse from(Department department) {

        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getCode(),
                department.getCreatedAt()
        );
    }
}
