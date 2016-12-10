package com.example.murali.alpha.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.murali.alpha.R;
import com.example.murali.alpha.data.LoginDatabaseAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPage extends AppCompatActivity {


    EditText signup_user_name;
    EditText input_password;
    Button btn_signup;
    TextView link_login;
    TextView credentials;
    Pattern pattern;
    Matcher matcher;
    TextInputLayout usernameInputLayout;
    TextInputLayout passwordInputLayout;

    LoginDatabaseAdapter loginDatabaseAdapter;
    private static final String REGEX_PATTERN="((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*[@#$%&=])(?!.*\\s).{8,})";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        signup_user_name = (EditText) findViewById(R.id.signup_user_name);
        input_password = (EditText) findViewById(R.id.input_password);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        link_login = (TextView) findViewById(R.id.link_login);
        credentials= (TextView) findViewById(R.id.credentials);
        usernameInputLayout= (TextInputLayout) findViewById(R.id.usernameInputLayout);
        passwordInputLayout= (TextInputLayout) findViewById(R.id.passwordInputLayout);
        loginDatabaseAdapter=new LoginDatabaseAdapter(this);

        /*
           validate the entered username and password before creating the account
         */

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = signup_user_name.getText().toString();
                String password = input_password.getText().toString();

                if (userName.length()==0&&password.length()==0||userName.length()<3 && password.length()<2) {

                    usernameInputLayout.setErrorEnabled(true);
                    passwordInputLayout.setErrorEnabled(true);
                    usernameInputLayout.setError("please enter username 4 characters long");
                    passwordInputLayout.setError("must be 7+ characters,1 Uppercase,1 lowercase,No special characters");



                } else if (userName.length() > 3 && password.length()>0||userName.length()>3&&password.length()==0) {

                    usernameInputLayout.setErrorEnabled(false);

                    passwordValidate();
                    if (!validate(password)) {
                        passwordInputLayout.setErrorEnabled(true);
                        passwordInputLayout.setError("must be 7+ characters,1 Uppercase,1 lowercase,No special characters");

                    } else {
                        loginDatabaseAdapter.insert(userName, password);
                        Toast.makeText(SignupPage.this, "Account created successfully please login", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }else if(userName.length()==0&&password.length()>0||userName.length()<=3 && password.length()>0){

                    passwordInputLayout.setErrorEnabled(false);
                    usernameInputLayout.setErrorEnabled(true);
                    usernameInputLayout.setError("please enter username 4 characters long");
                }
            }
        });


        /*
          back to login page
         */
        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent linkLogin= new Intent(SignupPage.this,LoginActivity.class);
                startActivity(linkLogin);
            }
        });


    }


    //check the password regular expression
    public void passwordValidate(){
       pattern = Pattern.compile(REGEX_PATTERN);
    }

    public boolean validate(final String checkPassword){
        matcher=pattern.matcher(checkPassword);
        return matcher.matches();


    }


}
