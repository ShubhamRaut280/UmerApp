package com.shubham.umerapp.Utils;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shubham.umerapp.Models.SessionInfo;
import com.shubham.umerapp.Models.userSummary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class helperFunctions {

    public String getCurrentDate()
    {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }
     public String getCurrentTime(){
        Date time = Calendar.getInstance().getTime();
        return time.toString();
    }


    public int getIndexForSpecificDateforMorning(userSummary user, String date)
    {
        for(int i = 0; i < user.getMorning().size(); i++)
        {
            if(user.getMorning().get(i).getDate().equals(date))
             return i;

        }

        return -1;
    }
    public int getIndexForSpecificDateforEvening(userSummary user, String date)
    {
        for(int i = 0; i < user.getEvening().size(); i++)
        {
            if(user.getEvening().get(i).getDate().equals(date))
            return  i;
        }

        return -1;
    }

    public interface SummaryCallback {
        void onSummaryReceived(ArrayList<userSummary> userSummaryList);
    }

    public static void getSummary(FirebaseFirestore db, Context context, SummaryCallback callback) {
        ArrayList<userSummary> userSummaryList = new ArrayList<>();

        db.collection("users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().isEmpty()) {
                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        userSummary usr = createSummaryFromDocument(document, db);
                        userSummaryList.add(usr);
                    }
                }

                callback.onSummaryReceived(userSummaryList);
            } else {
                Toast.makeText(context, "Something went wrong: " + task.getException(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error fetching user data: ", task.getException());
            }
        });
    }

    private static userSummary createSummaryFromDocument(DocumentSnapshot document, FirebaseFirestore db) {
        userSummary usr = new userSummary();
        usr.setDocumentId(document.getId());
        usr.setName(document.getString("Name"));
        usr.setEmail(document.getString("Email"));
        usr.setPhone(document.getString("Phone Number"));

        ArrayList<SessionInfo> sessionInfoListMorning = getSessionInfoList(document.getId(), "morningSession", db);
        usr.setMorning(sessionInfoListMorning);

        ArrayList<SessionInfo> sessionInfoListEvening = getSessionInfoList(document.getId(), "eveningSession", db);
        usr.setEvening(sessionInfoListEvening);

        return usr;
    }

    public static ArrayList<SessionInfo> getSessionInfoList(String userId, String collectionName, FirebaseFirestore db) {
        ArrayList<SessionInfo> sessionInfoList = new ArrayList<>();

        db.collection(collectionName).document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Map<String, Object> data = documentSnapshot.getData();

                if (data != null) {
                    for (Map.Entry<String, Object> entry : data.entrySet()) {
                        String dateFromFb = entry.getKey();
                        boolean value = (boolean) entry.getValue();
                        SessionInfo sessionInfo = new SessionInfo(dateFromFb, value);
                        sessionInfoList.add(sessionInfo);
                    }
                }
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching session data: " + e.getMessage());
        });

        return sessionInfoList;
    }
}


