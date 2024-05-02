package com.example.intro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class registerpage extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerpage);

        googleBtn = findViewById(R.id.google_button);
        mAuth = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            navigateToSecondActivity();
        }

        googleBtn.setOnClickListener(v -> signIn());

        findViewById(R.id.sign_up).setOnClickListener(v -> registerWithEmailPassword());

        findViewById(R.id.already_have_an_account).setOnClickListener(v -> navigateToLoginPage());
    }

    void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    void registerWithEmailPassword() {
        // Get references to email, password, and confirm password fields
        TextInputEditText emailEditText = findViewById(R.id.email);
        TextInputEditText passwordEditText = findViewById(R.id.password);
        TextInputEditText confirmPasswordEditText = findViewById(R.id.confirmpassword);

        // Get the text from the fields
        String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();
        String confirmPassword = Objects.requireNonNull(confirmPasswordEditText.getText()).toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(registerpage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();

            if (email.isEmpty()) {
                emailEditText.setError("Required");
            }
            if (password.isEmpty()) {
                passwordEditText.setError("Required");
            }
            if (confirmPassword.isEmpty()) {
                confirmPasswordEditText.setError("Required");
            }

            return;
        }


        if (!password.equals(confirmPassword)) {
            Toast.makeText(registerpage.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        navigateToSecondActivity();
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            // If user already exists, display a toast
                            Toast.makeText(registerpage.this, "Email already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(registerpage.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null) {
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(getApplicationContext(), "Google Sign-In Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        mAuth.signInWithCredential(GoogleAuthProvider.getCredential(acct.getIdToken(), null))
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Save user details for later use
                        saveProfileInfo(acct.getDisplayName(), acct.getEmail(),
                                acct.getPhotoUrl() != null ? acct.getPhotoUrl().toString() : null);

                        // Navigate to Home Page
                        navigateToSecondActivity();
                    } else {
                        Toast.makeText(getApplicationContext(), "Authentication with Google failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigateToSecondActivity();
    }

    void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(registerpage.this, homepage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(registerpage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
        startActivity(intent, options.toBundle());
    }

    void navigateToLoginPage() {
        Intent intent = new Intent(registerpage.this, loginpage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(registerpage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
        startActivity(intent, options.toBundle());
    }

    private void saveProfileInfo(String name, String email, String photoUrl) {
        SharedPreferences prefs = getSharedPreferences("UserProfile", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("photoUrl", photoUrl);
        editor.apply();
    }

}
