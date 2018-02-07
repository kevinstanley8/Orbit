package net.orbit.orbit.models.pojo;

/**
 * Created by Kevin Stanley on 2/6/2018.
 */

public class Assignment {
    private int assignmentId;
    private String year;
    private String name;
    private String type;
    private String maxPoints;
    private int courseId;
    private Boolean isSelected = false;

    public Assignment()
    {

    }

    public Assignment(int assignmentId, String year, String name, String type, String maxPoints, int courseId)
    {
        this.assignmentId = assignmentId;
        this.year = year;
        this.name = name;
        this.type = type;
        this.maxPoints = maxPoints;
        this.courseId = courseId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(String maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Assignment [assignmentId=" + assignmentId + ", year=" + year + ", name=" + name + ", type=" + type + ", maxPoints=" + maxPoints + ", courseId=" + courseId + "]";
    }
}
