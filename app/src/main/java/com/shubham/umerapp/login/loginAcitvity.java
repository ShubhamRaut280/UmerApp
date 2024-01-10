package com.shubham.umerapp.login;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.shubham.umerapp.MainActivity;
import com.shubham.umerapp.R;

import java.util.concurrent.TimeUnit;

public class loginAcitvity extends AppCompatActivity {
    String verificationId;
    ImageView googleLogin;
    GoogleSignInClient googleSignInClient;
    int RC_SIGN_IN = 20;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitvity);

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





    }




    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentuser != null)
        {
            Intent intent = new Intent(loginAcitvity.this, MainActivity.class);
            startActivity(intent);
            finish();
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
                Toast.makeText(this, "Error code: " + statusCode, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(loginAcitvity.this, "sign in successful", Toast.LENGTH_SHORT).show();
                            // Update UI
                            Intent intent = new Intent(loginAcitvity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(loginAcitvity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


}