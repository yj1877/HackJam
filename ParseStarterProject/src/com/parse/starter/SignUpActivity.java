package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.ParseException;


public class SignUpActivity extends Activity {
    private Button registerButton;
    private String textPerson;
    private String password;
    private String password2;
    private String phonenumber;
    private String emailname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setUpButtons();
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
                password2 = textPassword_editText3.getText().toString();
                EditText email_editText = (EditText) findViewById(R.id.email_editText);
                emailname = email_editText.getText().toString();
                EditText phone_editText = (EditText) findViewById(R.id.phone_editText);
                phonenumber = phone_editText.getText().toString();
                if (!TextUtils.isEmpty(textPerson) && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(password2) && password.equals(password2)
                        && !TextUtils.isEmpty(emailname)
                        && !TextUtils.isEmpty(phonenumber) && emailname.contains("@")) {
                    createUser();
                } else {
                    String output = "";
                    if (TextUtils.isEmpty(textPerson)){
                        output += "empty username; ";}
                    if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)){
                        output += "empty password; ";}
                    if (!password.equals(password2)){
                        output += "passwords do not match; ";}
                    if (!emailname.contains("@")){
                        output += "invalid email address; ";}
                    if (TextUtils.isEmpty(phonenumber)){
                        output += "empty phone #";}
                    Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();

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
        user.put("loc", (new ParseGeoPoint(37.871593, -122.272747)));
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
//                    ParseUser.getCurrentUser().put("location", "berkeley");
//                    ParseUser.getCurrentUser().saveInBackground();

                    Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(getApplicationContext(),MatchActivity.class);
                    startActivity(in);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong

                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();


                }
            }
        });


    }


}
