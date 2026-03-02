package com.labconnect.Exception.labReport;


public class MetricsProcessingException extends RuntimeException {
    public MetricsProcessingException(String message) {
        super("Error processing metrics: " + message);
    }
}
