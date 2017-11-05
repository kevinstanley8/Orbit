package net.orbit.orbit.Utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import net.orbit.orbit.HomeActivity;

/**
 * Created by sristic on 10/21/17.
 */

public class OrbitRestClient {
    // TODO Make this value a properties value
    //private static final String BASE_URL = "http://18.220.78.140/orbit-api/";
    //private static final String BASE_URL = "http://10.0.2.2:8080/";
    private String baseUrl;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public void getByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    public void postByUrl(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return getBaseUrl() + relativeUrl;
    }

    public void setBaseUrl(String url){
        this.baseUrl = url;
    }

    public String getBaseUrl(){
        return this.baseUrl;
    }
}
