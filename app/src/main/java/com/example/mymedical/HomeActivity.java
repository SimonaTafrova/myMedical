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

import com.example.mymedical.enums.SensorsEnum;

import java.time.LocalDate;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        CardView exit = findViewById(R.id.cardLogout);
        CardView ce = findViewById(R.id.cardEvents);
        CardView cgm = findViewById(R.id.GCM);
        CardView GPCalls = findViewById(R.id.cardGP);
        CardView logPrescription = findViewById(R.id.cardPerscriptionLog);
        CardView statistics = findViewById(R.id.cardMyProfile);
        Database database = new Database(getApplicationContext(),"myMedical", null, 1);

        setPrescriptionsCardView(logPrescription, database);
        setHeaderGreeting(sharedPreferences);
        setLogout(sharedPreferences, exit);
        setEventsCardView(ce,database);
        startSensors(database);

        cgm.setOnClickListener(v -> startActivity(new Intent( HomeActivity.this, CGMSensorsActivity.class)));
        GPCalls.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, GPActivity.class)));
        statistics.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, StatisticsActivity.class)));


    }

    private void setPrescriptionsCardView(CardView logPrescription, Database database) {
        logPrescription.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                database.createEvent("Collected a new prescription", LocalDate.now().toString());
            }
            logPrescription.setCardBackgroundColor(Color.parseColor("#8CCF8D"));
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
        });
    }

    private void setEventsCardView(CardView ce, Database database) {
        ce.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, EventActivity.class)));




        List<Event> allEvents = database.getAllEvents();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            allEvents.forEach(e -> {
                if(e.getViewed() == 0){
                    ce.setCardBackgroundColor(Color.parseColor("#CD8B8B"));
                }
            });
        }
    }

    private void setLogout(SharedPreferences sharedPreferences, CardView exit) {
        exit.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent( HomeActivity.this, LoginActivity.class));
        });
    }

    private void setHeaderGreeting(SharedPreferences sharedPreferences) {
        String fullName = sharedPreferences.getString("fullName","");
        TextView header = findViewById(R.id.titleHome);
        header.setText(String.format("Hello,  %s !", fullName));
    }

    private void startSensors(Database database) {
        CardView startLibre = findViewById(R.id.LibreStart);
        CardView startDexcom = findViewById(R.id.DexcomStart);

        setSensorsCardView(startLibre, SensorsEnum.FREESTYLE_LIBRE.getName(), database);
        setSensorsCardView(startDexcom, SensorsEnum.DEXCOM.getName(), database);

    }

    private void setSensorsCardView(CardView cardView, String name, Database database) {

        int quantity = database.getSensorQuantity(name);
        if(quantity == 0){
            cardView.setClickable(false);
            cardView.setCardBackgroundColor(Color.parseColor("#DD706666"));
        } else {
            cardView.setOnClickListener(v -> {
                database.decrease(name, quantity);
                cardView.setCardBackgroundColor(Color.parseColor("#8CCF8D"));
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));

            });}
    }
}