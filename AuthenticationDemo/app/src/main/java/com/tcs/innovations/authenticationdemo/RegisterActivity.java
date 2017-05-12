package com.tcs.innovations.authenticationdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private AutoCompleteTextView empId, name, email, phone, password;
    private Button submit;

    private ProgressDialog dialog;

    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        VolleyLog.DEBUG = true;

        queue = Volley.newRequestQueue(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(true);

        empId = (AutoCompleteTextView) findViewById(R.id.emp_id);
        name = (AutoCompleteTextView) findViewById(R.id.name);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        phone = (AutoCompleteTextView) findViewById(R.id.phone);
        password = (AutoCompleteTextView) findViewById(R.id.password);

        submit = (Button) findViewById(R.id.submit_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                final String employeeTv = empId.getText().toString();
                final String nameTv = name.getText().toString();
                final String emailTv = email.getText().toString();
                final String phoneTv = phone.getText().toString();
                String passwordTv = password.getText().toString();

                Log.i("Register", "emp: " + employeeTv);

                String strUrl = "https://authentication-test.herokuapp.com/register?emp_id=" + employeeTv
                        + "&name=" + nameTv
                        + "&email=" + emailTv
                        + "&phone=" + phoneTv
                        + "&password=" + passwordTv;

                Log.i("RegisterActivity", strUrl);

                // Pass second argument as "null" for GET requests
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, strUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    dialog.cancel();
                                    String result = response.getString("result");

                                    if(result.equalsIgnoreCase("success")){
                                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RegisterActivity.this, LoggedInActivity.class);

                                        intent.putExtra(LoginActivity.EXTRA_NAME, nameTv);
                                        intent.putExtra(LoginActivity.EXTRA_EMAIL, emailTv);
                                        intent.putExtra(LoginActivity.EXTRA_PHONE, phoneTv);
                                        intent.putExtra(LoginActivity.EXTRA_EMP_ID, employeeTv);

                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "User already registered", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error: ", error.getMessage());
                    }
                });

                queue.add(req);

//                RegisterTask task = new RegisterTask(getApplicationContext(), employeeTv, nameTv, emailTv, phoneTv, passwordTv);
//                task.execute();
            }
        });
    }
}
