package com.example.mymedical;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {
    EditText edUsername, edPassword, edConfirmPassword, edFullName;
    Button btn;
    TextView tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id .editTextRegUsername);
        edPassword = findViewById(R.id.editTextRegPassword);
        edConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        edFullName = findViewById(R.id.editTextRegFullname);
        btn = findViewById(R.id.RegisterButton);
        tv = findViewById(R.id.textViewExistingUser);

        tv.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

        btn.setOnClickListener(v -> registerUser(edUsername.getText().toString(), edPassword.getText().toString(), edConfirmPassword.getText().toString(), edFullName.getText().toString()));

    }

    private void registerUser(String username, String password, String confirmPassword, String fullName) {

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
        } else {
            if (!password.equals(confirmPassword)) {
                Toast.makeText(getApplicationContext(), "Password mismatch!", Toast.LENGTH_SHORT).show();
            } else {
                if (!isValidPassword(password)) {
                    Toast.makeText(getApplicationContext(), "Invalid password!", Toast.LENGTH_SHORT).show();
                } else {
                    Database database = new Database(getApplicationContext(),"myMedical", null, 1);
                    if(database.isUniqueUsername(username) == 1){
                        Toast.makeText(getApplicationContext(), "User with this username already exists!", Toast.LENGTH_SHORT).show();
                    } else {
                        database.register(username, password, fullName);
                        Toast.makeText(getApplicationContext(), "Successful registration!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
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

        return f1 == 1 && f2 == 1;
    }
}