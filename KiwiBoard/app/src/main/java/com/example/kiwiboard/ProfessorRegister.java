package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfessorRegister extends AppCompatActivity {
    private ArrayList<String> data;
    private String err_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_register);
        data = new ArrayList<>();
    }

    public void switch_to_ProfMain(View view){

            if(render_data() == null)
                System.out.println(err_msg);
            else {
                loadDummyData();
                this.finish();
                startActivity(new Intent(ProfessorRegister.this, ProfMain.class));
            }
    }
    /*
     * ProfData render_data()
     *   This function relays errors into the error text field, if the input is valid the data is placed inside the ProfData Object
     *
     */

    public ProfData render_data(){

        if( collect_data() == 1 ) {
            ((TextView) findViewById(R.id.output_error_txt)).setText(err_msg);
            return null;
        }
        return new ProfData(data.get(0),data.get(1),data.get(2),data.get(3));
    }
    /*
    *
    * int collect_data()
    *   The following function collects all data from the text fields from Activity_professor_register.xml
    *   than checks for errors in the input if one is found err_msg is filled
    *
     */
    public int collect_data(){
        data.clear();

        String val = ((EditText) findViewById(R.id.editText_name_prof_reg)).getText().toString();
        data.add(val);
        err_msg = new String();
        if(val.length() > 256)
            err_msg += "Name isn't valid.\n\n";
        if(val.split("[0-9]").length > 1)
            err_msg += "Name isn't valid.\n\n";
        if(val.length() == 0)
            err_msg += "Name is blank.\n\n";

        if(err_msg.length() > 0)
            return 1;

        val = ((EditText) findViewById(R.id.editText_email_address_prof_reg)).getText().toString();
        data.add(val);

        if(val.length() > 256)
            err_msg += "Email isn't valid.\n\n";
        if(!val.contains("@"))
            err_msg += "Email isn't valid.\n\n";
        if(val.length() == 0)
            err_msg += "Email is blank.\n\n";

        if(err_msg.length() > 0)
            return 1;


        val = ((EditText) findViewById(R.id.editText_password_prof_reg)).getText().toString();
        data.add(val);

        if(val.length() > 256)
            err_msg += "Is to long.\n\n";
        if(val.length() == 0)
            err_msg += "Password is blank.\n\n";

        if(err_msg.length() > 0)
            return 1;


        val = ((EditText) findViewById(R.id.editText_university_prof_reg)).getText().toString();
        data.add(val);

        if(val.length() > 256)
            err_msg += "University isn't valid, university is too long.\n\n";
        else if(val.split("[0-9]").length > 1)
            err_msg += "University isn't valid, contains an number.\n\n";
        if(val.length() == 0)
            err_msg += "University is blank.\n\n";

        if(err_msg.length() == 0)
            return 0;
        else
            return 1;
    }
    public void  loadDummyData() {

        // Loading dummy data into Questions
        ArrayList<String> choices;
        String description;
        int mcanswer;
        Question q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15;
        description = "Which wave causes the most damage to buildings?";
        choices = new ArrayList<>(Arrays.asList("love waves", "red waves", "primary waves", "secondary waves"));
        mcanswer = 0;
        q1 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, 0,0,null, null, 0, null, null, 0);

        choices = new ArrayList<>(Arrays.asList("the biosphere", "the geosphere", "the hydrosphere", "the atmosphere"));
        description = "What subsystem or sphere represents the solid rock of the Earth?";
        mcanswer = 1;
        q2 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "A __________ studies the origin of Earth's landscapes.";
        choices = new ArrayList<>(Arrays.asList("hydrologist", "paleontologist", "structural geologist", "geomorphologist"));
        mcanswer = 3;
        q3 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "The __________ includes organisms living on Earth and their environments.";
        choices = new ArrayList<>(Arrays.asList("the atmosphere", "the geosphere", "the hydrosphere", "the biosphere"));
        mcanswer = 3;
        q4 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "What part of the Earth is thought to be composed of iron and nickel?";
        choices = new ArrayList<>(Arrays.asList("the mantle", "the crust", "the core", "the lithosphere"));
        mcanswer = 2;
        q5 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        choices = new ArrayList<>(Arrays.asList("about 100 million years old", "4.567 billion years old", "about 6,000 years old", "as old as the hills and twice as dusty"));
        description = "Geologists generally agree that the Earth is __________";
        mcanswer = 1;
        q6 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "Earth's external heat engine is driven by what source of energy?";
        choices = new ArrayList<>(Arrays.asList("solar power", "coal", "natural gas", "petroleum"));
        mcanswer = 0;
        q7 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "The most voluminous portion of the Earth is known to geologists as _____.";
        choices = new ArrayList<>(Arrays.asList("the lithosphere", "the mantle", "the core", "the crust"));
        mcanswer = 1;
        q8 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "Which of the following Earth processes and landscapes are related to slow rates of change?";
        choices = new ArrayList<>(Arrays.asList("volcano erupting down a mountain side", "erosion of a canyon", "earthquake in California"));
        mcanswer = 1;
        q9 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "Which type of mining is more efficient and less expensive?";
        choices = new ArrayList<>(Arrays.asList("surface mining", "shaft mining", "borehole mining", "underground mining"));
        mcanswer = 0;
        q10 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "What is the most fundamental resource derived from our planet Earth?";
        choices = new ArrayList<>(Arrays.asList("steel", "gold", "water", "titanium"));
        mcanswer = 2;
        q11 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "Which internal processes are responsible for plate movements of the Earth?";
        choices = new ArrayList<>(Arrays.asList("convection currents", "subduction currents", "conduction currents", "heat currents"));
        mcanswer = 0;
        q12 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "Which step comes first in a scientific method?";
        choices = new ArrayList<>(Arrays.asList("gather evidence", "test hypothesis", "develop theory", "observation"));
        mcanswer = 3;
        q13 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "In 1912, a German meteorologist named _____________ began lecturing and writing scientific papers about continental drift?";
        choices = new ArrayList<>(Arrays.asList("James Hutton", "Alfred Wegener", "Charles Lyell", "Harry Hess"));
        mcanswer = 1;
        q14 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        description = "What fossil plant evidence supports the continental drift theory?";
        choices = new ArrayList<>(Arrays.asList("Glassopteris", "Lycophyta", "Ginkgo", "Equisetum"));
        mcanswer = 0;
        q15 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);

        ArrayList<Question> questions = new ArrayList<>();
        questions.add(q1);
        questions.add(q2);
        questions.add(q3);
        questions.add(q4);
        questions.add(q5);
        questions.add(q6);
        questions.add(q7);
        questions.add(q8);
        questions.add(q9);
        questions.add(q10);
        questions.add(q11);
        questions.add(q12);
        questions.add(q13);
        questions.add(q14);
        questions.add(q15);
        for (int i = 0; i < questions.size(); i++){
            q1 = questions.get(i);
            q1.setQuestionnumber(i+1);
            questions.set(i, q1);
        }

        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("John", "jfebedai12@gmail.com", questions));
        students.add(new Student("Mary", "mhill@gmail.com", questions));
        students.add(new Student("Wendy", "wtulip23@gmail.com", questions));
        students.add(new Student("Edward", "edwhoff3@gmail.com", questions));
        students.add(new Student("Kyle", "kmitchel93@gmail.com", questions));
        students.add(new Student("Emily", "ehanson853@gmail.com", questions));
        students.add(new Student("Andrew", "adijkstra213@gmail.com", questions));
        students.add(new Student("Molly", "meverson425@gmail.com", questions));
        students.add(new Student("Justin", "jmanning554@gmail.com", questions));
        students.add(new Student("Becky", "btellys239@gmail.com", questions));

        Course Geology = new Course("", "Kimberly Meehan", 10323, questions, students);

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(Geology);


        //name = StudentData.getName();
        //email = StudentData.getEmail();
        //password = StudentData.getPassword();

        //StudentData.setCourses(courses);
        //StudentData.setCurrentcourse(courses.indexOf(Geology));
        //StudentData.setName(name);
        //StudentData.setEmail(email);
        //StudentData.setPassword(password);


        ProfData.setCourses(courses);
        ProfData.setCurrentcourse(courses.indexOf(Geology));
        ProfData.setName(data.get(0));
        ProfData.setEmail(data.get(1));
        ProfData.setPassword(data.get(2));
    }
}
