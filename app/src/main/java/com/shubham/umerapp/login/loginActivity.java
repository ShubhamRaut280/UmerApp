package com.shubham.umerapp.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.shubham.umerapp.Admin.AdminHomeScreen;
import com.shubham.umerapp.Admin.AdminLoginPage;
import com.shubham.umerapp.User.userHomeAcitvity;
import com.shubham.umerapp.R;

public class loginActivity extends AppCompatActivity {

    boolean isAdmin = false;

    ImageView googleLogin;
    CardView loginasAdmin;
    GoogleSignInClient googleSignInClient;
    int RC_SIGN_IN = 20;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitvity);

        loginasAdmin = findViewById(R.id.GotoAdminLogin);
        googleLogin = findViewById(R.id.googleLogin);


        GoogleSignInOptions gso  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);



        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignin();
            }
            private void googleSignin() {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent,RC_SIGN_IN);
            }



        });

        loginasAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAdmin = true;
                Intent intent = new Intent(loginActivity.this, AdminLoginPage.class);
                intent.putExtra("isAdmin", isAdmin);
                startActivity(intent);
            }



        });





    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            if (currentUser.getEmail() != null && currentUser.getEmail().equals("rautshubham368@gmail.com")) {
                isAdmin = true;
            }

            // Assume you have a variable isAdmin indicating the user's role
            if (!isAdmin) {
                Intent intent = new Intent(loginActivity.this, userHomeAcitvity.class);
                startActivity(intent);
                finish();
            } else {
                startActivity(new Intent(loginActivity.this, AdminHomeScreen.class));
                finish();
            }
        }
    }





    // code for google login

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);


            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (ApiException e) {
                int statusCode = e.getStatusCode();
                Toast.makeText(this, "Error code: " + statusCode+" error : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

    }


    private void firebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Log.d(TAG, "onComplete: username is : "+ user.getDisplayName()+ " email for user is : "+ user.getEmail()
                                    +" user : "+ user.getPhoneNumber());
                            Toast.makeText(loginActivity.this, "sign in successful", Toast.LENGTH_SHORT).show();
                            // Update UI
                            Intent intent = new Intent(loginActivity.this, userHomeAcitvity.class);
                            startActivity(intent);

                        }
                    } else {
                        Toast.makeText(loginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


}