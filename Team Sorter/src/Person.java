package src;

import java.util.List;

public class Person{
    //variables
    String name;
    String major;
    List<Integer> projectList;

    //constructor with name, major, and project list
    Person(String name, String major, List<Integer> projectList){
        this.name = name;
        this.major = major;
        this.projectList = projectList;
    }

    public String getName(){
        return name;
    }

    public String getMajor(){
        return major;
    }

    public List<Integer> returnProjectList(){
        return projectList;
    }
}
