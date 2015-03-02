package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseAnalytics;
import com.parse.ParseObject;

public class ParseStarterProjectActivity extends Activity {
	/** Called when the activity is first created. */

    private Button registerButton;
    private Button loginButton;
    private String username;
    private String password;

    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
		ParseAnalytics.trackAppOpenedInBackground(getIntent());

        setUpButtons();
    }

    private void setUpButtons() {
        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpUserName();
            }
        });

        loginButton = (Button) findViewById(R.id.register_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username_editText = (EditText) findViewById(R.id.username_editText);
                username = username_editText.getText().toString();
                EditText password_editText = (EditText) findViewById(R.id.password_editText);
                password = password_editText.getText().toString();
            }
        });


    }
    // goes to SignUpActivity,
    private void setUpUserName(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }




}
