package net.orbit.orbit.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Stanley on 1/28/2018.
 */

public class EnrollStudentInClassDTO {
    private List<EnrollRecord> enrollRecords;

    public EnrollStudentInClassDTO()
    {
        enrollRecords = new ArrayList<>();
    }

    public List<EnrollRecord> getEnrollRecords() {
        return enrollRecords;
    }

    public void setEnrollRecords(List<EnrollRecord> enrollRecords) {
        this.enrollRecords = enrollRecords;
    }

    public void addEnrollRecord(int studentID, int courseID)
    {
        this.enrollRecords.add(new EnrollRecord(studentID, courseID));
    }
}
