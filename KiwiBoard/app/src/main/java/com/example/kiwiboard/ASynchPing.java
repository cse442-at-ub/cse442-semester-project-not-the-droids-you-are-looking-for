package com.example.kiwiboard;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ASynchPing extends AsyncTask<String, String, String> {
    private String scriptpath;
    private JSONObject json;
    private HttpURLConnection urlConnection;

    public ASynchPing(String scriptpath, JSONObject json) {
        this.scriptpath = scriptpath;
        this.json = json;
    }

    @Override
    protected String doInBackground(String... args) {

        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(scriptpath);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);

            //JSONObject jsonParam = new JSONObject();
            //jsonParam.put("action", "get");
            //jsonParam.put("name", "Hackerman");

            Log.i("JSON", json.toString());
            DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            os.writeBytes(json.toString());

            os.flush();
            os.close();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch( Exception e) {
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        //Do something with the JSON string
        //MainActivity main = (MainActivity) context;
        //main.setjson(result);
    }

}
