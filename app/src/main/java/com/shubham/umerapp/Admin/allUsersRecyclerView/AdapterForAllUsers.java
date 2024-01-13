package com.shubham.umerapp.Admin.allUsersRecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.umerapp.R;
import com.shubham.umerapp.User.userDetails;

import java.util.ArrayList;

public class AdapterForAllUsers extends RecyclerView.Adapter<HolderForAllUsers> {
    ArrayList<userDetails> list = new ArrayList<>();

    public AdapterForAllUsers(ArrayList<userDetails> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public HolderForAllUsers onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_tile, parent, false);
        return new HolderForAllUsers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderForAllUsers holder, int position) {
        userDetails user = list.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.phone.setText(user.getPhone());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

