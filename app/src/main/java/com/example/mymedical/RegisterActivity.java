package com.example.mymedical;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    EditText edUsername, edPassword, edConfirmPassword;
    Button btn;
    TextView tv;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.editTextRegUsername);
        edPassword = findViewById(R.id.editTextRegPassword);
        edConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btn = findViewById(R.id.RegisterButton);
        tv = findViewById(R.id.textViewExistingUser);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(edUsername.getText().toString(), edPassword.getText().toString(), edConfirmPassword.getText().toString());
            }
        });

    }

    private void registerUser(String username, String password, String confirmPassword) {

        if (username.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
            Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
        } else {
            if (!password.equals(confirmPassword)) {
                Toast.makeText(getApplicationContext(), "Password mismatch!", Toast.LENGTH_SHORT).show();
            } else {
                if (!isValidPassword(password)) {
                    Toast.makeText(getApplicationContext(), "Invalid password!", Toast.LENGTH_SHORT).show();
                } else {
                    database.register(username,password);
                    Toast.makeText(getApplicationContext(), "Successful registration!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        }
    }



    public static boolean isValidPassword(String password){
        int f1 = 0;
        int f2 = 0;
        if(password.length() < 6){
            return false;
        }

        for(int i=0; i < password.length(); i++){
            if(Character.isDigit(password.charAt(i))){
                f1 = 1;
            }
            if(Character.isLetter(password.charAt(i))){
                f2 = 1;
            }
        }

        if( f1==1 && f2==1){
            return true;
        }
        return false;
    }
}