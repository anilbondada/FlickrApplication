package com.sampletest.anil.testapplication.datastore.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkParamsDetails;
import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkResponseListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by H211060 on 8/25/2018.
 */

public class DataDownloader extends AsyncTask<Object,Void,String> {
    INetworkResponseListener callback;
    INetworkParamsDetails networkParamsDetails;
    public DataDownloader(final INetworkParamsDetails details, final INetworkResponseListener listener){
        this.callback = listener;
        this.networkParamsDetails = details;
    }

    private String getResponseFromServer(INetworkParamsDetails networkParamsDetails){
        String jsonResponse = null;
        HttpsURLConnection conn = null;
        try {
            URL url = new URL(networkParamsDetails.getUrl());

            conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder();
            for (String key : networkParamsDetails.getParams().keySet()) {
                builder.appendQueryParameter(key, networkParamsDetails.getParams().get(key));
            }
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            StringBuilder response = new StringBuilder();
            while ((temp = bufferedReader.readLine()) != null) {
                response.append(temp);
            }
            jsonResponse = response.toString();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn != null){
                conn.disconnect();
            }
        }
        return jsonResponse;
    }

    @Override
    protected String doInBackground(Object... objects) {
        String response = getResponseFromServer(networkParamsDetails);
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        callback.onNetworkResponse(true,200,response);
    }
}
