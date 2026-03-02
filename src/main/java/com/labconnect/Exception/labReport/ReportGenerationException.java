package com.labconnect.Exception.labReport;

public class ReportGenerationException extends RuntimeException {
    public ReportGenerationException(String message) {
        super("Failed to generate LabReport: " + message);
    }
}
