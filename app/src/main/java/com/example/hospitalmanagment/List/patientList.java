package com.example.hospitalmanagment.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.hospitalmanagment.Adapter.PatientAdapter;
import com.example.hospitalmanagment.Model.Patient;
import com.example.hospitalmanagment.R;

import java.util.ArrayList;
import java.util.List;

public class patientList extends AppCompatActivity {

    RecyclerView patientRecycler;
    PatientAdapter patientAdapter;
    List<Patient> patientList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        patientRecycler = findViewById(R.id.petientRecycler);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());

        Patient patient = new Patient("ads", "da");
         patient = new Patient("ads", "da");
        patientList.add(patient);
        patientAdapter = new PatientAdapter(getApplicationContext(), patientList);
        patientRecycler.setAdapter(patientAdapter);
        patientRecycler.setLayoutManager(mlayoutManager);



    }
}