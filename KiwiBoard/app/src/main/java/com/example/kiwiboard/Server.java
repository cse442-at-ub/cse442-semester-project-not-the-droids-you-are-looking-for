package com.example.kiwiboard;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Struct;

public class Server {

    public enum ParseMethod{
        login, register, loadUserData, none; // Parse Methods
    }

    private static String basePath = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442p/";
    private static Handler loginHandler, registrationHandler;

    // Registration process
    public static void register(Context context, String name, String email, String password){
        String mode = "";
        if (context instanceof ProfRegistration)
            mode = "p";
        if (context instanceof StudentRegistration)
            mode = "s";
        // Package input JSON object
        JSONObject json = new JSONObject();
        try {
            json.put("action", "insert");
            json.put("email", email);
            json.put("name", name);
            json.put("password", password);
            json.put("mode", mode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        registrationHandler = new Handler(); // Precedes worker thread start
        Log.i("SCRIPT", "authentication.php");
        get("authentication.php", json, ParseMethod.register, context);
    }
    public static void parseRegister(Context context, String email, String password, String json){
        Gson gson = new Gson();
        Parser container = gson.fromJson(json, Parser.class);
        Log.i("STATUS", container.getStatus());
        Log.i("ID", container.getId());

        login(context, email, password);
    }

    public static void login(Context context, String email, String password){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "login");
            json.put("email", email);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Checks if the function was called from the Login activity and not a worker thread
        if ( context instanceof Login ) {
            loginHandler = new Handler(); // Precedes worker thread start
        }
        Log.i("SCRIPT", "authentication.php");
        get("authentication.php", json, ParseMethod.login, context);
    }
    public static void parseLogin(Context context, String json, String email) {

            Gson gson = new Gson();
            Parser container = gson.fromJson(json, Parser.class);
            Log.i("STATUS", container.getStatus());

            // If no ID is returned, the login failed
            if (container.getId() == null){
                if ( context instanceof Login ) {
                    final Context mcontext = context;
                    loginHandler.post(new Runnable(){
                        public void run() {
                            Toast.makeText(mcontext, "Login failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else{
                String ID = container.getId();
                Log.i("ID", ID); // Display the fetched ID to the console log

                // Must check whether a student or professor logged in
                String mode = container.getMode();
                //String mode = "p"; /////////  FIX !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                Log.i("MODE", mode);
                if(mode.equals("p")){
                    ProfData.setProfessormode(true);
                    ProfData.setID(ID);
                    ProfData.setEmail(email);
                } else if(mode.equals("s")) {
                    StudentData.setStudentmode(true);
                    StudentData.setID(ID);
                    StudentData.setEmail(email);
                }
                loadUserData(context, ID, mode);
            }
    }

    public static void loadUserData(Context context, String ID, String mode){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "get");
            json.put("mode", mode);
            json.put("id", ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("SCRIPT", "course.php");
        get("course.php", json, ParseMethod.loadUserData, context);
    }
    public static void parseUserData(Context context, String json, String mode) {

        Gson gson = new Gson();
        Parser container = gson.fromJson(json, Parser.class);
        Log.i("NAME", container.getStatus());
        Log.i("EMAIL", container.getEmail());
        Log.i("ID", container.getId());
        Log.i("MODE", container.getMode());

        final Context mcontext = context;
        if (context instanceof Login) {
            loginHandler.post(new Runnable(){
                public void run() {
                    if(ProfData.isProfessormode())
                        mcontext.startActivity(new Intent(mcontext, ProfMain.class));
                    if(StudentData.isStudentmode())
                        mcontext.startActivity(new Intent(mcontext, StudentMain.class));
                    Login login = (Login) mcontext;
                    login.finish();
                }
            });
        } else if (context instanceof ProfRegistration){
            registrationHandler.post(new Runnable(){
                public void run() {
                    mcontext.startActivity(new Intent(mcontext, ProfMain.class));
                    ProfRegistration profRegistration = (ProfRegistration) mcontext;
                    profRegistration.finish();
                }
            });
        } else if (context instanceof StudentRegistration){
            registrationHandler.post(new Runnable(){
                public void run() {
                    mcontext.startActivity(new Intent(mcontext, StudentMain.class));
                    StudentRegistration studentRegistration = (StudentRegistration) mcontext;
                    studentRegistration.finish();
                }
            });
        }

    }

    public static void get(final String script, final JSONObject inputJson, final ParseMethod parseMethod, final Context context){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder result = new StringBuilder();
                try {

                    URL url = new URL(basePath.concat(script));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    Log.i("INPUT JSON", inputJson.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(inputJson.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    InputStream in = new BufferedInputStream(conn.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    String jsonresult = result.toString();
                    Log.i("RESULT JSON" , jsonresult);

                    switch (parseMethod){
                        case login:
                            parseLogin(context,  jsonresult, inputJson.getString("email"));
                            break;
                        case register:
                            parseRegister(context, inputJson.getString("email"), inputJson.getString("password"), jsonresult);
                            break;
                        case loadUserData:
                            parseUserData(context, jsonresult, inputJson.getString("mode"));
                            break;
                        default:
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
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

}
