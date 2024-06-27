package com.example.mymedical;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","").toString();
        Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();

        CardView exit = findViewById(R.id.cardLogout);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent( HomeActivity.this, LoginActivity.class));
            }
        });

        CardView ce = findViewById(R.id.cardEvents);

        ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, EventActivity.class));
            }
        });

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


        cgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( HomeActivity.this, GSMSensorsActivity.class));
            }
        });

        CardView GPCalls = findViewById(R.id.cardGP);
        GPCalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, GPActivity.class));
            }
        });

        CardView startLibre = findViewById(R.id.LibreStart);

        startLibre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database = new Database(getApplicationContext(),"myMedical", null, 1);
                int quantity = database.getSensorQuantity("FreestyleLibre");
                database.decrease("FreestyleLibre", quantity);
                startLibre.setCardBackgroundColor(Color.parseColor("#8CCF8D"));
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));

            }
        });

        CardView startDexcom = findViewById(R.id.DexcomStart);

        startDexcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database = new Database(getApplicationContext(),"myMedical", null, 1);
                int quantity = database.getSensorQuantity("Dexcom");
                database.decrease("Dexcom", quantity);
                startDexcom.setCardBackgroundColor(Color.parseColor("#8CCF8D"));
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            }
        });

        CardView logPerscription = findViewById(R.id.cardPerscriptionLog);

        logPerscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database = new Database(getApplicationContext(),"myMedical", null, 1);
                database.createEvent("Collected a new perscription", LocalDate.now().toString());
                logPerscription.setCardBackgroundColor(Color.parseColor("#8CCF8D"));
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            }
        });


    }
}