package com.example.hospitalmanagment.Model;

public class Appointment {

    String AppointmentDoctorName;
    String AppointmentDoctorDes;
    String AppointmentDoctorTime;

    public String getAppointmentDoctorName() {
        return AppointmentDoctorName;
    }

    public void setAppointmentDoctorName(String appointmentDoctorName) {
        AppointmentDoctorName = appointmentDoctorName;
    }

    public String getAppointmentDoctorDes() {
        return AppointmentDoctorDes;
    }

    public void setAppointmentDoctorDes(String appointmentDoctorDes) {
        AppointmentDoctorDes = appointmentDoctorDes;
    }

    public String getAppointmentDoctorTime() {
        return "at 7:00 pm Today";
    }

    public void setAppointmentDoctorTime(String appointmentDoctorTime) {
        AppointmentDoctorTime = appointmentDoctorTime;
    }
}
