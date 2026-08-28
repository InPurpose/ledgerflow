package org.stephen.ledgerflow.common.api;

public record FieldValidationError(
        String field,
        String message
) {

}
