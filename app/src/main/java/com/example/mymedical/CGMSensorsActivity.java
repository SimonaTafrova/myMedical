package com.example.mymedical;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import java.time.LocalDate;
import java.util.Map;

public class CGMSensorsActivity extends AppCompatActivity {
    ImageView backArrow, increaseFreestyle, decreaseFreestyle, increaseDexcom, decreaseDexcom;
    TextView quantityFreestyle, quantityDexcom;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cgmsensors);

        quantityFreestyle = findViewById(R.id.quantityFreestyle);
        quantityDexcom = findViewById(R.id.quantityDexcom);
        increaseDexcom = findViewById(R.id.increaseDexcom);
        decreaseDexcom = findViewById(R.id.decreaseDexcom);
        increaseFreestyle = findViewById(R.id.increaseFreestyle);
        decreaseFreestyle = findViewById(R.id.decreaseFreestyle);
        backArrow = findViewById(R.id.backArrow);
        Database database = new Database(getApplicationContext(),"myMedical", null, 1);

        Map<String,Integer> allsesors = database.getAllSensors();
        quantityFreestyle.setText(allsesors.get("FreestyleLibre").toString());
        quantityDexcom.setText(allsesors.get("Dexcom").toString());
        backArrow.setOnClickListener(v -> startActivity(new Intent(CGMSensorsActivity.this, HomeActivity.class)));



        decreaseFreestyle.setOnClickListener(v -> {

            int currentValue = Integer.parseInt(quantityFreestyle.getText().toString());
            database.decrease("FreestyleLibre", currentValue);

            quantityFreestyle.setText(database.getAllSensors().get("FreestyleLibre").toString());
        });

        increaseFreestyle.setOnClickListener(v -> {
            int currentValue = Integer.parseInt(quantityFreestyle.getText().toString());
            database.increase("FreestyleLibre", currentValue);
            quantityFreestyle.setText(database.getAllSensors().get("FreestyleLibre").toString());

        });

        decreaseDexcom.setOnClickListener(v -> {
            int currentValue = Integer.parseInt(quantityDexcom.getText().toString());
            database.decrease("Dexcom", currentValue);
            int newValue = database.getAllSensors().get("Dexcom");
            if(newValue <=1){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    database.createEvent("You need to order Dexcome One sensors!", LocalDate.now().toString());
                }
            }
            quantityDexcom.setText(database.getAllSensors().get("Dexcom").toString());
        });

        increaseDexcom.setOnClickListener(v -> {
            int currentValue = Integer.parseInt(quantityDexcom.getText().toString());
            database.increase("Dexcom", currentValue);
            quantityDexcom.setText(database.getAllSensors().get("Dexcom").toString());
        });


    }


}