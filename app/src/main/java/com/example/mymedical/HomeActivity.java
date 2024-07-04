package com.example.mymedical;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.time.LocalDate;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String fullName = sharedPreferences.getString("fullName","");
        TextView header = findViewById(R.id.titleHome);
        header.setText(String.format("Hello,  %s !", fullName));

        CardView exit = findViewById(R.id.cardLogout);
        exit.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent( HomeActivity.this, LoginActivity.class));
        });

        CardView ce = findViewById(R.id.cardEvents);

        ce.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, EventActivity.class)));

        CardView cgm = findViewById(R.id.GCM);
        Database database = new Database(getApplicationContext(),"myMedical", null, 1);

        List<Event> allEvents = database.getAllEvents();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            allEvents.forEach(e -> {
                if(e.getViewed() == 0){
                    ce.setCardBackgroundColor(Color.parseColor("#CD8B8B"));
                }
            });
        }


        cgm.setOnClickListener(v -> startActivity(new Intent( HomeActivity.this, CGMSensorsActivity.class)));

        CardView GPCalls = findViewById(R.id.cardGP);
        GPCalls.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, GPActivity.class)));



        CardView startLibre = findViewById(R.id.LibreStart);

        startLibre.setOnClickListener(v -> {

            int quantity = database.getSensorQuantity("FreestyleLibre");
            database.decrease("FreestyleLibre", quantity);
            startLibre.setCardBackgroundColor(Color.parseColor("#8CCF8D"));
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));

        });

        CardView startDexcom = findViewById(R.id.DexcomStart);

        startDexcom.setOnClickListener(v -> {
            int quantity = database.getSensorQuantity("Dexcom");
            database.decrease("Dexcom", quantity);
            startDexcom.setCardBackgroundColor(Color.parseColor("#8CCF8D"));
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
        });

        CardView logPrescription = findViewById(R.id.cardPerscriptionLog);

        logPrescription.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                database.createEvent("Collected a new prescription", LocalDate.now().toString());
            }
            logPrescription.setCardBackgroundColor(Color.parseColor("#8CCF8D"));
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
        });

        CardView statistics = findViewById(R.id.cardMyProfile);

        statistics.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, StatisticsActivity.class)));


    }
}