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
import com.shubham.umerapp.Models.SessionInfo;
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
        Log.d(TAG, "-----------------------------------------------------------------------------");

        for (userSummary usr : list) {
            Log.d(TAG, "Printing the users and theier responses: "+ usr.getName());
            Log.d(TAG, "Printing Morning responses ");
            for (SessionInfo s :  usr.getMorning()) {
                Log.d(TAG, s.getDate()+" : "+ s.getResponse());
            }
            Log.d(TAG, "Printing Evening responses ");
            for (SessionInfo s :  usr.getEvening()) {
                Log.d(TAG, s.getDate()+" : "+ s.getResponse());
            }
            Log.d(TAG, "-----------------------------------------------------------------------------");
        }

        int m = func.getIndexForSpecificDateforMorning(user,date);
        int e = func.getIndexForSpecificDateforEvening(user,date);



            // for morning
            // user didn't responded
            if(m==-1)
                holder.mstatus.setImageResource(R.drawable.dontknow);
            else
            {
                boolean s = user.getMorning().get(m).getResponse();
                // user didn't responded
                if(s==true)
                    holder.mstatus.setImageResource(R.drawable.received);
                else
                    // user didn't received the product
                    holder.mstatus.setImageResource(R.drawable.notreceived);
            }


        // for evening
        // user didn't responded
        if(e==-1)
            holder.estatus.setImageResource(R.drawable.dontknow);
        else
        {

            boolean s = user.getEvening().get(e).getResponse();
            Log.d(TAG, "onBindViewHolder: for user "+ user.getName()+" respnse is : "+ s);

            // user didn't responded
            if(s==true)
                holder.estatus.setImageResource(R.drawable.received);
            else
                // user didn't received the product
                holder.estatus.setImageResource(R.drawable.notreceived);
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
