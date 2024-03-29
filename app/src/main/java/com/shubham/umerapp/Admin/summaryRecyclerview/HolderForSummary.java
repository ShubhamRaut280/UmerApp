package com.shubham.umerapp.Admin.summaryRecyclerview;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.umerapp.R;

public class HolderForSummary extends RecyclerView.ViewHolder {
    TextView username, phonenumber;
    ImageView mstatus, estatus;

    public HolderForSummary(@NonNull View itemView) {
        super(itemView);
        username = itemView.findViewById(R.id.name_of_user);
        phonenumber = itemView.findViewById(R.id.phone_of_user);
        mstatus = itemView.findViewById(R.id.m_status);
        estatus = itemView.findViewById(R.id.e_status);

    }
}
