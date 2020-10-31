package com.example.twitterclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    List<ModelRI> itemList;


    public ItemAdapter(List<ModelRI> itemList) {
        this.itemList=itemList;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowitem,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {

        holder.userProfile.setImageResource(itemList.get(position).getUserProfile());
        holder.username.setText(itemList.get(position).getUsername());
        holder.userStatus.setText(itemList.get(position).getUserStatus());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userProfile;
        TextView username,userStatus;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfile=itemView.findViewById(R.id.itemPicture);
            username=itemView.findViewById(R.id.itemUsername);
            userStatus=itemView.findViewById(R.id.itemTweet);



        }
    }
}
