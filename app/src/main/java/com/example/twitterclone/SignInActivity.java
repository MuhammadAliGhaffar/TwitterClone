package com.example.twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignInActivity extends AppCompatActivity {

    private EditText edt_sign_in_email,edt_sign_in_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity);

        edt_sign_in_email=findViewById(R.id.edt_sign_in_email);
        edt_sign_in_password=findViewById(R.id.edt_sign_in_password);

        if(ParseUser.getCurrentUser() != null){
            //   ParseUser.getCurrentUser().logOut();
            Intent intent =new Intent(SignInActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }
    public void goToSignUpActivity(View view){
        Intent intent=new Intent(SignInActivity.this,SignUpActivity.class);
        startActivity(intent);
        finish();
    }
    public void btnUserLogIn(View view){
        Log.i("TAG","Sign in is pressed");
        try{
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(edt_sign_in_email.getText().toString().equals("") || edt_sign_in_password.getText().toString().equals("")){
            FancyToast.makeText(SignInActivity.this,"Please fil out all fields",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();

        }else {
            final ProgressDialog progressDialogg=new ProgressDialog(SignInActivity.this);
            progressDialogg.setMessage("Logging in");
            progressDialogg.setCanceledOnTouchOutside(false);
            progressDialogg.show();
            ParseUser.logInInBackground(edt_sign_in_email.getText().toString(), edt_sign_in_password.getText().toString(), new LogInCallback() {

                @Override
                public void done(ParseUser parseuser, ParseException e) {
                    if(parseuser != null) {
                        if(parseuser.getBoolean("emailVerified")){
                            FancyToast.makeText(SignInActivity.this,"Welcome " + parseuser.getUsername() + " !",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                            Intent intent =new Intent(SignInActivity.this,HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }else {

                            ParseUser.logOut();
                            FancyToast.makeText(SignInActivity.this,"Login Fail please verify your email first",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                        }
                    }else{

                        ParseUser.logOut();
                        FancyToast.makeText(SignInActivity.this,"Login Fail "+e.getMessage()+"please re-try",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                    }
                    progressDialogg.dismiss();
                }
            });
        }


    }


    public void rootLayoutTappedSignIn(View view){
        try{
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}