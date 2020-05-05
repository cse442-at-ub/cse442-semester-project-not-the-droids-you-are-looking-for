package com.example.kiwiboard;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public enum ParseMethod{
        login, register, loadUserData, createCourse, loadQuestions, createQueueQuestion, createQuestion; // Parse Methods
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
                String name = container.getName();
                Log.i("NAME", name); // Display the fetched name to the console log
                Log.i("ID", ID); // Display the fetched ID to the console log

                // Must check whether a student or professor logged in
                String mode = container.getMode();
                //String mode = "p"; /////////  FIX !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                Log.i("MODE", mode);
                if(mode.equals("p")){
                    ProfData.clearAllData();
                    ProfData.setProfessormode(true);
                    ProfData.setName(name);
                    ProfData.setID(ID);
                    ProfData.setEmail(email);
                } else if(mode.equals("s")) {
                    StudentData.clearAllData();
                    StudentData.setStudentmode(true);
                    StudentData.setName(name);
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
            json.put("user_id", ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("SCRIPT", "course.php");
        get("course.php", json, ParseMethod.loadUserData, context);
    }
    public static void parseUserData(Context context, String json, String mode) {

        if (!json.contains("results")){
            Log.i("STATUS", "No results");
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
            return;
        }

        ArrayList<Course> courses = new ArrayList<>();
        String results = "";
        JSONObject jsonObject = null;
        try {
            jsonObject  = new JSONObject(json);
             JSONArray jarray = jsonObject.getJSONArray("results");
            results = jarray.toString();

            String name, desc, ident, profname;

            Course course;
            Gson gson = new Gson();
            Parser container;
            for(int i = 0; i < jarray.length(); i++)
            {
                JSONObject object = jarray.getJSONObject(i);
                container = gson.fromJson(object.toString(), Parser.class);
                ident = container.getId();
                name = container.getCourse_name();
                desc = container.getCourse_description();
                profname = container.getName();
                course = new Course(name, profname, desc);
                course.setID(ident);
                courses.add(course);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Course course = null;
        // Store courses into data structures
        if (ProfData.isProfessormode()){
            ProfData.setCourses(courses);
            if (ProfData.getCourses().size() == 1)
                ProfData.setCurrentcourse(0);
            course = ProfData.getCourse(ProfData.getCurrentcourse());
            Log.i("CURRENT COURSE", course.getCourseName());
        }
        if (StudentData.isStudentmode()){
            StudentData.setCourses(courses);
            if (StudentData.getCourses().size() == 1)
                StudentData.setCurrentcourse(0);
            course = StudentData.getCourse(StudentData.getCurrentcourse());
            Log.i("CURRENT COURSE", course.getCourseName());
        }
        String courseID = "";
        if (course != null) {
            courseID = course.getID();
            loadQuestions(context, courseID);
        }

    }
    public static void createCourse(Context context, String userID, String mode, String description, String courseName){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "insert");
            json.put("mode", mode);
            json.put("user_id", userID);
            json.put("course_name", courseName);
            json.put("description", description);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("SCRIPT", "course.php");
        get("course.php", json, ParseMethod.createCourse, context);
    }
    public static void parseNewCourse(Context context, String json, String mode) {
        Gson gson = new Gson();
        Parser container = gson.fromJson(json, Parser.class);
        String courseId = container.getId();
        Log.i("COURSE ID", courseId);

        int newindex = ProfData.getCourses().size() - 1;
        Course course = ProfData.getCourse(newindex);
        course.setID(courseId);
        ProfData.setCourse(newindex, course);
        if (ProfData.getCurrentcourse() < 0){
            ProfData.setCurrentcourse(newindex);
        }
    }

    public static void loadQuestions(Context context, String courseID){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "get_all");
            json.put("course_id", courseID);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("SCRIPT", "questions.php");
        get("questions.php", json, ParseMethod.loadQuestions, context);
    }
    public static void parseQuestions(Context context, String json, String courseID){




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

    public static void createQuestion(Context context, Question newquestion){ // Create new question
        String type;
        String number = Integer.toString(newquestion.getQuestionnumber());
        String totaltime = Integer.toString(newquestion.getTimelimit());
        String logtime = Long.toString(newquestion.getTimelaunched());
        String description = newquestion.getDescription();
        String points = Double.toString(newquestion.getMaxpoints());
        ArrayList<String> choices = newquestion.getChoices();
        String[] labels = new String[5];
        labels[0] = "multi_choice1";
        labels[1] = "multi_choice2";
        labels[2] = "multi_choice3";
        labels[3] = "multi_choice4";
        labels[4] = "multi_choice5";
        String mcanswer = Integer.toString(newquestion.getMcanswer());
        String saanswer = newquestion.getTextanswer();
        String courseID = ProfData.getCourse(ProfData.getCurrentcourse()).getID();

        JSONObject json = new JSONObject();
        try {
            json.put("action", "insert");
            json.put("course_id", courseID);
            json.put("description", description);
            json.put("points", points);
            json.put("number", number);
            switch (newquestion.getType()){
                case SHORTANSWER:
                    type = "0";
                    json.put("type", type);
                    json.put("sa_answer", saanswer);
                    break;
                case MULTIPLECHOICE:
                    type = "1";
                    json.put("type", type);
                    for (int i = 0; i < choices.size(); i++){ // Fill all choices
                        json.put(labels[i], choices.get(i));
                    }
                    json.put("mc_answer", mcanswer);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("SCRIPT", "questions.php");
        get("questions.php", json, ParseMethod.createQuestion, context);
    }
    public static void parseNewQuestion(Context context, String json) { // Package the new question ID
        Gson gson = new Gson();
        Parser container = gson.fromJson(json, Parser.class);
        String questionID = container.getId();
        Log.i("QUESTION ID", questionID);

        int cindex = ProfData.getCurrentcourse();
        if (cindex < 0){
            Log.i("ERROR", "Cannot create question without being in a course");
            return;
        }
        Course course = ProfData.getCourse(cindex);
        int newindex = course.getQuestions().size() - 1;
        Question question = course.getQuestion(newindex);
        question.setID(questionID);
        course.setQuestion(newindex, question);
        ProfData.setCourse(cindex, course);
    }

    public static void createQueueQuestion(Context context, Question newquestion){
        String type;
        String number = Integer.toString(newquestion.getQuestionnumber());
        String description = newquestion.getDescription();
        String points = Double.toString(newquestion.getMaxpoints());
        ArrayList<String> choices = newquestion.getChoices();
        String[] labels = new String[5];
        labels[0] = "multi_choice1";
        labels[1] = "multi_choice2";
        labels[2] = "multi_choice3";
        labels[3] = "multi_choice4";
        labels[4] = "multi_choice5";
        String mcanswer = Integer.toString(newquestion.getMcanswer());
        String saanswer = newquestion.getTextanswer();
        String courseID = ProfData.getCourse(ProfData.getCurrentcourse()).getID();

        JSONObject json = new JSONObject();
        try {
            json.put("action", "insert");
            json.put("course_id", courseID);
            json.put("description", description);
            json.put("points", points);
            json.put("number", number);
            switch (newquestion.getType()){
            case SHORTANSWER:
                type = "0";
                json.put("type", type);
                json.put("sa_answer", saanswer);
                break;
            case MULTIPLECHOICE:
                type = "1";
                json.put("type", type);
                for (int i = 0; i < choices.size(); i++){ // Fill all choices
                    json.put(labels[i], choices.get(i));
                }
                json.put("mc_answer", mcanswer);
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("SCRIPT", "draft_questions.php");
        get("draft_questions.php", json, ParseMethod.createQueueQuestion, context);
    }
    public static void parseNewQueueQuestion(Context context, String json) { // Package the new question ID
        Gson gson = new Gson();
        Parser container = gson.fromJson(json, Parser.class);
        String questionID = container.getId();
        Log.i("QUESTION ID", questionID);

        int cindex = ProfData.getCurrentcourse();
        if (cindex < 0){
            Log.i("ERROR", "Cannot create question without being in a course");
            return;
        }
        Course course = ProfData.getCourse(cindex);
        int newindex = course.getQueue().size() - 1;
        Question question = course.getQueueQuestion(newindex);
        question.setID(questionID);
        course.setQueueQuestion(newindex, question);
        ProfData.setCourse(cindex, course);
    }

    public static void deleteQueueQuestion(Context context, String questionID){
        JSONObject json = new JSONObject();
        try {
            json.put("action", "delete");
            json.put("id", questionID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("SCRIPT", "draft_questions.php");
        post("draft_questions.php", json);
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
                        case loadQuestions:
                            parseQuestions(context, jsonresult, inputJson.getString("course_id"));
                            break;
                        case createCourse:
                            parseNewCourse(context, jsonresult, inputJson.getString("mode"));
                            break;
                        case createQueueQuestion:
                            parseNewQueueQuestion(context, jsonresult);
                            break;
                        case createQuestion:
                            parseNewQuestion(context, jsonresult);
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
