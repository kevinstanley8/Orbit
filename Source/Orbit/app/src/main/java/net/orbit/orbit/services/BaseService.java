package net.orbit.orbit.services;

import android.content.Context;

import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.OrbitRestClient;
import net.orbit.orbit.utils.OrbitUserPreferences;
import net.orbit.orbit.utils.PropertiesService;

/**
 * Created by brocktubre on 2/22/18.
 */

public class BaseService {
    private PropertiesService propertiesService;
    private OrbitRestClient orbitRestClient;

    public PropertiesService getPropertiesService(Context context){
        if(this.propertiesService == null){
            return this.propertiesService = new PropertiesService(context);
        }
        return this.propertiesService;
    }

    public OrbitRestClient getOrbitRestClient(Context context){
        if(this.orbitRestClient == null){
            this.orbitRestClient = new OrbitRestClient(context);
            this.orbitRestClient.setBaseUrl(getPropertiesService(context).getProperty(context, Constants.ORBIT_API_URL));
            return this.orbitRestClient;
        }
        return this.orbitRestClient;
    }
}
