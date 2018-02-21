package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.models.pojo.AccountDetailsDTO;
import net.orbit.orbit.models.pojo.User;
import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;
import net.orbit.orbit.utils.OrbitUserPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by sristic on 12/4/17.
 */

public class UserService {
    OrbitRestClient orbitRestClient = new OrbitRestClient();
    PropertiesService propertiesService = new PropertiesService();
    Context context;

    public UserService(Context context){
        this.context = context;
    }


    public void addUser(AccountDetailsDTO accountDetails){
        Gson gson = new Gson();
        String json = gson.toJson(accountDetails);
        StringEntity entity = null;
        try {
            entity = new StringEntity(json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context, Constants.ORBIT_API_URL));
        orbitRestClient.post(this.context, "add-user", entity, "application/json",
                new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray user) {
                        // called when success happens
                        Log.i("RegisterActivity", "Successfully added new user: " + user);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("RegisterActivity", "Error when adding new user: " + errorResponse);
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }

    /**
     * Find user by Firebase UID
     * @param uid
     * @param savetoSP - Save to Shared Preferences
     */
    public void findUserByUID(final String uid, final boolean savetoSP){
        if (uid == null) {
            return;

        }
        String url = "get-user/" + uid;
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,Constants.ORBIT_API_URL));
        orbitRestClient.get(url, null, new JsonHttpResponseHandler(){
                    @Override
                    public void onStart() {
                        // called before request is started
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject user) {
                        Log.i("UserService", "Successfully found a user: " + user);
                        Gson gson = new Gson();
                        User dbUser = gson.fromJson(user.toString(), User.class);
                        if (savetoSP) {
                            OrbitUserPreferences orbitPref = new OrbitUserPreferences(context);
                            orbitPref.storePreference("loggedUser", dbUser);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                        Log.e("UserService", "Error when finding user: " + errorResponse);
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }

    public void storeUserInPreferences(FirebaseAuth mAuth) {
        FirebaseUser user = mAuth.getCurrentUser();
        this.findUserByUID(user.getUid(), true);
    }

}
