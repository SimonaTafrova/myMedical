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
    TextView sensorView, prescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_statistics);

        sensorView = findViewById(R.id.SensorsCountTextView);
        prescriptionView = findViewById(R.id.PerscriptionCountTextView);

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
                        };
                    });
        }


        sensorView.setText(String.valueOf(sensorsCount));
        prescriptionView.setText(String.valueOf(perscriptionCount));


        // ImageView back = findViewById(R.id.backArrowStat);

       // back.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //startActivity(new Intent(StatisticsActivity.this, HomeActivity.class));
            //}
       // });


    }
}