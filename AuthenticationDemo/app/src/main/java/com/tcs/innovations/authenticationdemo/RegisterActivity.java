package com.tcs.innovations.authenticationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView empId, name, email, phone, password;
    private Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        empId = (AutoCompleteTextView) findViewById(R.id.emp_id);
        name = (AutoCompleteTextView) findViewById(R.id.name);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        phone = (AutoCompleteTextView) findViewById(R.id.phone);
        password = (AutoCompleteTextView) findViewById(R.id.password);

        submit = (Button) findViewById(R.id.submit_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeeTv = empId.getText().toString();
                String nameTv = name.getText().toString();
                String emailTv = email.getText().toString();
                String phoneTv = phone.getText().toString();
                String passwordTv = password.getText().toString();

                Log.i("Register", "emp: " + employeeTv);

                RegisterTask task = new RegisterTask(getApplicationContext(), employeeTv, nameTv, emailTv, phoneTv, passwordTv);
                task.execute();
            }
        });
    }
}
