package com.example.kiwiboard;

import java.util.ArrayList;

class Course {
    private String courseName;                  // Name of course
    private String instructorname;              // Name of instructor
    private String ID;                          // ID of course
    private int classKey;                       // Class key
    private String description;                 // Class description
    private ArrayList<Question> questions;      // Question list
    private ArrayList<Question> queue;          // Question queue
    private ArrayList<Student> students;        // Student list. Null for students.
    private ArrayList<String> urls;             // Useful link url
    private ArrayList<String> sitenames;         // Useful link sitename


    // Default constructor with parameters
    public Course(String courseName, String instructorname, int classKey, ArrayList<Question> questions, ArrayList<Student> students) {
        this.courseName = courseName;
        this.instructorname = instructorname;
        this.classKey = classKey;
        this.description = "";
        this.questions = questions;
        this.queue = new ArrayList<>();
        this.students = new ArrayList<>();
        this.urls = new ArrayList<>();
        this.sitenames = new ArrayList<>();
    }

    // Secondary constructor
    public Course(String courseName, String instructorname, String description) {
        this.courseName = courseName;
        this.instructorname = instructorname;
        this.description = description;
        this.questions = new ArrayList<>();;
        this.queue = new ArrayList<>();
        this.students = new ArrayList<>();
        this.urls = new ArrayList<>();
        this.sitenames = new ArrayList<>();
    }

    // Adds a new question from an existing Question object
    public void addQuestion(Question question){
        question.setQuestionnumber(questions.size());
        questions.add(question);
    }

    // Adds a new question using Question's default constructor with parameters
    public void addNewQuestion(Question.QuestionType type, String description, ArrayList<String> choices, int questionnumber, double pointsreceived, int maxpoints, int mcanswer, double numericanswer, String textanswer, ArrayList<Integer> multipleanswers, int mcresponse, ArrayList<Integer> multipleresponses, String textresponse, double numericresponse){
        questions.add(new Question(type, description, choices, questionnumber, pointsreceived, maxpoints, mcanswer, numericanswer, textanswer, multipleanswers, mcresponse, multipleresponses, textresponse, numericresponse));
    }

    // Set the question at the index given
    public void setQuestion(int index, Question question){
        questions.set(index, question);
    }

    // Get the question at the index given
    public Question getQuestion(int index){
        return questions.get(index);
    }

    // Removes a question at a particular index. Indices start at 0
    public void removeQuestion(int index){
        questions.remove(index);
    }

    public void assignQuestionNumbers(){
        Question q;
        for (int i = 0; i < questions.size(); i++){
            q = questions.get(i);
            q.setQuestionnumber(i+1);
            questions.set(i, q);
        }
    }

    // Adds a new question from an existing Question object
    public void addQueueQuestion(Question question){
        if (queue.isEmpty()){
            question.setQuestionnumber(0);
        } else {
            question.setQuestionnumber(queue.size());
        }
        question.setInQueue(true);
        question.setActive(false);
        queue.add(question);
    }
    // Get the queue question at the index given
    public Question getQueueQuestion(int index){
        return queue.get(index);
    }

    // Set the queue question at the index given
    public void setQueueQuestion(int index, Question question){
        queue.set(index, question);
    }

    // Adds a new question using Question's default constructor with parameters
    public void addNewQueueQuestion(Question.QuestionType type, String description, ArrayList<String> choices, int questionnumber, double pointsreceived, int maxpoints, int mcanswer, double numericanswer, String textanswer, ArrayList<Integer> multipleanswers, int mcresponse, ArrayList<Integer> multipleresponses, String textresponse, double numericresponse){
        Question question = new Question(type, description, choices, questionnumber, pointsreceived, maxpoints, mcanswer, numericanswer, textanswer, multipleanswers, mcresponse, multipleresponses, textresponse, numericresponse);
        question.setInQueue(true);
        question.setActive(false);
        if (queue.isEmpty()){
            question.setQuestionnumber(0);
        } else {
            question.setQuestionnumber(queue.size());
        }
        queue.add(question);
    }

