package com.shubham.umerapp.Admin.allUsersRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.umerapp.R;

public class HolderForAllUsers extends RecyclerView.ViewHolder {

    ImageView profilePhoto;
    TextView name, email, phone;
    public HolderForAllUsers(@NonNull View itemView) {
        super(itemView);

        profilePhoto = itemView.findViewById(R.id.userProfileImage);
        name = itemView.findViewById(R.id.name_of_user);
        email = itemView.findViewById(R.id.email_of_user);
        phone = itemView.findViewById(R.id.phone_of_user);

    }
}
