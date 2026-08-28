package org.stephen.ledgerflow.identity.department.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateDepartmentRequest(
        // name 的验证规则
        @NotBlank
        @Size(max = 100)
        String name,

        // code 的验证规则
        @NotBlank
        @Size(max = 30)
        String code
) {
}