package src;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TeamSorter {

    static List<Project> projectList = new ArrayList();
    static List<Person> studentList = new ArrayList();

    //main method
    public static void main(String[]args) {
        //reads the project list
        readProjectList();
        //reads the student list
        readStudentDescription();

        //puts the students in their first project
        for(int x = 0; x < studentList.size(); x++){
            Person student = studentList.get(x);
            int projectNumber = student.returnProjectList().get(0);
            projectList.get(projectNumber-1).addPerson(student.getName());
        }

        //prints out the project and the disciplines attatched to it
        for(Project project: projectList){
            System.out.print(project.projectNumber + " ");
            for(Object student: project.peopleList){
                System.out.print(student + " ");
            }
            System.out.println();
        }//*/

        /*prints out the project and the disciplines attatched to it
        System.out.println("\n");
        for(Person person: studentList){
            System.out.print(person.getName() + " ");
            System.out.print(person.getMajor() + " ");
            for(int projectNum: person.returnProjectList()){
                System.out.print(projectNum + " ");
            }
            System.out.println();
        }*/
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
                    studentList.add( new Person( studentName, studentMajor, projectList(studentProjectList) ) );
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
    private static List projectList(String projects){
        List projectList = new ArrayList();
        //while there is still content in the string
        while(!projects.isEmpty()){
            //for the first 4 projects in the list of projects
            if(projects.contains(",")) {
                String firstproject = projects.substring(0, projects.indexOf(","));
                projects = projects.substring(projects.indexOf(",")+1);
                projectList.add(Integer.parseInt(firstproject));
            }
            //if there is only one project left
            else{
                projectList.add(Integer.parseInt(projects));
                projects = "";
            }
        }
        //return the list of disciplines
        return projectList;
    }
}
