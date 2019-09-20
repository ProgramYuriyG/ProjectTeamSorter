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
                if(line != null && !line.equals(".")){
                    int projectNumber = Integer.parseInt( line.substring(0, line.indexOf('-')-1) );
                    String majors = line.substring(line.indexOf('-')+1);
                    projectList.add( new Project(projectNumber, majorList(majors)) );
                }
            }
            //String everything = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        while(!majors.isEmpty()){
            if(majors.contains("/")) {
                String firstmajor = majors.substring(0, majors.indexOf("/"));
                majors = majors.substring(majors.indexOf("/")+1);
                majorsList.add(firstmajor);
            }else{
                majorsList.add(majors);
                majors = "";
            }
        }
        return majorsList;
    }
}
