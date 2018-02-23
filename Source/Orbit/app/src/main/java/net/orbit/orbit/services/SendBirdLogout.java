package net.orbit.orbit.services;

import android.content.Context;
import android.content.Intent;

import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;

import net.orbit.orbit.activities.HomeActivity;
import net.orbit.orbit.messaging.main.ConnectionManager;
import net.orbit.orbit.utils.OrbitUserPreferences;

/**
 * Created by David on 2/22/2018.
 */

public class SendBirdLogout
{
    private Context mContext;

    public SendBirdLogout(Context context)
    {
        mContext = context;
    }

    OrbitUserPreferences orbitUserPreferences = new OrbitUserPreferences(mContext);
    public void disconnect() {
        SendBird.unregisterPushTokenAllForCurrentUser(new SendBird.UnregisterPushTokenHandler() {
            @Override
            public void onUnregistered(SendBirdException e) {
                // Error!
                // Don't return because we still need to disconnect.
                if (e != null) {
                    //e.printStackTrace();
                } else {
//                    Toast.makeText(MainActivity.this, "All push tokens unregistered.", Toast.LENGTH_SHORT).show();
                }

                ConnectionManager.logout(new SendBird.DisconnectHandler() {
                    @Override
                    public void onDisconnected() {
                        orbitUserPreferences.setConnected(false);
                        Intent intent = new Intent(mContext, HomeActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            }
        });
    }

}
