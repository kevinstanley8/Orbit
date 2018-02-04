package net.orbit.orbit.models.dto;

import net.orbit.orbit.utils.Constants;

/**
 * Created by brocktubre on 2/4/18.
 */

public class ResponseStatusDTO {
    private Boolean success;
    private String displayStatus;
    private String status;

    public ResponseStatusDTO(Boolean success){
        if(success){
            this.success = true;
            this.displayStatus = Constants.SUCCESS_STATUS_DISPLAY;
            this.status = Constants.SUCCESS_STATUS;
        }else{
            this.success = false;
            this.displayStatus = Constants.FAIL_STATUS_DISPLAY;
            this.status = Constants.FAIL_STATUS;
        }
    }
    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public String getDisplayStatus() {
        return displayStatus;
    }
    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
