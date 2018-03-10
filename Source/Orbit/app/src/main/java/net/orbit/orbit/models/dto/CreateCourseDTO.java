package net.orbit.orbit.models.dto;

/**
 * Created by brocktubre on 12/25/17.
 */

public class CreateCourseDTO {
    private int teacherID;
    private String name;

    public CreateCourseDTO(int teacherID, String name) {
        this.teacherID = teacherID;
        this.name = name;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateCourseDTO{" +
                "teacherID='" + teacherID + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
