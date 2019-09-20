package src;

import java.util.ArrayList;
import java.util.List;

public class Project {

    //list that holds the people on a project
    List peopleList;
    //project number
    int projectNumber;
    List<String> majors;

    //constructor that is called when a Project object is created
    Project(){
        peopleList = new ArrayList();
        majors = new ArrayList();
    }

    Project(int projectNum, List majorList){
        peopleList = new ArrayList();
        majors = new ArrayList();
        //declares the variables
        projectNumber = projectNum;
        majors = majorList;
    }

    public void addPerson(String personName){
        peopleList.add(personName);
    }

    //gets the list of people
    public List getPeopleList(){
        return peopleList;
    }
}
