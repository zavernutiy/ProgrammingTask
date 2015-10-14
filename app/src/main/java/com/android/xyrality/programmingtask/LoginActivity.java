package com.android.xyrality.programmingtask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private EditText mUsername;
    private EditText mPassword;
    private Button mLogin;

    // variables for storing entered username and password
    private String username;
    private String password;

    // correct username and password
    private final String correctUsername = "android.test@xyrality.com";
    private final String correctPassword = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsername = (EditText) findViewById(R.id.username_edit_text);
        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableLoginIfReady();
            }
        });

        mPassword = (EditText) findViewById(R.id.password_edit_text);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableLoginIfReady();
            }
        });

        mLogin = (Button) findViewById(R.id.login_button);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = mUsername.getText().toString().trim();
                password = mPassword.getText().toString().trim();

                if (correctUsername.equals(username)) {
                    if (correctPassword.equals(password)) {
                        AppDelegate.getSharedPreferences().edit().putString(AppDelegate.USERNAME_TAG, username).commit();
                        AppDelegate.getSharedPreferences().edit().putString(AppDelegate.PASSWORD_TAG, password).commit();

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void enableLoginIfReady() {
        username = mUsername.getText().toString().trim();
        password = mPassword.getText().toString().trim();

        if (!username.isEmpty() && !password.isEmpty()) {
            mLogin.setEnabled(true);
        } else {
            mLogin.setEnabled(false);
        }
    }
}
