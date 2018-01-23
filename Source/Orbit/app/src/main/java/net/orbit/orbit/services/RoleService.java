package net.orbit.orbit.services;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import net.orbit.orbit.activities.BaseActivity;
import net.orbit.orbit.activities.RegisterActivity;
import net.orbit.orbit.models.Role;
import net.orbit.orbit.models.User;
import net.orbit.orbit.utils.OrbitRestClient;
import net.orbit.orbit.utils.OrbitUserPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by sristic on 12/5/17.
 */

public class RoleService {
    OrbitRestClient orbitRestClient = new OrbitRestClient();
    PropertiesService propertiesService = new PropertiesService();
    Context context;

    public RoleService(Context context){
        this.context = context;
        //orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,"orbit.api.url"));
    }

    public void viewRoles(final RegisterActivity activity){
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,"orbit.api.url"));
        orbitRestClient.get("all-roles", null, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray roles) {
                Gson gson = new Gson();
                Role[] teacherList = gson.fromJson(roles.toString(), Role[].class);
                activity.updateRolesSpinner(teacherList);
                Log.d("RolesService", "Successfully pulled all roles: " + roles);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("RolesService", "Error when pulling roles: " + errorResponse);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });
    }

    public void hasTeacherRole(){
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,"orbit.api.url"));
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String UID = currentUser.getUid();
        orbitRestClient.get("has-teacher-role/" + UID, null, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject checkRole) {
//                Gson gson = new Gson();
//                Role[] teacherList = gson.fromJson(roles.toString(), Role[].class);
//                activity.updateRolesSpinner(teacherList);
                Log.d("RolesService", "Checking teacher role: " + checkRole);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("RolesService", "Error on checking teacher role: " + errorResponse);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });
    }

    /**
     * Update nav bar if updateNavBar equals true, otherwise do nothing
     */
    public void findRoleByID(final int roleID, final boolean updateNavBar, final BaseActivity activity){
        String url = "get-role/" + roleID;
        orbitRestClient.setBaseUrl(propertiesService.getProperty(this.context,"orbit.api.url"));
        orbitRestClient.get(url, null, new JsonHttpResponseHandler(){
            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject role) {
                Log.i("RoleService", "Successfully found a role: " + role);
                Gson gson = new Gson();
                Role dbRole = gson.fromJson(role.toString(), Role.class);
                if (updateNavBar) {
                    activity.updateNavBar(dbRole);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject errorResponse) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("RoleService", "Error when finding a role: " + errorResponse);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }

        });
    }
}
