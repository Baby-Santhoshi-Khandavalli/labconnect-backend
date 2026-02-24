package com.labconnect.Exception;

public class InvalidReportDataException extends RuntimeException {
    public InvalidReportDataException(String message) {
        super("Invalid LabReport data: " + message);
    }
}

