package com.example.mymedical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GSMSensorsActivity extends AppCompatActivity {
    ImageView backArrow, increase, decrease;
    TextView quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gsmsensors);

        quantity = findViewById(R.id.quantityFreestyle);

        backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GSMSensorsActivity.this, HomeActivity.class));
            }
        });

        increase = findViewById(R.id.increaseFreestyle);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr = quantity.getText().toString();
                String newQuantity = String.valueOf(Integer.parseInt(curr) + 1);
                quantity.setText(newQuantity);

            }
        });


    }
}