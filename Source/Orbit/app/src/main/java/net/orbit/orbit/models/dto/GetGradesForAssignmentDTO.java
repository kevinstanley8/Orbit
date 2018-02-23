package net.orbit.orbit.models.dto;

/**
 * Created by brocktubre on 12/25/17.
 */

public class GetGradesForAssignmentDTO {
    private int courseID;
    private int assignmentID;

    public GetGradesForAssignmentDTO(int courseID, int assignmentID) {
        this.courseID = courseID;
        this.assignmentID = assignmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getAssignmentID() {
        return assignmentID;
    }

    public void setAssignmentID(int assignmentID) {
        this.assignmentID = assignmentID;
    }

    @Override
    public String toString() {
        return "GetGradesForAssignmentDTO{" +
                "courseID='" + courseID + '\'' +
                ", assignmentID='" + assignmentID + '\'' +
                '}';
    }

}
