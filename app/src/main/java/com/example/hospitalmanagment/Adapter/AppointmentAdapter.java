package com.example.hospitalmanagment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hospitalmanagment.Model.Appointment;
import com.example.hospitalmanagment.Model.Doctor;
import com.example.hospitalmanagment.R;
import com.example.hospitalmanagment.StorageMangment;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.myViewHolder> {

    private Context context;
    private List<Appointment> appointments;

    public AppointmentAdapter(Context context, List<Appointment> appointments) {
        this.context = context;
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointmentlistcard, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Appointment doctor = appointments.get(position);
        String value = StorageMangment.getInstance(holder.itemView.getContext()).setLoginInformation();
        System.out.println(value);

        if(value.equals("loginPatient"))
        {
            holder.doctorApointmentNames.setText(doctor.getAppointmentDoctorName());
            holder.DoctorAppointmentDescrition.setText(doctor.getAppointmentDoctorDes());
            holder.DoctorAppointmentTime.setText(doctor.getAppointmentDoctorTime());

            Glide.with(holder.itemView.getContext()).load("http://goo.gl/gEgYUd").into(holder.iconCardofAppointment);
        }

        if(value.equals("loginDoctor"))
        {
            holder.doctorApointmentNames.setText("uday ramani");
            holder.DoctorAppointmentDescrition.setText("male  ");
            holder.DoctorAppointmentTime.setText("at 7:00 am               9586592853");
            Glide.with(holder.itemView.getContext()).load(R.drawable.profile_reg).into(holder.iconCardofAppointment);

        }

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView doctorApointmentNames;
        TextView DoctorAppointmentDescrition;
        TextView DoctorAppointmentTime;
        ImageView iconCardofAppointment;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorApointmentNames = (TextView) itemView.findViewById(R.id.DoctorAppointmentName);
            DoctorAppointmentDescrition = (TextView) itemView.findViewById(R.id.DoctorAppointmentDescrition);
            DoctorAppointmentTime = (TextView) itemView.findViewById(R.id.doctorAppointmentTime);
            iconCardofAppointment = (ImageView) itemView.findViewById(R.id.iconCardofAppointment);

        }
    }
}
