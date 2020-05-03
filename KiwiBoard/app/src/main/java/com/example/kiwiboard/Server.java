package com.example.kiwiboard;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server {
    private static String basePath = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442p/";

    public static void createUser(String name, String email, String password){
        JSONObject json = new JSONObject();
        try {
        json.put("action", "insert");
        json.put("email", email);
        json.put("name", name);
        json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        post("authentication.php", json);
    }

    public static void post(String script, JSONObject json){
        final String scriptname = script;
        final JSONObject jsonParam = json;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    URL url = new URL(basePath.concat(scriptname));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        //try {
        //    thread.join();
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
    }
/*
    public static JSONObject get(String script, JSONObject json){
        new ASynchPing(basePath + script, json).execute();

    }

    public static JSONArray getArray(String script, JSONObject json){

    }
*/
}
