package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.orbit.orbit.R;
import net.orbit.orbit.models.pojo.AccountDetailsDTO;
import net.orbit.orbit.models.pojo.Role;
import net.orbit.orbit.models.pojo.User;
import net.orbit.orbit.services.RoleService;
import net.orbit.orbit.services.UserService;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitUserPreferences;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Spinner roleSpinner;
    UserService userService = new UserService(this);
    RoleService roleService = new RoleService(this);
    Map<String, Role> mapRoles = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        roleService.viewRoles(this);
        mapRoles.clear();
        CardView mEmailSignInButton = (CardView) findViewById(R.id.register_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("LOGIN", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("LOGIN", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, RegisterActivity.class);
        return i;
    }

    private void createAccount() {

        // Store values at the time of the login attempt.
        TextView emailTextView = (TextView) findViewById(R.id.email);
        TextView passwordTextView = (TextView) findViewById(R.id.password);
        String email = emailTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordTextView.setError(getString(R.string.error_invalid_password));
            focusView = passwordTextView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            passwordTextView.setError(getString(R.string.error_invalid_password));
            focusView = passwordTextView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailTextView.setError(getString(R.string.error_field_required));
            focusView = emailTextView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailTextView.setError(getString(R.string.error_invalid_email));
            focusView = emailTextView;
            cancel = true;
        }

        if (cancel) {

            focusView.requestFocus();
        } else {

            createAccount(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 5;
    }

    private void createAccount(final String email, String password) {
        Log.d("LOGIN", "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("LOGIN", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, R.string.registrationFailed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String userUID = mAuth.getCurrentUser().getUid();
                            String r = (String) ( (Spinner) findViewById(R.id.roleSpinner) ).getSelectedItem();
                            EditText firstName = (EditText)findViewById(R.id.firstName);
                            EditText lastName = (EditText)findViewById(R.id.lastName);
                            Role role = mapRoles.get(r);
                            // Get current date
                            Date dateObj = new Date();
                            String date = new SimpleDateFormat(Constants.DATE_FORMAT).format(dateObj);
                            User user = new User(email, userUID, date, Constants.USER_INVALID_ATTEMPTS, Constants.USER_ACTIVE, role);
                            AccountDetailsDTO accountDetails = new AccountDetailsDTO(user, firstName.toString(), lastName.toString());

                            Log.i("role", role.toString());
                            // Add user to database
                            userService.addUser(accountDetails);
                            userService.storeUserInPreferences(mAuth);
                            Toast.makeText(RegisterActivity.this, R.string.newAccountCreated,
                                    Toast.LENGTH_SHORT).show();

                            setResult(0);
                            finish();
                        }
                        // ...
                    }
                });
    }

    public void updateRolesSpinner(Role[] roleArray)
    {
        List<String> list = new ArrayList<>();
        for (Role r : roleArray) {
            if (!r.getName().equals(Constants.ROLE_ADMIN)) {
                list.add(r.getName());
                mapRoles.put(r.getName(),r);
            }
        }

        Log.d("Roles list:", list.toString());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(dataAdapter);
    }

    private boolean validateForm()
    {
        return true;
    }
}
