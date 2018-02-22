package net.orbit.orbit.services;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;

import net.orbit.orbit.activities.BaseActivity;
import net.orbit.orbit.activities.LoginActivity;
import net.orbit.orbit.utils.OrbitUserPreferences;

/**
 * Created by sristic on 1/31/18.
 */

public class LogoutService {

    private Context context;
    private OrbitUserPreferences orbitPref = new OrbitUserPreferences(this.context);

    public LogoutService(Context context){
        this.context = context;
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        orbitPref.clear();
        context.startActivity(LoginActivity.createIntent(context));
    }
}
