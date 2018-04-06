package net.orbit.orbit.models.dto;

import net.orbit.orbit.models.pojo.Attendance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 4/6/2018.
 */

public class SaveAttendanceDTO {
    private List<Attendance> attendanceList;

    public SaveAttendanceDTO(){
        this.attendanceList = new ArrayList<Attendance>();
    }
    public SaveAttendanceDTO(List<Attendance> attendances)
    {
        for(Attendance attendance : attendances)
            attendanceList.add(attendance);
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }
    public void addAttendance(Attendance attendance){
        this.attendanceList.add(attendance);
    }
}
