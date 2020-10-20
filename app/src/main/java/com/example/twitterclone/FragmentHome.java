package com.example.twitterclone;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


public class FragmentHome extends Fragment {

    private EditText edtUserStatus;
    private Button btnPostTweet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        edtUserStatus=view.findViewById(R.id.edtUserStatus);
        btnPostTweet=view.findViewById(R.id.btnPostTweet);
        btnPostTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG","btnPostTweet is Tapped");
                ParseObject myTweet = new ParseObject("MyTweet");
                myTweet.put("tweet", edtUserStatus.getText().toString());
                myTweet.put("user", ParseUser.getCurrentUser().getUsername());

                myTweet.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(getContext(),"Tweet is saved",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                        }
                    }
                });
            }
        });


        return view;
    }


}