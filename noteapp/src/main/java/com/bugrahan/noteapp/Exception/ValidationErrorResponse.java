package com.bugrahan.noteapp.Exception;

import java.util.ArrayList;
import java.util.List;


public class ValidationErrorResponse {

    private String message;
    private List<FieldError> errors = new ArrayList<>();

    public ValidationErrorResponse(String message) {
        this.message = message;
    }

    public void addError(String field, String message) {
        errors.add(new FieldError(field, message));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public void setErrors(List<FieldError> errors) {
        this.errors = errors;
    }
// ************************************************************************************
    public static class FieldError {
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
// ************************************************************************************
}
