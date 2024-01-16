package com.shubham.umerapp.Admin.summaryRecyclerview;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shubham.umerapp.R;
import com.shubham.umerapp.Models.userSummary;
import com.shubham.umerapp.Utils.helperFunctions;

import java.util.ArrayList;

public class AdapterForSummary extends RecyclerView.Adapter<HolderForSummary> {
    helperFunctions func = new helperFunctions();

    ArrayList<userSummary> list = new ArrayList<>();
    String date;

    public AdapterForSummary(ArrayList<userSummary> list, String date) {
        this.list = list;
        this.date = date;
    }

    @NonNull
    @Override
    public HolderForSummary onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.summary_tile, parent, false);
        return new HolderForSummary(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderForSummary holder, int position) {
        userSummary user = list.get(position);
        holder.username.setText(user.getName());
        holder.phonenumber.setText(user.getPhone());
        int index = func.getIndexForSpecificDateforMorning(user,date);
        Log.d(TAG, "onBindViewHolder: from adapter : "+ user.getName()+" { "+index+" }");

        // user didn't responded
        if(index==-1)
            holder.status.setImageResource(R.drawable.dontknow);
        else
        {

            boolean s = user.getMorning().get(index).getResponse();
            Log.d(TAG, "onBindViewHolder: for user "+ user.getName()+" respnse is : "+ s);

            // user didn't responded
            if(s==true)
                holder.status.setImageResource(R.drawable.received);
            else
                // user didn't received the product
                holder.status.setImageResource(R.drawable.notreceived);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
