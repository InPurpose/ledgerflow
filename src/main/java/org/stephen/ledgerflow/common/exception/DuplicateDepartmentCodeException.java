package org.stephen.ledgerflow.common.exception;

public class DuplicateDepartmentCodeException extends RuntimeException {

    public DuplicateDepartmentCodeException(String code) {
//        super(code);
        super("Department code '" + code + "' already exists");
    }

}
