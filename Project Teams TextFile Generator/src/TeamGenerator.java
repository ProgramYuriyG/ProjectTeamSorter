package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamGenerator {

    //creates the static global variables which will hold the amount of people in each discipline
    private final static int CS = 46;
    private final static int MCE = 7;
    private final static int CE = 3;
    private final static int EE = 70;


    //the main method which is executing the program
    public static void main(String[]args){
        //reads the project file, Each line is a seperate project
        //EX: 1 - EE/MCE  ( the 1 is the project number, dash means you're stating disciplines, slash means its multidisciplinary, EE and MCE are the disciplines
        //a dot in the file means that it is switching from Industry Projects -> Faculty Projects -> Student Led Projects
        /** Later implementation: can have a specified number of people */
        //CS(46) MCE(7) CE(3) EE(70)

        //opens the PrintWriter to make a new textfile which will hold all of the information about students
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("studentdescriptions(3).txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //generates the CS student descriptions
        for(int x = 0; x < CS; x++){
            List list = generateList();
            writer.println("Jack Hills"+x + " - CS :" + formatList(list));
        }
        //generates the MCE student descriptions
        for(int x = 0; x < MCE; x++){
            List list = generateList();
            writer.println("Jenny Miller"+x + " - MCE :" + formatList(list));
        }
        //generates the CS student descriptions
        for(int x = 0; x < CE; x++){
            List list = generateList();
            writer.println("Jimmy Jones"+x + " - CE :" + formatList(list));
        }
        //generates the MCE student descriptions
        for(int x = 0; x < EE; x++){
            List list = generateList();
            writer.println("Albert Einher"+x + " - EE :" + formatList(list));
        }

        //closes the writer
        writer.close();
    }

    //method used to format the list to put it into the text file
    private static String formatList(List list){
        String returnString = "";
        for (Object o : list) {
            returnString = returnString.concat(o + ",");
        }
        return returnString.substring(0, returnString.length()-1);
    }

    //method used to generate the list
    private static List generateList(){
        List list = new ArrayList();
        Random rand = new Random();
        for(int y = 0; y < 5; y++){
            int randomNum = rand.nextInt(41)+1;
            while (list.contains(randomNum)){
                randomNum = rand.nextInt(41)+1;
            }
            list.add(randomNum);
        }
        return list;
    }

}
