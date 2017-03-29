package com.tcs.innovations.fragmenttutorial;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton button;
    private static Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (AppCompatButton) findViewById(R.id.change_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("MainActivity", "in onClick()");

                if (flag){
                    Log.i("MainActivity", "flag=true");

                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();

                    FirstFragment fragment = new FirstFragment();
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();

                    flag = false;
                }else {
                    Log.i("MainActivity", "flag=false");

                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();

                    SecondFragment fragment = new SecondFragment();
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();

                    flag = true;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        FirstFragment fragment = new FirstFragment();
        transaction.add(R.id.container, fragment);
        transaction.commit();

    }
}
