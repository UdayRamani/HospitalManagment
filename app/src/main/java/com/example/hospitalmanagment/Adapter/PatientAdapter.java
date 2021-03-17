package com.example.hospitalmanagment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalmanagment.Model.Patient;
import com.example.hospitalmanagment.R;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.myViewHolder> {

    private Context context;
    private List<Patient> patientList;

    public PatientAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.petientcard,parent,false);
            return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Patient patient=patientList.get(position);
        holder.patientName.setText(patient.getPatientName());
        holder.patientPhoneNumber.setText(patient.getPatientNumber());

    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView patientName;
        TextView patientPhoneNumber;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            patientName = (TextView)itemView.findViewById(R.id.patentName);
            patientPhoneNumber =  (TextView)itemView.findViewById(R.id.phoneNumber);
        }
    }
}
