package src;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TeamSorter {

    static List<Project> projectList = new ArrayList();
    static List<Person> studentList = new ArrayList();
    //static counter for how many projects the student has listed
    private static final int studentProjectCounter = 5;

    //main method
    public static void main(String[]args) {
        //reads the project list
        readProjectList();
        //reads the student list
        readStudentDescription();

        //Method 2
        //puts the students in their first project
        for(int x = 0; x < studentList.size(); x++){
            Person student = studentList.get(x);
            Project project = student.returnProjectList().get(0);
            project.addPerson(student);
            student.setCurrentProject(project);
        }
        //for each project that does not have enough students, search the students for the ones which fit the best
        for(int priority = 0; priority < studentProjectCounter; priority++) {
            for (int x = 0; x < projectList.size(); x++) {
                Project currentProject = projectList.get(x);
                int projectsize = currentProject.peopleList.size();
                //if the project is not filled
                if (projectsize < 3) {
                    //search through students who have this project on their list
                    for (int y = 0; y < studentList.size(); y++) {
                        Person student = studentList.get(y);
                        if (student.getCurrentProject() != currentProject && student.returnProjectList().get(priority) == currentProject) {
                            //adds the person to the current project and removes them from their previous one
                            currentProject.addPerson(student);
                            student.getCurrentProject().removePerson(student);
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

        /* Method 1
        //puts the students in their first project
        for(int x = 0; x < studentList.size(); x++){
            Person student = studentList.get(x);
            int projectNumber = student.returnProjectList().get(0);
            projectList.get(projectNumber-1).addPerson(student);
        }
        //if projects are overfilled then put people in their next priority one
        for(int priorityCounter = 1; priorityCounter < studentProjectCounter; priorityCounter++ ) {
            //gets the project list and for each project tries to resort people
            for (int x = 0; x < projectList.size(); x++) {
                Project currentProject = projectList.get(x);
                //if the size of the people in the project is greater than 3
                if (currentProject.getPeopleList().size() > 3) {
                    //check if the person's second option is full, if so
                    for (int y = 0; y < currentProject.getPeopleList().size(); y++) {
                        //gets their second priority project
                        Person currentPerson = currentProject.getPeopleList().get(y);
                        Project nextProj = projectList.get(currentPerson.returnProjectList().get(priorityCounter) - 1);
                        //checks if the project is full and puts them in if it is not
                        if (nextProj.getPeopleList().size() < 4) {
                            nextProj.addPerson(currentPerson);
                            currentProject.removePerson(currentPerson);
                        }
                    }
                }
            }
        }*/

        //prints out the project and the disciplines attatched to it
        for(Project project: projectList){
            System.out.print(project.projectNumber + " ");
            //System.out.println(project.peopleList.size());
            for(Person student: project.getPeopleList()){
                System.out.print(student.getName() + ", ");
            }
            System.out.println();
        }

        //counters for how many of each priority were given
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;

        //prints out the project and the disciplines attatched to it
        System.out.println("\n");
        for(Person person: studentList){
            System.out.print(person.getName() + " ");
            List list = person.returnProjectList();
            int index = person.returnProjectList().indexOf(person.getCurrentProject());
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
        }
        System.out.println("count 1: " + count1);
        System.out.println("count 2: " + count2);
        System.out.println("count 3: " + count3);
        System.out.println("count 4: " + count4);
        System.out.println("count 5: " + count5);
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
                    projectList.add( new Project(projectNumber, majorList(majors)) );
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
        try (BufferedReader br = new BufferedReader(new FileReader("studentdescriptions.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = "";

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
                    //creates a new Project and adds it to the project list
                    studentList.add( new Person( studentName, studentMajor, convertProjectList(studentProjectList) ) );
                }
            }
            //String everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method used to convert the string of major to a list
    private static List majorList(String majors){
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
