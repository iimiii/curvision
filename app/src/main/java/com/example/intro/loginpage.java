package com.example.intro;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.GoogleAuthProvider;

public class loginpage extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    ImageView googleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpage);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        Button signInButton = findViewById(R.id.sign_in);
        TextView forgotPasswordButton = findViewById(R.id.forgot_password);
        TextView createNewAccountButton = findViewById(R.id.create_new_account);
        googleBtn = findViewById(R.id.google_button);

        signInButton.setOnClickListener(v -> signInWithEmailPassword());
        forgotPasswordButton.setOnClickListener(v -> forgotPassword());
        createNewAccountButton.setOnClickListener(v -> createNewAccount());
        googleBtn.setOnClickListener(v -> signInWithGoogle());

        // Configure Google Sign-In
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
    }

    private void signInWithEmailPassword() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(loginpage.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();

            if (email.isEmpty()) {
                emailEditText.setError("Required");
            }
            if (password.isEmpty()) {
                passwordEditText.setError("Required");
            }

            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // If sign-in is successful, direct to homepage
                        navigateToSecondActivity();
                    } else {
                        // If sign-in fails, check the reason and display appropriate toast message
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                            // If password is incorrect
                            Toast.makeText(loginpage.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        } else if (exception instanceof FirebaseAuthUserCollisionException) {
                            // If email is not found in Firebase
                            Toast.makeText(loginpage.this, "Email not found", Toast.LENGTH_SHORT).show();
                        } else {
                            // For other authentication failures
                            Toast.makeText(loginpage.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onBackPressed() {
        // Get the class name of the current activity
        String currentActivityName = this.getClass().getSimpleName();
        // Check if the current activity is not the homepage
        if (!currentActivityName.equals("homepage")) {
            // If not, navigate to the homepage
            navigateToSecondActivity();
        } else {
            // If already on the homepage, perform default back button behavior
            super.onBackPressed();
        }
    }


    void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(loginpage.this, homepage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(loginpage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
        startActivity(intent, options.toBundle());
    }

    private void signInWithGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
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
                        Intent intent = new Intent(loginpage.this, homepage.class);
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(loginpage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
                        startActivity(intent, options.toBundle());
                    } else {
                        Toast.makeText(getApplicationContext(), "Google Sign-In Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void forgotPassword() {
        // Implement forgot password functionality here
    }

    private void createNewAccount() {
        Intent intent = new Intent(loginpage.this, registerpage.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(loginpage.this, R.anim.animate_in_out_enter, R.anim.animate_in_out_exit);
        startActivity(intent, options.toBundle());
    }
}