package com.example.mymedical;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.time.LocalDate;
import java.util.List;

public class GPActivity extends AppCompatActivity {
    ImageView back;
    EditText edDoctor, edTopic;
    Button button;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gpactivity);
        Database database = new Database(getApplicationContext(),"myMedical", null, 1);

        edDoctor = findViewById(R.id.editTextDoctorName);
        edTopic = findViewById(R.id.editTextCallTopic);
        button = findViewById(R.id.createDoctorLogButton);
        back = findViewById(R.id.backArrowGP);


        initTable(database.getAllGPLogs());

        button.setOnClickListener(v -> createNewLog(edDoctor.getText().toString(),edTopic.getText().toString()));
        back.setOnClickListener(v -> startActivity(new Intent(GPActivity.this, HomeActivity.class)));


    }

    public void createNewLog(String doctor, String topic){
        if(doctor.isEmpty() || topic.isEmpty()){
            Toast.makeText(getApplicationContext(),"All fields are required", Toast.LENGTH_SHORT).show();
        } else {
            Database database = new Database(getApplicationContext(),"myMedical", null, 1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                database.saveGPLog(doctor,topic,LocalDate.now().toString());
            }
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

    public void initTable(List<GPLog> logs) {
        TableLayout table = findViewById(R.id.gpTable);
        TableRow tableRow = new TableRow(this);
        TextView messageHeader = new TextView(this);
        messageHeader.setText("Doctor");
        messageHeader.setTextColor(Color.parseColor("#FFFFFFFF"));
        messageHeader.setTextSize(24);
        messageHeader.setTypeface(Typeface.DEFAULT_BOLD);
        messageHeader.setPadding(30, 10, 10, 10);
        tableRow.addView(messageHeader);
        TextView topicHeader = new TextView(this);
        topicHeader.setTextColor(Color.parseColor("#FFFFFFFF"));
        topicHeader.setTextSize(24);
        topicHeader.setText("Topic");
        tableRow.addView(topicHeader);
        TextView dateHeader = new TextView(this);
        dateHeader.setTextColor(Color.parseColor("#FFFFFFFF"));
        dateHeader.setTextSize(24);
        dateHeader.setText("Date");
        dateHeader.setTypeface(Typeface.DEFAULT_BOLD);

        tableRow.addView(dateHeader);
        table.addView(tableRow);
    }
    }