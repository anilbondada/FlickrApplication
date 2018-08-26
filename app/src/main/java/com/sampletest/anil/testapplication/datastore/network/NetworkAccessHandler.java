package com.sampletest.anil.testapplication.datastore.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sampletest.anil.testapplication.datastore.network.interfaces.IImageDataCallback;
import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkHandler;
import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkParamsDetails;
import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by H211060 on 8/23/2018.
 */

public class NetworkAccessHandler implements INetworkHandler{

        private static final String TAG = "NetworkAccessHandler";
        private static NetworkAccessHandler mInstance;
        private ImageLoader mImageLoader;
        private RequestQueue mRequestQueue;
        private static Context mCtx;
        private NetworkAccessHandler(Context context){
            this.mCtx = context;
            mRequestQueue = getRequestQueue();
            mImageLoader = new ImageLoader(mRequestQueue,
                    new ImageLoader.ImageCache() {
                        private final LruCache<String, Bitmap>
                                cache = new LruCache<String, Bitmap>(20);

                        @Override
                        public Bitmap getBitmap(String url) {
                            return cache.get(url);
                        }

                        @Override
                        public void putBitmap(String url, Bitmap bitmap) {
                            cache.put(url, bitmap);
                        }
                    });
        }

        private RequestQueue getRequestQueue() {
            if (mRequestQueue == null) {
                mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            }
            return mRequestQueue;
        }

        public static INetworkHandler getInstance(Context ctx){
            if(mInstance == null){
                mInstance = new NetworkAccessHandler(ctx);
            }
            return mInstance;
        }


        @Override
        public void getJSONRequest(final INetworkParamsDetails details, final INetworkResponseListener listener) {
            String url = details.getUrl();
           JSONObject jsonObject = new JSONObject(details.getParams());
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listener.onNetworkResponse(true,Integer.valueOf("1"),response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: "+error.getMessage());
                    listener.onNetworkResponse(false,Integer.valueOf("0"),error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    super.getParams();
                    return details.getParams();
                }
            };

//            addToRequestQueue(request);
            CustomRequest request1 = new CustomRequest(details.getUrl(), details.getParams(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            listener.onNetworkResponse(true, 1, response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onNetworkResponse(false,0,error.getMessage());
                }
            });
            addToRequestQueue(request1);


/*
            JsonObjectRequest jsonArrayRequest = new JsonObjectRequest (Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onNetworkResponse(true,Integer.valueOf("1"),response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onErrorResponse: "+error.getMessage());
                    listener.onNetworkResponse(false,Integer.valueOf("0"),error.toString());
                }
            });
            addToRequestQueue(jsonArrayRequest);
*/
        }


        public <T> void addToRequestQueue(Request<T> req) {
            mRequestQueue.add(req);
        }

        public void getImageFromServer(final String id, String url, final IImageDataCallback callback){
            mImageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                    callback.onImageFetchedSuccess(id,response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    callback.onImageFetchedFailed(error.getMessage());
                }
            });

/*
            ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            callback.onImageFetchedSuccess(id,bitmap);
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            callback.onImageFetchedFailed(error.getMessage());
                        }
                    });
            addToRequestQueue(request);
*/
        }


    public class CustomRequest extends Request<JSONObject> {

        private Response.Listener<JSONObject> listener;
        private Map<String, String> params;
        private int method;
        private String mUrl;

        public CustomRequest(String url, Map<String, String> params,
                             Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
            super(Method.GET, url, errorListener);
            this.listener = reponseListener;
            this.params = params;
            this.method = Method.GET;
            this.mUrl = url;
        }

        public CustomRequest(int method, String url, Map<String, String> params,
                             Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
            super(method, url, errorListener);
            this.listener = reponseListener;
            this.params = params;
            this.method = method;
            this.mUrl = url;
        }

        @Override
        public String getUrl() {
            if(method == Request.Method.GET) {
                if(params != null) {
                    StringBuilder stringBuilder = new StringBuilder(mUrl);
                    Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
                    int i = 1;
                    while (iterator.hasNext()) {
                        Map.Entry<String, String> entry = iterator.next();
                        if (i == 1) {
                            stringBuilder.append("?" + entry.getKey() + "=" + entry.getValue());
                        } else {
                            stringBuilder.append("&" + entry.getKey() + "=" + entry.getValue());
                        }
                        iterator.remove(); // avoids a ConcurrentModificationException
                        i++;
                    }
                    mUrl = stringBuilder.toString();
                }
            }
            return mUrl;
        }

        protected Map<String, String> getParams()
                throws com.android.volley.AuthFailureError {
            return params;
        };

        @Override
        protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers));
                return Response.success(new JSONObject(jsonString),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }

        @Override
        protected void deliverResponse(JSONObject response) {
            // TODO Auto-generated method stub
            listener.onResponse(response);
        }

    }

}
