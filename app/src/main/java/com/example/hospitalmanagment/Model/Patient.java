package com.example.hospitalmanagment.Model;

public class Patient {
    String PatientName;
    String PatientNumber;

    public Patient(String patientName, String patientNumber) {
        PatientName = patientName;
        PatientNumber = patientNumber;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getPatientNumber() {
        return PatientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        PatientNumber = patientNumber;
    }
}
