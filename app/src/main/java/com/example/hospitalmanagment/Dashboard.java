package com.example.hospitalmanagment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalmanagment.Adapter.AppointmentAdapter;
import com.example.hospitalmanagment.List.AppointmentList;
import com.example.hospitalmanagment.List.DoctorList;
import com.example.hospitalmanagment.List.HospitalList;
import com.example.hospitalmanagment.List.patientList;
import com.example.hospitalmanagment.Model.Appointment;
import com.example.hospitalmanagment.Model.Doctor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    ImageView logoutBtn;
    RelativeLayout card_Patient, card_doctor, card_hospital;
    Bundle bd;
    String defineLogin;
    AppCompatTextView txt_doctrlist;
    RecyclerView AppoinmentRecycler;
    AppointmentAdapter appointmentAdapter;
    List<Appointment> appointmentList = new ArrayList<>();
    DatabaseReference myRef;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        logoutBtn = findViewById(R.id.logout);
        card_Patient = findViewById(R.id.card_Patient);
        card_doctor = findViewById(R.id.card_pharmacy);
        card_hospital = findViewById(R.id.card_hospital);
        txt_doctrlist = findViewById(R.id.txt_doctrlist);

        AppoinmentRecycler = findViewById(R.id.recycler_appointmentList);
        final RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getApplicationContext());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("AppointmentDoctorList");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> objectMap = (HashMap<String, Object>)
                        dataSnapshot.getValue();
                appointmentList = new ArrayList<>();
                for (Object obj : objectMap.values()) {
                    if (obj instanceof Map) {
                        Map<String, Object> mapObj = (Map<String, Object>) obj;
                        Appointment appointment = new Appointment();
                        appointment.setAppointmentDoctorName((String) mapObj.get("doctorName"));
                        appointment.setAppointmentDoctorDes((String) mapObj.get("doctorProfession"));
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


        String value = StorageMangment.getInstance(getApplicationContext()).setLoginInformation();
        System.out.println(value);

        if (value.equals("loginPatient")) {
            card_Patient.setVisibility(View.GONE);
            txt_doctrlist.setText("Your Appointments");
        } else if (value.equals("loginDoctor")) {
            card_doctor.setVisibility(View.GONE);
            txt_doctrlist.setText("Your Appointments Today");
        }
        card_Patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, patientList.class);
                startActivity(intent);
                Toast.makeText(Dashboard.this, "Loading...", Toast.LENGTH_SHORT).show();

            }
        });
        card_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, DoctorList.class);
                startActivity(intent);
                Toast.makeText(Dashboard.this, "Loading...", Toast.LENGTH_SHORT).show();
            }
        });
        card_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Dashboard.this, HospitalList.class);
                startActivity(intent);
                Toast.makeText(Dashboard.this, "Loading...", Toast.LENGTH_SHORT).show();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popup = new PopupMenu(getApplicationContext(), logoutBtn);
                popup.inflate(R.menu.menuitemforfilter);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_logout:
                                Toast.makeText(Dashboard.this, "logout", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                                Intent intent = new Intent(Dashboard.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }
}