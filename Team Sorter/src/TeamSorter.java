package src;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamSorter {

    //counters for how many of each priority were given
    static int count1 = 0;
    static int count2 = 0;
    static int count3 = 0;
    static int count4 = 0;
    static int count5 = 0;
    static int previousSum = 0;
    static int previousCountLess = 0;

    //the array lists containing the project information and the student information
    static List<Project> projectList = new ArrayList();
    static List<Person> studentList = new ArrayList();
    //list of extra students
    static List<Person> extraList = new ArrayList<>();
    //static counter for how many projects the student has listed
    private static final int studentProjectCounter = 5;
    //the counter for how many projects havn't been fulfilled
    private static int countLessThanMinimum = -1;

    //main method
    public static void main(String[]args) {
        for(int x = 0; x < 100000; x++) {
            countLessThanMinimum = -1;
            count1 = 0;
            count2 = 0;
            count3 = 0;
            count4 = 0;
            count5 = 0;
            //reads the project list
            readProjectList();
            //reads the student list
            readStudentDescription();
            //places the students into their desired project based on project requirements and
            placeStudents();
            projectList = new ArrayList<>();
            studentList = new ArrayList<>();
        }
        //prints out the project information with the students in the projects
        //printProjectInformation();
    }

    //sums up all the counts for the best project
    private static int sumCounts(){
        return (count1*5)+(count2*4)+(count3*3)+(count4*2)+(count5);
    }

    //writes up all the counts into the global variables
    private static void writeCounts(){
        for(Person person: studentList){
            List list = person.returnProjectList();
            //for whichever priority the person's current project is, increase that counter to get the count of how many
            //of each priority students got
            int index = list.indexOf(person.getCurrentProject());
            switch(index){
                case 0:
                    count1++;
                    break;
                case 1:
                    count2++;
                    break;
                case 2:
                    count3++;
                    break;
                case 3:
                    count4++;
                    break;
                case 4:
                    count5++;
                    break;
            }
        }
    }

    //writes the information to the textFile
    private static void writeToTextFile(){
        //opens the PrintWriter to make a new textfile which will hold all of the information about students
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("project_teams.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //prints out the project and the disciplines attatched to it
        for(Project project: projectList){
            writer.print(project.projectNumber + " ");
            //System.out.println(project.peopleList.size());
            for(Person student: project.getPeopleList()){
                writer.print(student.getName() + ", ");
            }
            writer.println();
        }

        //counters for how many of each priority were given
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;

        //prints out the project and the disciplines attatched to it
        writer.println("\n");
        for(Person person: studentList){
            writer.print(person.getName() + " ");
            List list = person.returnProjectList();
            //for whichever priority the person's current project is, increase that counter to get the count of how many
            //of each priority students got
            int index = list.indexOf(person.getCurrentProject());
            switch(index){
                case 0:
                    count1++;
                    break;
                case 1:
                    count2++;
                    break;
                case 2:
                    count3++;
                    break;
                case 3:
                    count4++;
                    break;
                case 4:
                    count5++;
                    break;
            }
            writer.print(index + 1 + "") ;
            writer.println();
        }
        //prints out the priority counts and then the projects with less than the minimum members
        writer.println("count 1: " + count1);
        writer.println("count 2: " + count2);
        writer.println("count 3: " + count3);
        writer.println("count 4: " + count4);
        writer.println("count 5: " + count5);
        writer.println("Projects With Less Than 3: " + countLessThanMinimum);
        writer.close();
    }
    //method used to put people into the correct projects with every project filled
    private static void placeStudents(){
        //Method 2

        //used to give the students extra majors for the projects that they are allowed to do.
        for(Person student: studentList){
            List<String> studentMajorList = student.getMajor();
            switch(studentMajorList.get(0)){
                case "CS":
                    studentMajorList.add("CE");
                    break;
                case "CE":
                    studentMajorList.add("CS");
                    studentMajorList.add("EE");
                    break;
                case "EE":
                    studentMajorList.add("MCE");
                    break;
                case "MCE":
                    studentMajorList.add("EE");
                    break;
            }
            student.setMajor(studentMajorList);
        }

        //puts the students in their first project
        for(int x = 0; x < studentList.size(); x++){
            Person student = studentList.get(x);
            List<Project> studentProjectList = student.returnProjectList();
            for(int y = 0; y < studentProjectList.size(); y++) {
                if (!studentProjectList.get(y).majors.contains(student.getMajor().get(0))) {
                    studentProjectList.remove(studentProjectList.get(y));
                    y--;
                }
            }
            //Person student = studentList.get(x);
            //Project project = student.returnProjectList().get(0);
            //project.addPerson(student);
            //student.setCurrentProject(project);
        }

        //counts how many projects have less than 3
        //while(countLessThanMinimum != 1) {
        //for(int countx = 0; countx < 10; countx++) {
            countLessThanMinimum = 0;
            //for each project that does not have enough students, search the students for the ones which fit the best
            for (int priority = 0; priority < studentProjectCounter; priority++) {
                for (int count = 0; count < 3; count++) {
                    for (int x = 0; x < projectList.size(); x++) {
                        Project currentProject = projectList.get(x);
                        int projectsize = currentProject.peopleList.size();
                        //if the project is not filled
                        if (projectsize < 3) {
                            //randomely shuffles the student list before iterating through the list
                            Collections.shuffle(studentList);
                            //search through students who have this project on their list
                            for (int y = 0; y < studentList.size(); y++) {
                                Person student = studentList.get(y);
                                List<String> majorList = student.getMajor();
                                boolean nextStudent = true;
                                for (int majorCount = 0; majorCount < majorList.size(); majorCount++) {
                                    if (currentProject.majors.contains(majorList.get(majorCount))) {
                                        nextStudent = false;
                                        break;
                                    }
                                }
                                if (nextStudent) {
                                    continue;
                                }
                                if (student.returnProjectList().size() > priority) {
                                    if (student.getCurrentProject() != currentProject && student.returnProjectList().get(priority) == currentProject) {
                                        //adds the person to the current project and removes them from their previous one
                                        currentProject.addPerson(student);
                                        if (student.getCurrentProject() != null) {
                                            student.getCurrentProject().removePerson(student);
                                        }
                                        //adds the project as the student's current project
                                        student.setCurrentProject(currentProject);
                                        //make sure that once the projectsize counter reaches 3 then go on to the next project
                                        projectsize++;
                                        if (!(projectsize < 3)) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //prints out the project and the disciplines attatched to it
            for(Project project: projectList) {
                if (project.getPeopleList().size() < 3) {
                    countLessThanMinimum++;
                }
            }
        //}

        //writes it to a textFile
        writeCounts();
        if(previousCountLess > countLessThanMinimum) {
            writeToTextFile();
            previousSum = sumCounts();
            previousCountLess = countLessThanMinimum;
        }else if(previousCountLess == countLessThanMinimum && previousSum < sumCounts()){
            writeToTextFile();
            previousSum = sumCounts();
        }
    }

    //method used to print out the information that we want to look at from the projects
    private static void printProjectInformation(){

        //prints out the project and the disciplines attatched to it
        for(Project project: projectList){
            System.out.print(project.projectNumber + " ");
            System.out.print(project.majors.toString() + " ");
            //System.out.println(project.peopleList.size());
            for(Person student: project.getPeopleList()){
                System.out.print(student.getName() + ", ");
            }
            System.out.println();
        }

        //prints out the project and the disciplines attatched to it
        System.out.println("\n");
        /*for(Person person: studentList){
            System.out.print(person.getName() + " ");
            List list = person.returnProjectList();
            //for whichever priority the person's current project is, increase that counter to get the count of how many
            //of each priority students got
            int index = list.indexOf(person.getCurrentProject());
            switch(index){
                case 0:
                    count1++;
                    break;
                case 1:
                    count2++;
                    break;
                case 2:
                    count3++;
                    break;
                case 3:
                    count4++;
                    break;
                case 4:
                    count5++;
                    break;
            }
            System.out.print(index + 1 + "") ;
            System.out.println();
        }*/
        //prints out the priority counts and then the projects with less than the minimum members
        System.out.println("count 1: " + count1);
        System.out.println("count 2: " + count2);
        System.out.println("count 3: " + count3);
        System.out.println("count 4: " + count4);
        System.out.println("count 5: " + count5);
        System.out.println("Projects With Less Than 3: " + countLessThanMinimum);
    }

    //method used to populate the project list by reading the project description text
    private static void readProjectList(){
        //reads the project description file and populates the project list
        try (BufferedReader br = new BufferedReader(new FileReader("projectdescriptions.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = "";

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                //if the line is not null and the line is not switching to the next time of project with a "."
                if(line != null && !line.equals(".")){
                    //gets the project number and the list of majors
                    int projectNumber = Integer.parseInt( line.substring(0, line.indexOf('-')-1) );
                    String majors = line.substring(line.indexOf('-')+2);
                    //creates a new Project and adds it to the project list
                    projectList.add( new Project(projectNumber, convertMajorList(majors)) );
                }
            }
            //String everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method used to populate the people list by reading the student description text
    private static void readStudentDescription(){
        //reads the project description file and populates the project list
        try (BufferedReader br = new BufferedReader(new FileReader("studentdescriptions(2).txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = "";
            //while there is still stuff to read in the textfile
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                //if the line is not null and the line is not switching to the next time of project with a "."
                if(line != null){
                    //gets the project number and the list of majors
                    String studentName = line.substring(0, line.indexOf('-')-1);
                    String studentMajor = line.substring(line.indexOf('-')+2, line.indexOf(':')-1);
                    String studentProjectList = line.substring(line.indexOf(':')+1);
                    //creates a list for future studentMajors
                    List<String> studentMajors = new ArrayList<>();
                    studentMajors.add(studentMajor);
                    //creates a new Project and adds it to the project list
                    studentList.add( new Person( studentName, studentMajors, convertProjectList(studentProjectList) ) );
                }
            }
            //String everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method used to convert the string of major to a list
    private static List convertMajorList(String majors){
        List majorsList = new ArrayList();
        //while there is still content in the string
        while(!majors.isEmpty()){
            //if it is a multidisciplinary project
            if(majors.contains("/")) {
                String firstmajor = majors.substring(0, majors.indexOf("/"));
                majors = majors.substring(majors.indexOf("/")+1);
                majorsList.add(firstmajor);
            }
            //if there is only one disciplne left
            else{
                majorsList.add(majors);
                majors = "";
            }
        }
        //return the list of disciplines
        return majorsList;
    }

    //method used to convert the string of projectList to a list
    private static List convertProjectList(String projects){
        List convertedList = new ArrayList();
        //while there is still content in the string
        while(!projects.isEmpty()){
            //for the first 4 projects in the list of projects
            if(projects.contains(",")) {
                String firstproject = projects.substring(0, projects.indexOf(","));
                projects = projects.substring(projects.indexOf(",")+1);
                convertedList.add(projectList.get( (Integer.parseInt(firstproject))-1 ) );
            }
            //if there is only one project left
            else{
                convertedList.add(projectList.get( (Integer.parseInt(projects))-1 ) );
                projects = "";
            }
        }
        //return the list of disciplines
        return convertedList;
    }
}
