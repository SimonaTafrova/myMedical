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

import java.util.List;

public class StatisticsActivity extends AppCompatActivity {
    int sensorsCount = 0;
    int perscriptionCount = 0;
    int gpCallsCount = 0;
    TextView sensorView, prescriptionView, doctorCallsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);

        sensorView = findViewById(R.id.SensorsCountTextView);
        prescriptionView = findViewById(R.id.PerscriptionCountTextView);
        doctorCallsView = findViewById(R.id.GpCallCountTextView);

        Database database = new Database(getApplicationContext(),"myMedical", null, 1);

        //perscriptionCount = findViewById(R.id.PerscriptionCountTextView);
        //sensorsCount = findViewById(R.id.SensorsCountTextView);

        List<Event> allEvents = database.getAllEvents();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            allEvents
                    .forEach(e -> {
                        if(e.getMessage().equals("Started new sensor")){
                            sensorsCount++;
                        } else if(e.getMessage().equals("Collected a new perscription")){
                            perscriptionCount++;
                        } else if(e.getMessage().equals("A new doctor call recorded!")){
                            gpCallsCount++;
                        };
                    });
        }


        sensorView.setText(String.valueOf(sensorsCount));
        prescriptionView.setText(String.valueOf(perscriptionCount));
        doctorCallsView.setText(String.valueOf(gpCallsCount));


        // ImageView back = findViewById(R.id.backArrowStat);

       // back.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //startActivity(new Intent(StatisticsActivity.this, HomeActivity.class));
            //}
       // });


    }
}