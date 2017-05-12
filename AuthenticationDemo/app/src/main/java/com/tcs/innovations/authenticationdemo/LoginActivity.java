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

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView empId, password;
    private Button submit;

    private ProgressDialog dialog;

    private RequestQueue queue;

    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_PHONE = "phone";
    public static final String EXTRA_EMP_ID = "emp_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        VolleyLog.DEBUG = true;
        queue = Volley.newRequestQueue(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setIndeterminate(true);

        empId = (AutoCompleteTextView) findViewById(R.id.emp_id);
        password = (AutoCompleteTextView) findViewById(R.id.password);

        submit = (Button) findViewById(R.id.submit_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                final String employeeTv = empId.getText().toString();
                String passwordTv = password.getText().toString();

                Log.i("Register", "emp: " + employeeTv);

                String strUrl = "https://authentication-test.herokuapp.com/login?emp_id=" + employeeTv
                        + "&password=" + passwordTv;

                Log.i("LoginActivity", strUrl);

                // Pass second argument as "null" for GET requests
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, strUrl, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    dialog.cancel();
                                    String result = response.getString("result");

                                    if(result.equalsIgnoreCase("success")){
                                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                                        String name = response.getString("name");
                                        String email = response.getString("email");
                                        String phone = response.getString("phone");

                                        Intent intent = new Intent(LoginActivity.this, LoggedInActivity.class);

                                        intent.putExtra(EXTRA_NAME, name);
                                        intent.putExtra(EXTRA_EMAIL, email);
                                        intent.putExtra(EXTRA_PHONE, phone);
                                        intent.putExtra(EXTRA_EMP_ID, employeeTv);

                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(LoginActivity.this, "Invalid user credentials", Toast.LENGTH_SHORT).show();
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
