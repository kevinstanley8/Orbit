package net.orbit.orbit.services;

import android.content.Context;
import android.widget.Toast;

import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;

import net.orbit.orbit.activities.HomeActivity;
import net.orbit.orbit.messaging.main.ConnectionManager;
import net.orbit.orbit.messaging.utils.PushUtils;
import net.orbit.orbit.utils.OrbitUserPreferences;

/**
 * Created by David on 3/7/2018.
 */

public class ConnectToSendBird
{
    private Context mContext;
    public ConnectToSendBird(Context context)
    {
        this.mContext = context;
    }
    public void connectToSendBird(final String userId, final String userNickname) {
        // Show the loading indicator
        //showProgressBar(true);
        //mConnectButton.setEnabled(false);
        final OrbitUserPreferences orbitPref = new OrbitUserPreferences(mContext);
        ConnectionManager.login(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(com.sendbird.android.User user, SendBirdException e) {
                // Callback received; hide the progress bar.

                if (e != null) {
                    // Error!
                    Toast.makeText(
                            mContext, "" + e.getCode() + ": " + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show login failure snackbar
                    //showSnackbar("Login to SendBird failed");
                    //mConnectButton.setEnabled(true);
                    orbitPref.setConnected(false);
                    return;
                }

                orbitPref.setUserId(orbitPref.getUserPreferenceObj("loggedUser").getUid());
                orbitPref.setNickname(user.getNickname());
                orbitPref.setProfileUrl(user.getProfileUrl());
                orbitPref.setConnected(true);

                // Update the user's nickname
                updateCurrentUserInfo(userNickname);
                updateCurrentUserPushToken();

                // Proceed to MainActivity

            }
        });
    }



    private void updateCurrentUserPushToken() {
        PushUtils.registerPushTokenForCurrentUser(mContext, null);
    }

    private void updateCurrentUserInfo(final String userNickname) {
        SendBird.updateCurrentUserInfo(userNickname, null, new SendBird.UserInfoUpdateHandler() {
            @Override
            public void onUpdated(SendBirdException e) {
                if (e != null) {
                    // Error!
                    Toast.makeText(
                            mContext, "" + e.getCode() + ":" + e.getMessage(),
                            Toast.LENGTH_SHORT)
                            .show();

                    // Show update failed snackbar

                    return;
                }
                final OrbitUserPreferences orbitPref = new OrbitUserPreferences(mContext);
                orbitPref.setNickname(userNickname);
            }
        });


    }
}
