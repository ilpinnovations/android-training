package com.tcs.innovations.authenticationdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 1115394 on 3/28/2017.
 */


public class RegisterTask extends AsyncTask<Void, Void, String> {

    private Context mContext;
    private String mEmpId, mName, mEmail, mPhone, mPassword;

    public RegisterTask(Context context, String empId, String name, String email, String phone, String password){
        this.mContext = context;
        this.mEmpId = empId;
        this.mName = name;
        this.mEmail = email;
        this.mPhone = phone;
        this.mPassword = password;
    }

    @Override
    protected String doInBackground(Void... params) {

        HttpURLConnection connection;
        BufferedReader reader;
        String responseJson = "";

        String strUrl = "http://163.122.226.74:8080/register?emp_id=" + mEmpId
                + "&name=" + mName
                + "&email=" + mEmail
                + "&phone=" + mPhone
                + "&password=" + mPassword;

        Log.i("ReisterTask", strUrl);

        try {
            URL url = new URL(strUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Getting the result back
            InputStream stream = connection.getInputStream();
            if (stream == null){
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line;

            while ( (line = reader.readLine()) != null ){
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0){
                // response was empty
                // no point in parsing
                return null;
            }

            responseJson = buffer.toString();

            Log.i("FetchFeed", responseJson);
        }catch (Exception e){
            e.printStackTrace();
        }


        return responseJson;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

//        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//        builder.setTitle("Loading");
    }

    @Override
    protected void onPostExecute(String responseJson) {
        super.onPostExecute(responseJson);

        if (responseJson.equalsIgnoreCase("user registered successfully")){
            Intent intent = new Intent(mContext, LoggedInActivity.class);
            mContext.startActivity(intent);
        }else {
            Toast.makeText(mContext, "User already registered", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
