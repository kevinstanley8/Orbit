package net.orbit.orbit.services;

import android.content.Context;

import net.orbit.orbit.utils.Constants;
import net.orbit.orbit.utils.PropertyReader;

import java.util.Properties;

/**
 * Created by brocktubre on 11/5/17.
 */

public class PropertiesService {

    public PropertyReader propertyReader;
    public Context context;
    public Properties properties;

    public String getProperty(Context context, String propName) {
        this.context = context;
        this.propertyReader = new PropertyReader(context);
        this.properties = this.propertyReader.getMyProperties(Constants.APP_PROPERTIES);
        return this.properties.getProperty(propName);
    }
}