    // Removes a question at a particular index. Indices start at 0
    public void removeQueueQuestion(int index){
        queue.remove(index);
    }


    public void refreshQuestionNumbers(){
        Question question;
        for(int i = 0; i < questions.size(); i++){
            question = questions.get(i);
            question.setQuestionnumber(i+1);
            questions.set(i, question);
        }
        for(int i = 0; i < queue.size(); i++){
            question = queue.get(i);
            question.setQuestionnumber(i+1);
            queue.set(i, question);
        }
    }

    public ArrayList<Double> calculateAverages() {
        ArrayList<Double>averages = new ArrayList<>();

        int numQuestions = questions.size();
        int numStudents = students.size();
        double sum, grade, avg, total;
        Question question;
        Student student;

        for(int i=0;i < numQuestions;i++)
        {
            sum=0;
            for(int j=0;j<numStudents;j++)
            {
                student=students.get(j);
                question=student.getQuestions().get(i);
                grade=question.calculateScore();
                sum+=grade;
            }
            total=(questions.get(i).getMaxpoints())*numStudents;
            if(total==0)
            {
                avg = 0;
            }
            avg=sum/total;
            averages.add(avg);
        }
        return averages;
    }

    public double calculateClassAverage(){
        double sum=0,maxSum=0;
        int numQuestions=questions.size();
        ArrayList<Double>averages=calculateAverages();
        for(int i=0;i<numQuestions;i++)
        {
            sum+=averages.get(i);
            maxSum+=questions.get(i).getMaxpoints();
        }
        return (sum/maxSum)*1000;
    }

    void setUrls(ArrayList<String> urls){ this.urls = urls;}
    ArrayList<String> getUrls(){return urls;}
    void setSitenames(ArrayList<String> sitenames){ this.sitenames = sitenames; }
    ArrayList<String> getSitenames(){return sitenames;}

    public void addUrl(String url){ urls.add(url);};
    public void setUrl(int index, String url){ urls.add(index, url); };
    public String getURL(int index){ return urls.get(index); };
    public void removeUrl(int index){ urls.remove(index);};

    public void addSite(String site){sitenames.add(site);}
    public void setSite(int index, String site) {sitenames.add(index,site);}
    public String getSite(int index){return sitenames.get(index);}
    public void removeSite(int index){ sitenames.remove(index);}
    
    // Adds a new student from an existing Student object
    public void addStudent(Student student){
        students.add(student);
    }

    // Remove a new student from an existing Student object
    public void removeStudent(int num){
        students.remove(num);
    }

    // Adds a new student using Student's default constructor with parameters
    public void addNewStudent(Question.QuestionType type, String description, ArrayList<String> choices, int questionnumber, double pointsreceived, int maxpoints, int mcanswer, double numericanswer, String textanswer, ArrayList<Integer> multipleanswers, int mcresponse, ArrayList<Integer> multipleresponses, String textresponse, double numericresponse){
        queue.add(new Question(type, description, choices, questionnumber, pointsreceived, maxpoints, mcanswer, numericanswer, textanswer, multipleanswers, mcresponse, multipleresponses, textresponse, numericresponse));
    }

    // Set the student at the index given
    public void setStudent(int index, Student student){
        students.set(index, student);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getInstructorname() {
        return instructorname;
    }

    public void setInstructorname(String instructorname) {
        this.instructorname = instructorname;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getClassKey() {
        return classKey;
    }

    public void setClassKey(int classKey) {
        this.classKey = classKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> Students) {
        this.students = Students;
    }

    public int getNumberOfStudents(){
      return students.size();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<Question> getQueue() {
        return queue;
    }

    public void setQueue(ArrayList<Question> queue) {
        this.queue = queue;
    }
}
