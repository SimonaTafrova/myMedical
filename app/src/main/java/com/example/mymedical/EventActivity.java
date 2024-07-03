package com.example.mymedical;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;


public class EventActivity extends AppCompatActivity {
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event);

        Database database = new Database(getApplicationContext(),"myMedical", null, 1);
        database.viewAllEvents();

        initTable(database.getLast10Events());

        backBtn = findViewById(R.id.backArrowEvent);

        backBtn.setOnClickListener(v -> startActivity(new Intent(EventActivity.this, HomeActivity.class)));



    }

    @SuppressLint("SetTextI18n")
    public void initTable(List<Event> events){
        TableLayout table = findViewById(R.id.eventTable);
        TableRow tableRow = new TableRow(this);
        TextView messageHeader = new TextView(this);
        messageHeader.setText("Message");
        messageHeader.setTextColor(Color.parseColor("#FFFFFFFF"));
        messageHeader.setTextSize(24);
        messageHeader.setTypeface(Typeface.DEFAULT_BOLD);
        messageHeader.setPadding(30,10,10,10);
        tableRow.addView(messageHeader);
        TextView dateHeader = new TextView(this);
        dateHeader.setTextColor(Color.parseColor("#FFFFFFFF"));
        dateHeader.setTextSize(24);
        dateHeader.setText("Date");
        dateHeader.setTypeface(Typeface.DEFAULT_BOLD);
        tableRow.addView(dateHeader);
        table.addView(tableRow);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            events.forEach(e -> {
                TableRow newRow = new TableRow(this);
                TextView message = new TextView(this);
                message.setTextColor(Color.parseColor("#FFFFFFFF"));
                message.setText(e.getMessage());
                message.setPadding(30,10,50,10);
                TextView date = new TextView(this);
                date.setText(e.getDate());
                date.setTextColor(Color.parseColor("#FFFFFFFF"));
                newRow.addView(message);
                newRow.addView(date);
                table.addView(newRow);
            });
        }

    }
}