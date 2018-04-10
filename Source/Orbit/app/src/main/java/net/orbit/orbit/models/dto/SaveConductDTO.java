package net.orbit.orbit.models.dto;

import net.orbit.orbit.models.pojo.Conduct;
import net.orbit.orbit.models.pojo.Grade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin Stanley on 4/7/2018.
 */

public class SaveConductDTO {
    private List<Conduct> conductList;

    public SaveConductDTO() {
        this.conductList = new ArrayList<Conduct>();
    }

    public List<Conduct> getConductList() {
        return conductList;
    }

    public void setConductList(List<Conduct> conductList) {
        this.conductList = conductList;
    }

    public void addConduct(Conduct conduct)
    {
        this.conductList.add(conduct);
    }

    @Override
    public String toString() {
        return "SaveConductDTO{" +
                "conductList='" + conductList.toString() + '\'' +
                '}';
    }

}
