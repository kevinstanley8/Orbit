package net.orbit.orbit.models.dto;

import net.orbit.orbit.models.pojo.Grade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Stanley on 2/20/2018.
 */

public class SaveGradesDTO {
    private List<Grade> gradeList;

    public SaveGradesDTO() {
        this.gradeList = new ArrayList<Grade>();
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public void addGrade(Grade grade)
    {
        this.gradeList.add(grade);
    }

    @Override
    public String toString() {
        return "SaveGradesDTO{" +
                "gradeList='" + gradeList.toString() + '\'' +
                '}';
    }

}
