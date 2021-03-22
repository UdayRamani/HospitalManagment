package com.example.hospitalmanagment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalmanagment.Model.Hospital;
import com.example.hospitalmanagment.R;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.myViewHolder> {

    private Context context;
    private List<Hospital> hospitalList;

    public HospitalAdapter(Context context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospitallistcard, parent, false);
        return new myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Hospital hospital = hospitalList.get(position);
        holder.hospitalNames.setText(hospital.getHospitalName());
        holder.hospitalDes.setText(hospital.getHospitalDescription());
        holder.hospitalAddress.setText(hospital.getHospitalAddress());

    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        TextView hospitalNames;
        TextView hospitalDes;
        TextView hospitalAddress;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            hospitalNames = (TextView) itemView.findViewById(R.id.HospitalName);
            hospitalDes = (TextView) itemView.findViewById(R.id.HospitalDiscriptionName);
            hospitalAddress = (TextView) itemView.findViewById(R.id.HospitalAddress);
        }
    }
}
