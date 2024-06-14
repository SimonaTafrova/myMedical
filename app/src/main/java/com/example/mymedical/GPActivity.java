package com.example.mymedical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GPActivity extends AppCompatActivity {
    ImageView back;
    EditText edDoctor, edTopic;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpactivity);

        edDoctor = findViewById(R.id.editTextDoctorName);
        edTopic = findViewById(R.id.editTextCallTopic);
        button = findViewById(R.id.createDoctorLogButton);

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
        }
    }
}