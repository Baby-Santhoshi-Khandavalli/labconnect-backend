package com.labconnect.Exception;

public class ReportGenerationException extends RuntimeException {
    public ReportGenerationException(String message) {
        super("Failed to generate LabReport: " + message);
    }
}
