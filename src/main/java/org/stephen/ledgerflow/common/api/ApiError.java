package org.stephen.ledgerflow.common.api;

import java.time.Instant;
import java.util.List;

public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        List<FieldValidationError> fieldErrors
) {

    public ApiError(Instant timestamp,int status, String error, String message, List<FieldValidationError> fieldErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}
