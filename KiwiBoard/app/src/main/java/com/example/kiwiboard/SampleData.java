package com.example.kiwiboard;

import java.util.ArrayList;
import java.util.Arrays;

public class SampleData {

    private ArrayList<Question> GeologyQuestions = new ArrayList<>();
    private ArrayList<Student> GeologyStudents = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();

    public void SampleData(){
        // Nothing to construct
    }

    // Adds all sample courses to a list
    private void loadSampleCourses(){
        courses.clear();
        courses.add(Geology());
        // Add additional courses here
    }


    public void loadProfCourses(){
        loadSampleCourses();
        ProfData.setCourses(courses);
    }

    public void loadProfInfo(){
        ProfData.setName("Kimberly Meehan");
        ProfData.setEmail("kmeehan234@gmail.com");
        ProfData.setPassword("iamaprofessor2");
    }

    public void loadStudentCourses(){
        loadSampleCourses();
        StudentData.setCourses(courses);
    }

    public void loadStudentInfo(){
        StudentData.setName("Johnson");
        StudentData.setEmail("jgohe293@gmail.com");
        StudentData.setPassword("hyperGeometric392");
    }

    // Sample Courses
    public Course Geology() {
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
        //q14.setActive(true);

        description = "What is albedo?";
        String profAnswer = "Albedo is a measure of how much light that hits a surface is reflected without being absorbed.";
        q15 = new Question(Question.QuestionType.SHORTANSWER, description, null, -1, 10,10, 0,0,profAnswer, null, 0, null, null, 0);
        //q15.setActive(true);

        GeologyQuestions.add(q1);
        GeologyQuestions.add(q2);
        GeologyQuestions.add(q3);
        GeologyQuestions.add(q4);
        GeologyQuestions.add(q5);
        GeologyQuestions.add(q6);
        GeologyQuestions.add(q7);
        GeologyQuestions.add(q8);
        GeologyQuestions.add(q9);
        GeologyQuestions.add(q10);
        GeologyQuestions.add(q11);
        GeologyQuestions.add(q12);
        GeologyQuestions.add(q13);
        GeologyQuestions.add(q14);
        GeologyQuestions.add(q15);
        for (int i = 0; i < GeologyQuestions.size(); i++){
            q1 = GeologyQuestions.get(i);
            q1.setQuestionnumber(i+1);
            GeologyQuestions.set(i, q1);
        }

        GeologyStudents.add(new Student("John", "jfebedai12@gmail.com", 0, GeologyQuestions));
        GeologyStudents.add(new Student("Mary", "mhill@gmail.com", 1,GeologyQuestions));
        GeologyStudents.add(new Student("Wendy", "wtulip23@gmail.com", 2,GeologyQuestions));
        GeologyStudents.add(new Student("Edward", "edwhoff3@gmail.com", 3,GeologyQuestions));
        GeologyStudents.add(new Student("Kyle", "kmitchel93@gmail.com", 4,GeologyQuestions));
        GeologyStudents.add(new Student("Emily", "ehanson853@gmail.com", 5,GeologyQuestions));
        GeologyStudents.add(new Student("Andrew", "adijkstra213@gmail.com", 6,GeologyQuestions));
        GeologyStudents.add(new Student("Molly", "meverson425@gmail.com", 7,GeologyQuestions));
        GeologyStudents.add(new Student("Justin", "jmanning554@gmail.com", 8,GeologyQuestions));
        GeologyStudents.add(new Student("Becky", "btellys239@gmail.com", 9,GeologyQuestions));

        Course Geology = new Course("Geology", "Kimberly Meehan", 10323, GeologyQuestions, GeologyStudents);
        Geology.setDescription("Geology is the primary Earth science and looks at how the earth formed, its structure and composition, and the types of processes acting on it.");

        // Sample course resources

        Geology.addUrl("https://geology.com/");
        Geology.addSite("Geology.com");

        Geology.addUrl("https://www.geosociety.org/gsa/pubs/geology/home.aspx");
        Geology.addSite("GeoSociety.com");

        Geology.addUrl("https://pubs.geoscienceworld.org/geology");
        Geology.addSite("GeoScience World");

        Geology.addUrl("https://www.britannica.com/science/geology/Study-of-the-composition-of-the-Earth");
        Geology.addSite("ENCYCLOPEDIA BRITANNICA");

        // Sample queue questions
        Question q16, q17;

        description = "Why does the water cycle flow in different orders on different landscapes?";
        q16 = new Question(Question.QuestionType.SHORTANSWER, description, null, -1, 10,10, 0,0,null, null, 0, null, null, 0);
        q16.setTextanswer("YAY it works!!");
        q16.setInQueue(true);

        description = "The color of a mineral in powdered form is termed ______";
        choices = new ArrayList<>(Arrays.asList("Color", "Streak", "Specific Gravity", "Sulfides"));
        mcanswer = 1;
        q17 = new Question(Question.QuestionType.MULTIPLECHOICE, description, choices, -1, 10,10, mcanswer,0,null, null, 0, null, null, 0);
        q17.setInQueue(true);

        Geology.addQueueQuestion(q16);
        Geology.addQueueQuestion(q17);

        return Geology;
    }
}
