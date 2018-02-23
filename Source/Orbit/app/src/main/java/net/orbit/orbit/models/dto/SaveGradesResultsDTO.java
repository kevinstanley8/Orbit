package net.orbit.orbit.models.dto;

import net.orbit.orbit.models.pojo.Grade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Stanley on 2/20/2018.
 */

public class SaveGradesResultsDTO {
    private List<GradeResult> saveGradeResults;

    public class GradeResult {
        private int gradeId;
        private boolean saveSuccess;

        public GradeResult(int gradeId, boolean saveSuccess)
        {
            this.gradeId = gradeId;
            this.saveSuccess = saveSuccess;
        }

        public int getGradeId() {
            return gradeId;
        }

        public void setGradeId(int gradeId) {
            this.gradeId = gradeId;
        }

        public boolean isSaveSuccess() {
            return saveSuccess;
        }

        public void setSaveSuccess(boolean saveSuccess) {
            this.saveSuccess = saveSuccess;
        }
    }

    public SaveGradesResultsDTO()
    {
        this.saveGradeResults = new ArrayList<GradeResult>();
    }

    public void addResult(int gradeId, boolean saveSuccess)
    {
        this.saveGradeResults.add(new GradeResult(gradeId, saveSuccess));
    }

    public List<GradeResult> getSaveGradeResults() {
        return saveGradeResults;
    }

    public void setSaveGradeResults(List<GradeResult> saveGradeResults) {
        this.saveGradeResults = saveGradeResults;
    }

    @Override
    public String toString() {
        return "SaveGradesResultsDTO{" +
                "saveGradeResults='" + saveGradeResults + '\'' +
                '}';
    }

}
