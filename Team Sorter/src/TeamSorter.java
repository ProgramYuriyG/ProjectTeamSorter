import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class TeamSorter {

    static List<Project> projectList = new ArrayList();

    //main method
    public static void main(String[]args) {
        //creates
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
                    String majors = line.substring(line.indexOf('-')+1);
                    //creates a new Project and adds it to the project list
                    projectList.add( new Project(projectNumber, majorList(majors)) );
                }
            }
            //String everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //prints out the project and the disciplines attatched to it
        for(Project project: projectList){
            System.out.print(project.projectNumber + " ");
            for(String s: project.majors){
                System.out.print(s + " ");
            }
            System.out.println();
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
}
