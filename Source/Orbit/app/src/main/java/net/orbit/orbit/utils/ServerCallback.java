package net.orbit.orbit.utils;
import org.json.JSONObject;

/**
 * Created by brocktubre on 1/31/18.
 */

public interface ServerCallback<T> {
        void onSuccess(T result);
        void onFail();
}
