package com.example.krdvszerkeszt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private static final String LOG_TAG = SignupActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 1427;
    EditText usernameEditText;
    EditText useremailEditText;
    EditText userpasswordEditText;
    EditText userpasswordagainEditText;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != 1427){
            finish();
        }

        usernameEditText = findViewById(R.id.usernameEditText);
        useremailEditText = findViewById(R.id.useremailEditText);
        userpasswordEditText = findViewById(R.id.userpasswordEditText);
        userpasswordagainEditText = findViewById(R.id.userpasswordagainEditText);

        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String userNameStr = preferences.getString("userNameStr", "");
        String passwordStr = preferences.getString("passwordStr", "");

        useremailEditText.setText(userNameStr);
        userpasswordEditText.setText(passwordStr);
        userpasswordagainEditText.setText(passwordStr);

        mAuth = FirebaseAuth.getInstance();

        Log.i(LOG_TAG,"onCreate");
    }

    public void cancel(View view) {
        finish();
    }
    public void signup(View view) {
        String userName = usernameEditText.getText().toString();
        String email = useremailEditText.getText().toString();
        String password = userpasswordEditText.getText().toString();
        String passwordagain = userpasswordagainEditText.getText().toString();

        if(!password.equals(passwordagain)){
            Log.e(LOG_TAG,"A jelszavak nem egyeznek meg!");
            return;
        }


        Log.i(LOG_TAG, "Regisztrált: " + userName + "E-mail: " + email);


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Log.d(LOG_TAG,"Sikeres felhasználó létrehozás!");
                    startEdit();
                } else{
                    Log.d(LOG_TAG, "A felhasználót nem sikerült létrehozni!");
                    Toast.makeText(SignupActivity.this, "A felhasználót nem sikerült létrehozni: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void startEdit(/*TODO:regisztralt user adatok*/){
        Intent intent = new Intent(this, FormEditorActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG,"onRestart");
    }
}//signupactivity vege