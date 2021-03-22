package com.example.hospitalmanagment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalmanagment.Model.Doctor;
import com.example.hospitalmanagment.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.myViewHolder> {

    private Context context;
    private List<Doctor> doctorList;
    DatabaseReference myRef;
    FirebaseDatabase database;
    public DoctorAdapter(Context context, List<Doctor> patientList) {
        this.context = context;
        this.doctorList = patientList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctorlistcard, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder holder, int position) {
        final Doctor doctor = doctorList.get(position);
        holder.doctorNames.setText(doctor.getDoctorName());
        holder.doctorProfession.setText(doctor.getDoctorProfession());
        database = FirebaseDatabase.getInstance();
        holder.bookAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef = database.getReference().child("AppointmentDoctorList");
                myRef.push().setValue(doctor);
                holder.bookAppointmentBtn.setText("Booked");
                holder.bookAppointmentBtn.setEnabled(false);

            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView doctorNames;
        TextView doctorProfession;
        Button bookAppointmentBtn;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorNames = (TextView) itemView.findViewById(R.id.HospitalName);
            doctorProfession = (TextView) itemView.findViewById(R.id.HospitalAddress);
            bookAppointmentBtn = (Button) itemView.findViewById(R.id.bookAppointmentBtn);
        }
    }
}
