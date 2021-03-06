package com.example.hospitalmanagment.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.hospitalmanagment.Adapter.PatientAdapter;
import com.example.hospitalmanagment.Model.Patient;
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

public class patientList extends AppCompatActivity {

    RecyclerView patientRecycler;
    PatientAdapter patientAdapter;
    List<Patient> patientList = new ArrayList<>();
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);
        patientRecycler = findViewById(R.id.doctorRecycler);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("PatientList");

        Patient patient = new Patient();
        patient.setPatientName("Uday Ramani");
        patient.setPatientNumber("9586592853");
        patient.setPatientGender("male");


        myRef.push().setValue(patient);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               Map<String, Object> objectMap = (HashMap<String, Object>)
                        dataSnapshot.getValue();
                for (Object obj : objectMap.values()) {
                    if (obj instanceof Map) {
                        Map<String, Object> mapObj = (Map<String, Object>) obj;
                        Patient patient1=new Patient();
                        patient1.setPatientName((String) mapObj.get("patientName"));
                        patient1.setPatientNumber((String) mapObj.get("patientNumber"));
                        patient1.setPatientGender((String) mapObj.get("patientGender"));
                        patientList.add(patient1);
                        Log.d( "Value is: ", " " +mapObj);

                    }
                }


//                patient = new Patient(dataSnapshot.getValue().toString(), dataSnapshot.getValue().toString());

                patientAdapter = new PatientAdapter(getApplicationContext(), patientList);
                patientRecycler.setAdapter(patientAdapter);
                patientRecycler.setLayoutManager(mlayoutManager);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });



    }
}