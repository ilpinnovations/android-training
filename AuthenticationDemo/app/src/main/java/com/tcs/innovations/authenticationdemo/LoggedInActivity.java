package com.tcs.innovations.authenticationdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class LoggedInActivity extends AppCompatActivity {

    private AutoCompleteTextView empId, name, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        Intent intent = getIntent();

        String nameStr = intent.getStringExtra(LoginActivity.EXTRA_NAME);
        String empIdStr = intent.getStringExtra(LoginActivity.EXTRA_EMP_ID);
        String phoneStr = intent.getStringExtra(LoginActivity.EXTRA_PHONE);
        String emailStr = intent.getStringExtra(LoginActivity.EXTRA_EMAIL);


        empId = (AutoCompleteTextView) findViewById(R.id.emp_id);
        name = (AutoCompleteTextView) findViewById(R.id.name);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        phone = (AutoCompleteTextView) findViewById(R.id.phone);

        empId.setEnabled(false);
        name.setEnabled(false);
        email.setEnabled(false);
        phone.setEnabled(false);

        empId.setText(empIdStr);
        email.setText(emailStr);
        phone.setText(phoneStr);
        name.setText(nameStr);
    }
}
