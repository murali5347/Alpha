package com.example.murali.alpha.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.murali.alpha.R;
import com.example.murali.alpha.data.LoginDatabaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {


    EditText username;
    EditText password;
    Button loginButton;
    TextView sign_up;
    LoginDatabaseAdapter loginDatabaseAdapter;
    TextView validationError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.input_password);
        loginButton= (Button) findViewById(R.id.btn_login);
        sign_up = (TextView) findViewById(R.id.link_signup);
        validationError= (TextView) findViewById(R.id.validationError);
        loginDatabaseAdapter = new LoginDatabaseAdapter(this);
        //validationError.setVisibility(View.GONE);

        /*
               set the login button functionality
         */
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginName = username.getText().toString();
                String loginPassword = password.getText().toString();
                String checkPassword = loginDatabaseAdapter.storedPassword(loginName);
                /*
                 validate the given credentials
                 */
                if(loginPassword.equals(checkPassword)){
                    //Toast.makeText(LoginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                    validationError.setVisibility(View.GONE);
                    Intent alphaRequestIntent=new Intent(LoginActivity.this,AlphaMainActivity.class);
                    startActivity(alphaRequestIntent);



                }else{
                    validationError.setVisibility(View.VISIBLE);
                    validationError.setTextColor(getResources().getColor(R.color.colorRed));
                    username.setText("");
                    password.setText("");
                }

            }
        });

        /*
         set the sign up functionality
         */

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signUp = new Intent(LoginActivity.this,SignupPage.class);
                startActivity(signUp);
            }
        });


    }


}
