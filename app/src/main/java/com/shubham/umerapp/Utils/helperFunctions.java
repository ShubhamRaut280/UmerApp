package com.shubham.umerapp.Utils;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shubham.umerapp.Admin.LastSummaryForMorning;
import com.shubham.umerapp.Models.SessionInfo;
import com.shubham.umerapp.Models.userSummary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class helperFunctions {

    public String getCurrentDate()
    {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }


    public int getIndexForSpecificDateforMorning(userSummary user, String date)
    {
        int index = -1;
        Log.d(TAG, "getIndexForSpecificDateforMorning: got into hellper size of morning : "+ user.getMorning().size());
        for(int i = 0; i < user.getMorning().size(); i++)
        {
            Log.d(TAG, "getIndexForSpecificDateforMorning: user"+user.getName()+" date "+ user.getMorning().get(i).getDate()+ " my date "+ date+" are they equal : "+ user.getMorning().get(i).getDate().equals(date));
            if(user.getMorning().get(i).getDate().equals(date))
                index = i;
            break;
        }

        return index;
    }
    public int getIndexForSpecificDateforEvening(userSummary user, String date)
    {
        int index = -1;
        for(int i = 0; i < user.getEvening().size(); i++)
        {
            if(user.getEvening().get(i).getDate() == date)
                index = i;
            break;
        }

        return index;
    }


    public static void getSummary(FirebaseFirestore db, Context context, LastSummaryForMorning.SummaryCallback callback)
    {
        ArrayList<userSummary> userSummaryList = new ArrayList<>();

        db.collection("users").get().addOnCompleteListener(task -> {
            if(task.isSuccessful())
            {
                if (!task.getResult().isEmpty())
                {
                    for ( DocumentSnapshot document:task.getResult().getDocuments() ) {
                        userSummary usr = new userSummary();
                        usr.setDocumentId(document.getId());
                        usr.setName(document.getString("Name"));
                        usr.setEmail(document.getString("Email"));
                        usr.setPhone(document.getString("Phone Number"));

                        Log.d(TAG, "getSummary: user s: "+ document.getString("Name")+document.getString("Email")+ " "+ document.getId());

                        ArrayList<SessionInfo> sessionInfoListMorning = new ArrayList<>();
                        ArrayList<SessionInfo> sessionInfoListEvening = new ArrayList<>();


                        // got details now getting users responses

                        // for morning
                        db.collection("morningSession").document(document.getId()).get().addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                Map<String, Object> data = documentSnapshot.getData();

                                if (data != null) {

                                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                                        String datefromfb = entry.getKey();
                                        boolean value = (boolean) entry.getValue();

                                        SessionInfo myObject = new SessionInfo(datefromfb, value);
                                        sessionInfoListMorning.add(myObject);
                                    }

                                }
                            }
                        }).addOnFailureListener(e -> {
                            Toast.makeText(context, "Something went wrong!!"+e.getMessage(), Toast.LENGTH_SHORT).show();

                        });
                        usr.setMorning(sessionInfoListMorning);


                        // for morning
                        db.collection("eveningSession").document(document.getId()).get().addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                Map<String, Object> data = documentSnapshot.getData();

                                if (data != null) {

                                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                                        String datefromfb = entry.getKey();
                                        boolean value = (boolean) entry.getValue();

                                        SessionInfo myObject = new SessionInfo(datefromfb, value);
                                        sessionInfoListEvening.add(myObject);
                                    }

                                }
                            }
                        }).addOnFailureListener(e -> {
                            Toast.makeText(context, "Something went wrong!!"+e.getMessage(), Toast.LENGTH_SHORT).show();

                        });
                        usr.setEvening(sessionInfoListEvening);

                        // Add the populated userSummary to the list
                        userSummaryList.add(usr);

                        Log.d(TAG, "getSummary: size of list from helper : "+ userSummaryList.size());
                    }
                }



                for (userSummary user :
                        userSummaryList) {
                    Log.d(TAG, "onCreate: form helper :size of morning list :  "+
                            user.getMorning().size());}

                callback.onSummaryReceived(userSummaryList);

            }



        }).addOnFailureListener(e->{
            Toast.makeText(context, "Something went wrong !!"+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "getSummaryforMorning: Error in getSummaryMorning :"+ e.getMessage());
        });

        Log.d(TAG, "getSummary: size of list from helper at return  : "+ userSummaryList.size());


//        return userSummaryList;
    }


}
