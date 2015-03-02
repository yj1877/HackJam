package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;


public class SignUpActivity extends Activity {
    private Button registerButton;
    public String textPerson;
    public String password;
    public String phonenumber;
    public String emailname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //setUpButtons();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpButtons() {
        registerButton = (Button) findViewById(R.id.signup_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textPerson_editText = (EditText) findViewById(R.id.textPerson_editText);
                textPerson = textPerson_editText.getText().toString();
                EditText textPassword_editText2 = (EditText) findViewById(R.id.textPassword_editText2);
                password = textPassword_editText2.getText().toString();
                EditText textPassword_editText3 = (EditText) findViewById(R.id.textPassword_editText3);
                String password2 = textPassword_editText3.getText().toString();
                EditText email_editText = (EditText) findViewById(R.id.email_editText);
                emailname = email_editText.getText().toString();
                EditText phone_editText = (EditText) findViewById(R.id.phone_editText);
                phonenumber = phone_editText.getText().toString();
                
                if (password.equals(password2)) {
                    createUser();
                } else {
                    TextView textView = (TextView)findViewById(R.id.password_confirmation);
                    textView.setText("Passwords do not match");
                }
            }

        });


        }

    private void createUser() {
        ParseUser user = new ParseUser();
        user.setUsername(textPerson);
        user.setPassword(password);
        user.setEmail(emailname);
        user.put("phone", phonenumber);
        user.put("location", null);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });


    }


}
