package net.orbit.orbit.models.dto;

import net.orbit.orbit.models.pojo.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brocktubre on 2/4/18.
 */

public class AssignCourseToTeacherDTO {
    private List<Course> courseList;

    public AssignCourseToTeacherDTO()
    {
        courseList = new ArrayList<>();
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public void addCourseRecord(Course c)
    {
        this.courseList.add(c);
    }
}
