package com.example.hospitalmanagment.List;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalmanagment.Adapter.DoctorAdapter;
import com.example.hospitalmanagment.Adapter.HospitalAdapter;
import com.example.hospitalmanagment.Model.Doctor;
import com.example.hospitalmanagment.Model.Hospital;
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

public class HospitalList extends AppCompatActivity {

    RecyclerView hospitalRecycler;
    HospitalAdapter hospitalAdapter;
    List<Hospital> hospitalList = new ArrayList<>();
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        hospitalRecycler = findViewById(R.id.HospitalRecycler);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("HospitalList");

        Hospital  hospital = new Hospital();
        hospital.setHospitalName("Motion Hospital");
        hospital.setHospitalDescription("Psychiatric");
        hospital.setHospitalAddress("Dev clinic, Nr.Bus stand,Desalpar(vandhay, Bhuj, Gujarat 370040");
        myRef.push().setValue(hospital);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> objectMap = (HashMap<String, Object>)
                        dataSnapshot.getValue();
                for (Object obj : objectMap.values()) {
                    if (obj instanceof Map) {
                        Map<String, Object> mapObj = (Map<String, Object>) obj;
                        Hospital hospital = new Hospital();
                        hospital.setHospitalName((String) mapObj.get("hospitalName"));
                        hospital.setHospitalDescription((String) mapObj.get("hospitalDescription"));
                        hospital.setHospitalAddress((String) mapObj.get("hospitalAddress"));
                        hospitalList.add(hospital);
                        Log.d("Value is: ", " " + mapObj);

                    }
                }


                hospitalAdapter = new HospitalAdapter(getApplicationContext(), hospitalList);
                hospitalRecycler.setAdapter(hospitalAdapter);
                hospitalRecycler.setLayoutManager(mlayoutManager);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }
}