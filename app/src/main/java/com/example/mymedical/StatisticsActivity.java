package com.example.mymedical;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {
    int sensorsCount = 0;
    int prescriptionCount = 0;
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
        ImageView back = findViewById(R.id.backArrowStat);

        Database database = new Database(getApplicationContext(),"myMedical", null, 1);

        getCounters(database);
        setCounters(sensorView, prescriptionView, doctorCallsView);

        back.setOnClickListener(v -> startActivity(new Intent(StatisticsActivity.this, HomeActivity.class)));

    }

    private void setCounters(TextView sensorView, TextView prescriptionView, TextView doctorCallsView) {

        sensorView.setText(String.valueOf(sensorsCount));
        prescriptionView.setText(String.valueOf(prescriptionCount));
        doctorCallsView.setText(String.valueOf(gpCallsCount));

    }

    private void getCounters(Database database) {
        List<Event> allEvents = database.getAllEvents();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            allEvents
                    .forEach(e -> {
                        switch (e.getMessage()) {
                            case "Started new sensor":
                                sensorsCount++;
                                break;
                            case "Collected a new prescription":
                                prescriptionCount++;
                                break;
                            case "A new doctor call recorded!":
                                gpCallsCount++;
                                break;
                        }

                    });
        }
    }
}