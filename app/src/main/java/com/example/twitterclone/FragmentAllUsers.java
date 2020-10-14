package com.example.twitterclone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


public class FragmentAllUsers extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList<String> tUsers;
    private ArrayAdapter adapter;
    private String followedUsers="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_all_users, container, false);
        listView=view.findViewById(R.id.listView);
        tUsers = new ArrayList<>();
        adapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_checked,tUsers);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);



        try{

            ParseQuery<ParseUser> query =ParseUser.getQuery();
            query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(objects.size() > 0 && e == null){

                        for(ParseUser twitterUser : objects){
                            tUsers.add(twitterUser.getUsername());
                        }
                        listView.setAdapter(adapter);

                        for(String twitterUser : tUsers){

                            if(ParseUser.getCurrentUser().getList("fanOf") != null){

                                if(ParseUser.getCurrentUser().getList("fanOf").contains(twitterUser)) {
                                    followedUsers=followedUsers+twitterUser+"\n";

                                    listView.setItemChecked(tUsers.indexOf(twitterUser),true);

                                    FancyToast.makeText(getContext(),ParseUser.getCurrentUser().getUsername()+" is following "+followedUsers,FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();

                                }
                            }
                        }

                    }
                }
            });

        }catch (Exception e){
            FancyToast.makeText(getContext(),"Error :"+e.toString(),FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();

        }



        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        CheckedTextView checkedTextView = (CheckedTextView) view;

        if(checkedTextView.isChecked()){
            FancyToast.makeText(getContext(),tUsers.get(position)+" is now followed",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
            ParseUser.getCurrentUser().add("fanOf",tUsers.get(position));

        }else {
            FancyToast.makeText(getContext(),tUsers.get(position)+" user is not followed",FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
            ParseUser.getCurrentUser().getList("fanOf").remove(tUsers.get(position));
            List currentUserFanOfList = ParseUser.getCurrentUser().getList("fanOf");
            ParseUser.getCurrentUser().remove("fanOf");
            ParseUser.getCurrentUser().put("fanOf",currentUserFanOfList);
        }

        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                FancyToast.makeText(getContext(),"saved",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();

            }
        });


    }
}