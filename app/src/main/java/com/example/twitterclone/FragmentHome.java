package com.example.twitterclone;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


public class FragmentHome extends Fragment {

    private EditText edtUserStatus;
    private Button btnPostTweet;

    RecyclerView recyclerView;

    List<ModelRI> itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        edtUserStatus=view.findViewById(R.id.edtUserStatus);
        btnPostTweet=view.findViewById(R.id.btnPostTweet);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //initData();

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

        recyclerView.setAdapter(new ItemAdapter(initData()));

        return view;
    }

    private List<ModelRI> initData() {
        itemList=new ArrayList<>();
        ParseQuery<ParseObject> parseQuery=new ParseQuery<ParseObject>("MyTweet");

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for(ParseObject object : objects){
                        String tweet = (String) object.get("tweet");
                        String user = (String) object.get("user");

                        itemList.add(new ModelRI(user,tweet));
                    }
                }
            }
        });






        return itemList;

    }


}