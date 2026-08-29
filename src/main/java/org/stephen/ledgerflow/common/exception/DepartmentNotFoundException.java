package org.stephen.ledgerflow.common.exception;

import java.util.UUID;

public class DepartmentNotFoundException extends RuntimeException {
    public DepartmentNotFoundException(UUID id) {
        super("Department '" + id +"' was not found");
    }
}
