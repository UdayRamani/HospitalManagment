package com.example.hospitalmanagment.List;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalmanagment.Adapter.AppointmentAdapter;
import com.example.hospitalmanagment.Adapter.DoctorAdapter;
import com.example.hospitalmanagment.Model.Appointment;
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

public class AppointmentList extends AppCompatActivity {

    RecyclerView AppoinmentRecycler;
    AppointmentAdapter appointmentAdapter;
    List<Appointment> appointmentList = new ArrayList<>();
    DatabaseReference myRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_list);
        AppoinmentRecycler = findViewById(R.id.appointmentRecycler);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("AppointmentDoctorList");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> objectMap = (HashMap<String, Object>)
                        dataSnapshot.getValue();
                for (Object obj : objectMap.values()) {
                    if (obj instanceof Map) {
                        Map<String, Object> mapObj = (Map<String, Object>) obj;
                        Appointment appointment = new Appointment();
                        appointment.setAppointmentDoctorDes((String) mapObj.get("doctorName"));
                        appointment.setAppointmentDoctorName((String) mapObj.get("doctorProfession"));
                        appointmentList.add(appointment);
                        Log.d("Value is: ", " " + mapObj);

                    }
                }


                appointmentAdapter = new AppointmentAdapter(getApplicationContext(), appointmentList);
                AppoinmentRecycler.setAdapter(appointmentAdapter);
                AppoinmentRecycler.setLayoutManager(mlayoutManager);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }
}