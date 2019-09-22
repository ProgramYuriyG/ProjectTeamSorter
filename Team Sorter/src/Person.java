package src;

import java.util.List;

public class Person{
    //variables
    String name;
    private List<String> major;
    List<Project> projectList;
    Project currentProject;

    //constructor with name, major, and project list
    Person(String name, List<String> major, List<Project> projectList){
        this.name = name;
        this.major = major;
        this.projectList = projectList;
    }

    public String getName(){
        return name;
    }

    public List<String> getMajor(){
        return major;
    }

    public void setMajor(List<String> majorList){
        major = majorList;
    }

    //sets the person's current project
    public void setCurrentProject(Project project){
        currentProject = project;
    }

    //gets the person's current project
    public Project getCurrentProject(){
        return currentProject;
    }

    public List<Project> returnProjectList(){
        return projectList;
    }
}
