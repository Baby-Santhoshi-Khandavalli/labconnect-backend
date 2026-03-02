package com.labconnect.Exception.labReport;

public class InvalidReportDataException extends RuntimeException {
    public InvalidReportDataException(String message) {
        super("Invalid LabReport data: " + message);
    }
}

