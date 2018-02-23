package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;

import net.orbit.orbit.messaging.main.ConnectionManager;
import net.orbit.orbit.messaging.utils.PushUtils;
import net.orbit.orbit.utils.OrbitUserPreferences;

/**
 * Created by David on 2/22/2018.
 */

public class SendBirdLogin
{
    private Context mContext;

    public SendBirdLogin(Context context)
    {
        this.mContext = context;
    }
    OrbitUserPreferences orbitPref = new OrbitUserPreferences(mContext);


    public void connectToSendBird(final String userId, final String userNickname) {
        // Show the loading indicator
        //showProgressBar(true);
        //mConnectButton.setEnabled(false);

        ConnectionManager.login(userId, new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
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

                orbitPref.setNickname(userNickname);
            }
        });
    }
}
