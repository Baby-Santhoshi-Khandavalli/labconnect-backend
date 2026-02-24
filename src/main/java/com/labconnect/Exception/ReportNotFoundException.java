package com.labconnect.Exception;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(Long id) {
        super("LabReport with ID " + id + " not found");
    }
}
