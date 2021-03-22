package com.example.hospitalmanagment.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalmanagment.Adapter.DoctorAdapter;
import com.example.hospitalmanagment.Adapter.PatientAdapter;
import com.example.hospitalmanagment.Model.Doctor;
import com.example.hospitalmanagment.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorList extends AppCompatActivity {

    RecyclerView doctorRecycler;
    DoctorAdapter doctorAdapter;
    List<Doctor> doctorList = new ArrayList<>();
    DatabaseReference myRef;
    FirebaseDatabase database;
    Button bookAppointmentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        doctorRecycler = findViewById(R.id.doctorRecycler);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("DoctorList");
        Doctor doctor = new Doctor();
        doctor.setDoctorName("Uday Ramani");
        doctor.setDoctorProfession("Mediean");
        myRef.push().setValue(doctor);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> objectMap = (HashMap<String, Object>)
                        dataSnapshot.getValue();
                for (Object obj : objectMap.values()) {
                    if (obj instanceof Map) {
                        Map<String, Object> mapObj = (Map<String, Object>) obj;
                        Doctor doctor = new Doctor();
                        doctor.setDoctorName((String) mapObj.get("doctorName"));
                        doctor.setDoctorProfession((String) mapObj.get("doctorProfession"));
                        doctorList.add(doctor);
                        Log.d("Value is: ", " " + mapObj);

                    }
                }


                doctorAdapter = new DoctorAdapter(getApplicationContext(),doctorList );
                doctorRecycler.setAdapter(doctorAdapter);
                doctorRecycler.setLayoutManager(mlayoutManager);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }
}