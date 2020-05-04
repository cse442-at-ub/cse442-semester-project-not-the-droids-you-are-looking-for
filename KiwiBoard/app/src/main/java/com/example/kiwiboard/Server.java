package com.example.kiwiboard;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

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
        login, registerStudent, registerProfessor, launchStudent, launchProfessor, NUMERIC, TRUEFALSE; // Parse Methods
    }

    private static String basePath = "https://www-student.cse.buffalo.edu/CSE442-542/2020-spring/cse-442p/";

    // Student registration process
    public static void registerStudent(Context context, String name, String email, String password, String mode){
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
        String source = "StudentRegistration";
        get("authentication.php", json, ParseMethod.registerStudent, source,  context);
    }
    public static void parseRegisterStudent(Context context, String source, String email, String password, String json){
        Gson gson = new Gson();
        Parser gsonObj = gson.fromJson(json, Parser.class);
        Log.i("STATUS",gsonObj.getStatus());
        Log.i("ID",gsonObj.getId());

        login(context, source, email, password);
    }

    // Professor registration process
    public static void registerProfessor(Context context, String name, String email, String password, String mode){
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
        String source = "ProfRegistration";
        get("authentication.php", json, ParseMethod.registerProfessor, source, context);
    }
    public static void parseRegisterProfessor(Context context, String source, String email, String password, String json){
        Gson gson = new Gson();
        Parser gsonObj = gson.fromJson(json, Parser.class);
        Log.i("STATUS",gsonObj.getStatus());
        Log.i("ID",gsonObj.getId());

        login(context, source, email, password);
    }

    public static void login(Context context, String source, String email, String password){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "login");
            json.put("email", email);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        get("authentication.php", json, ParseMethod.login, source, context);
    }
    public static void parseLogin(Context context, String source, String json) {

            Gson gson = new Gson();
            Parser gsonObj = gson.fromJson(json, Parser.class);
            Log.i("STATUS",gsonObj.getStatus());
            Log.i("ID",gsonObj.getId());
            Log.i("MODE", gsonObj.getMode());

            String mode = gsonObj.getMode();
            if(mode.equals("p")){
                ProfData.setID(gsonObj.getId());
                launchProfessor(context, source, gsonObj.getId());
            } else if(mode.equals("s")) {
                StudentData.setID(gsonObj.getId());
                launchStudent(context, source, gsonObj.getId());
            }
    }

    public static void launchProfessor(Context context, String source, String ID){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "get");
            json.put("ID", ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        get("authentication.php", json, ParseMethod.launchProfessor, source, context);
    }
    public static void parseProfessor(Context context, String source, String json) {

        Gson gson = new Gson();
        Parser gsonObj = gson.fromJson(json, Parser.class);
        Log.i("NAME",gsonObj.getStatus());
        Log.i("EMAIL",gsonObj.getEmail());
        Log.i("PASSWORD",gsonObj.getPassword());
        Log.i("ID",gsonObj.getId());
        Log.i("TYPE",gsonObj.getMode());

        ProfData.setProfessormode(true);
        ProfData.setName(gsonObj.getName());
        ProfData.setEmail(gsonObj.getEmail());
        ProfData.setPassword(gsonObj.getPassword());
        if (source.equals("Login")) {
            Login login = (Login) context;
            login.launchProfessor();
        } else if (source.equals("ProfRegistration")){
            ProfRegistration profRegistration  = (ProfRegistration) context;
            profRegistration.launchProfessor();
        }

    }

    public static void launchStudent(Context context, String source, String ID){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "get");
            json.put("ID", ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        get("authentication.php", json, ParseMethod.launchStudent, source, context);
    }
    public static void parseStudent(Context context, String source, String json) {

        Gson gson = new Gson();
        Parser gsonObj = gson.fromJson(json, Parser.class);
        Log.i("NAME",gsonObj.getStatus());
        Log.i("EMAIL",gsonObj.getEmail());
        Log.i("PASSWORD",gsonObj.getPassword());
        Log.i("ID",gsonObj.getId());
        Log.i("TYPE",gsonObj.getMode());

        StudentData.setStudentmode(true);
        StudentData.setName(gsonObj.getName());
        StudentData.setEmail(gsonObj.getEmail());
        StudentData.setPassword(gsonObj.getPassword());
        if (source.equals("Login")) {
            Login login = (Login) context;
            login.launchStudent();
        } else if (source.equals("StudentRegistration")){
            StudentRegistration studentRegistration = (StudentRegistration) context;
            studentRegistration.launchStudent();
        }
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

    public static void get(String script, final JSONObject json, final ParseMethod parseMethod, String source, Context context){
        final String scriptname = script;
        final JSONObject jsonParam = json;
        final Context mcontext = context;
        final String msource = source;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuilder result = new StringBuilder();
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

                    InputStream in = new BufferedInputStream(conn.getInputStream());

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    String jsonresult = result.toString();
                    jsonresult = jsonresult.substring(jsonresult.indexOf("{"));
                    jsonresult.trim();
                    Log.i("RESULT" , jsonresult);

                    switch (parseMethod){
                        case login:
                            parseLogin(mcontext, msource, jsonresult);
                            break;
                        case registerStudent:
                            parseRegisterStudent(mcontext, msource, jsonParam.getString("email"), jsonParam.getString("password"), jsonresult);
                            break;
                        case registerProfessor:
                            parseRegisterProfessor(mcontext, msource, jsonParam.getString("email"), jsonParam.getString("password"), jsonresult);
                            break;
                        case launchProfessor:
                            parseProfessor(mcontext, msource, jsonresult);
                            break;
                        case launchStudent:
                            parseStudent(mcontext, msource, jsonresult);
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
        //try {
        //    thread.join();
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
    }




/*
    public static JSONObject getjson(String script, JSONObject json){
        new ASynchPing(basePath + script, json).execute();

    }

    public static JSONArray getjsonArray(String script, JSONObject json){

    }
*/
}
