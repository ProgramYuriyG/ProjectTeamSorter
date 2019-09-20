package src;

import java.util.ArrayList;
import java.util.List;

public class Project {

    //list that holds the people on a project
    List<Person> peopleList;
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

    public void addPerson(Person person){
        peopleList.add(person);
    }

    //removes the person from the list
    public void removePerson(Person person){
        peopleList.remove(person);
    }
    //gets the list of people
    public List<Person> getPeopleList(){
        return peopleList;
    }
}
