package com.example.kiwiboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfRegistration extends AppCompatActivity {
    private ArrayList<String> data;
    private String err_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_registration);
        data = new ArrayList<>();
    }

    public void switch_to_ProfMain(View view){

            if(render_data() == null)
                System.out.println(err_msg);
            else {
                createUser();
                this.finish();
                ProfData.setProfessormode(true); // Activate professor mode
                startActivity(new Intent(ProfRegistration.this, ProfMain.class));
            }
    }

    public void createUser() {
        // Set data fields
        ProfData.setName(data.get(0));
        ProfData.setEmail(data.get(1));
        ProfData.setPassword(data.get(2));
    }

    // This function relays errors into the error text field, if the input is valid the data is placed inside the ProfData Object
    public ProfData render_data(){

        if( collect_data() == 1 ) {
            ((TextView) findViewById(R.id.output_error_txt)).setText(err_msg);
            return null;
        }
        return new ProfData(data.get(0),data.get(1),data.get(2),data.get(3));
    }

    /* The following function collects all data from the text fields from Activity_professor_register.xml
     *  then checks for errors in the input if one is found err_msg is filled
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
}
