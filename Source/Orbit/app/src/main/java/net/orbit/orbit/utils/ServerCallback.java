package net.orbit.orbit.utils;
import net.orbit.orbit.models.exceptions.ErrorResponse;

import org.json.JSONObject;

/**
 * Created by brocktubre on 1/31/18.
 */

public interface ServerCallback<T> {
        void onSuccess(T result);
        void onFail(ErrorResponse errorMessage);
}
