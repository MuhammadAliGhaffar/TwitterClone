package com.example.twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpActivity extends AppCompatActivity {

    private EditText edt_sign_up_username,edt_sign_up_email,edt_sign_up_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edt_sign_up_username=findViewById(R.id.edt_sign_up_username);
        edt_sign_up_email=findViewById(R.id.edt_sign_up_email);
        edt_sign_up_password=findViewById(R.id.edt_sign_up_password);
        if(ParseUser.getCurrentUser() != null){
            ParseUser.getCurrentUser().logOut();
        }
    }
    public void goToSignInActivity(View view){
        Intent intent=new Intent(SignUpActivity.this,SignInActivity.class);
        startActivity(intent);
        finish();
    }
    public void btnUserSignUp(View view){
        if(edt_sign_up_username.getText().toString().equals("") || edt_sign_up_email.getText().toString().equals("") || edt_sign_up_password.getText().toString().equals("")){
            FancyToast.makeText(SignUpActivity.this,"Please fil out all fields",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();

        }else{
            try{
                //Sign up with parse
                ParseUser user =new ParseUser();

                user.setUsername(edt_sign_up_username.getText().toString());
                user.setEmail(edt_sign_up_email.getText().toString());
                user.setPassword(edt_sign_up_password.getText().toString());

                final ProgressDialog progressDialog=new ProgressDialog(SignUpActivity.this);
                progressDialog.setMessage("Signing up "+edt_sign_up_username.getText().toString());
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){

                            ParseUser.logOut();
                            FancyToast.makeText(SignUpActivity.this,"Account Created Successfully please verify your email before Login",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                            Intent intent =new Intent(SignUpActivity.this,SignInActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                        else{

                            FancyToast.makeText(SignUpActivity.this,"Error Account Creation failed account could not be created :"+e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

                        }
                        progressDialog.dismiss();
                    }
                });

            } catch (Exception e){
                e.printStackTrace();
            }
        }


    }

    public void rootLayoutTappedSignUp(View view){
        try{
            InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}