package net.orbit.orbit.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;

import net.orbit.orbit.R;
import net.orbit.orbit.messaging.main.ConnectionManager;
import net.orbit.orbit.models.pojo.User;
import net.orbit.orbit.services.LogoutService;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitUserPreferences;

public class LogoutActivity extends BaseActivity {

    Context context;

    public static Intent createIntent(Context context) {
        Intent i = new Intent(context, LogoutActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        logout();
        //setContentView(R.layout.activity_logout);
    }

    public void logout() {
        OrbitUserPreferences orbitPref = new OrbitUserPreferences(this.context);
        final User user = orbitPref.getUserPreferenceObj("loggedUser");

        FirebaseAuth.getInstance().signOut();

        if(user.getRole().getName() != Constants.ROLE_STUDENT)
            disconnect();

        orbitPref.clear("loggedUser");
        // Hoping this kills all previous activities
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        //context.startActivity(LoginActivity.createIntent(context));
    }

    private void disconnect() {
        SendBird.unregisterPushTokenAllForCurrentUser(new SendBird.UnregisterPushTokenHandler() {
            @Override
            public void onUnregistered(SendBirdException e) {
                if (e != null) {
                    // Error!
                    e.printStackTrace();

                    // Don't return because we still need to disconnect.
                } else {
//                    Toast.makeText(MainActivity.this, "All push tokens unregistered.", Toast.LENGTH_SHORT).show();
                }

                ConnectionManager.logout(new SendBird.DisconnectHandler() {
                    @Override
                    public void onDisconnected() {
                        OrbitUserPreferences.setConnected(false);
                    }
                });
            }
        });
    }
}
