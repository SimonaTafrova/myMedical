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

        setSensorsQuantity(database, quantityFreestyle, quantityDexcom);
        setDecreaseButtons(decreaseFreestyle, decreaseDexcom, database);
        setIncreaseButtons(increaseFreestyle, increaseDexcom, database);

        backArrow.setOnClickListener(v -> startActivity(new Intent(CGMSensorsActivity.this, HomeActivity.class)));
    }

    private void setIncreaseButtons(ImageView increaseFreestyle, ImageView increaseDexcom, Database database) {
        increaseFreestyle.setOnClickListener(v -> increase("FreestyleLibre", database, quantityFreestyle));
        increaseDexcom.setOnClickListener(v -> increase("Dexcom", database, quantityDexcom));
    }

    private void increase(String name, Database database, TextView quantity) {
        int currentValue = Integer.parseInt(quantity.getText().toString());
        database.increase(name, currentValue);
        quantity.setText(database.getAllSensors().get(name).toString());
    }

    private void setDecreaseButtons(ImageView decreaseFreestyle, ImageView decreaseDexcom, Database database) {
        decreaseFreestyle.setOnClickListener(v -> decrease("FreestyleLibre", database, quantityFreestyle));
        decreaseDexcom.setOnClickListener(c -> decrease("Dexcom", database, quantityDexcom));

    }

    private void decrease(String name,  Database database, TextView quantity) {
        int currentValue = Integer.parseInt(quantity.getText().toString());
        database.decrease(name, currentValue);
        int newValue = database.getAllSensors().get(name);
        if(newValue <=1){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                database.createEvent("You need to order " + name + " sensors!", LocalDate.now().toString());
            }
        }

        quantity.setText(database.getAllSensors().get(name).toString());
    }

    private void setSensorsQuantity(Database database, TextView quantityFreestyle, TextView quantityDexcom) {
        Map<String,Integer> allsesors = database.getAllSensors();
        quantityFreestyle.setText(allsesors.get("FreestyleLibre").toString());
        quantityDexcom.setText(allsesors.get("Dexcom").toString());

    }
}