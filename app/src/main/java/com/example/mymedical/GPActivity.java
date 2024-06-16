package com.example.mymedical;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GPActivity extends AppCompatActivity {
    ImageView back;
    EditText edDoctor, edTopic;
    Button button;

    TextView tv;

    List<GPLog> allLogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpactivity);
        Database database = new Database(getApplicationContext(),"myMedical", null, 1);

        edDoctor = findViewById(R.id.editTextDoctorName);
        edTopic = findViewById(R.id.editTextCallTopic);
        button = findViewById(R.id.createDoctorLogButton);
        tv = findViewById(R.id.history);

        tv.setText(getHistory(database.getAllGPLogs()));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewLog(edDoctor.getText().toString(),edTopic.getText().toString());
            }
        });

        back = findViewById(R.id.backArrowGP);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GPActivity.this, HomeActivity.class));
            }
        });


    }

    public void createNewLog(String doctor, String topic){
        if(doctor.length() == 0 || topic.length() == 0){
            Toast.makeText(getApplicationContext(),"All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            allLogs.add(new GPLog(doctor,topic, LocalDate.now()));
            Database database = new Database(getApplicationContext(),"myMedical", null, 1);
            database.saveGPLog(doctor,topic,LocalDate.now().toString());
            edDoctor.setText("");
            edTopic.setText("");
            startActivity(new Intent(GPActivity.this, GPActivity.class));

        }
    }

    public String getHistory(List<String> logs){
        StringBuilder sb = new StringBuilder();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            logs
                    .forEach(l -> {
                        sb.append(l);
                        sb.append(System.lineSeparator());

            });
        }

        return  sb.toString();
    }
}