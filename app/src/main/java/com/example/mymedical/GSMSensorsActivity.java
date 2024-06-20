package com.example.mymedical;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Map;

public class GSMSensorsActivity extends AppCompatActivity {
    ImageView backArrow, increaseFreestyle, decreaseFreestyle, increaseDexcom, decreaseDexcom;
    TextView quantityFreestyle, quantityDexcom, tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gsmsensors);

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
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GSMSensorsActivity.this, HomeActivity.class));
            }
        });



        decreaseFreestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                decrease(quantityFreestyle);
            }
        });

        increaseFreestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentValue = Integer.parseInt(quantityFreestyle.getText().toString());
                database.increase("Freestyle Libre", currentValue);
                quantityFreestyle.setText(database.getAllSensors().get("FreestyleLibre").toString());

            }
        });

        decreaseDexcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease(quantityDexcom);
            }
        });

        increaseDexcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase(quantityDexcom);
            }
        });


    }

    public void decrease (TextView quantity) {
        String curr = quantity.getText().toString();
        String newQuantity = String.valueOf(Integer.parseInt(curr) - 1);
        quantity.setText(newQuantity);
    }
    public void increase (TextView quantity) {

        String curr = quantity.getText().toString();
        String newQuantity = String.valueOf(Integer.parseInt(curr) + 1);
        quantity.setText(newQuantity);
    }
}