package net.orbit.orbit.models.dto;

/**
 * Created by brocktubre on 1/15/18.
 */

public class CheckRoleDTO {
    private Boolean hasRole;
    private String currentRoleName;

    public CheckRoleDTO(String currentRoleName){
        this.currentRoleName = currentRoleName;
        this.hasRole = false;
    }

    public CheckRoleDTO(String currentRoleName, Boolean hasRole){
        this.currentRoleName = currentRoleName;
        this.hasRole = hasRole;
    }

    public Boolean getHasRole() {
        return hasRole;
    }
    public void setHasRole(Boolean hasRole) {
        this.hasRole = hasRole;
    }
    public String getCurrentRoleName() {
        return currentRoleName;
    }
    public void setCurrentRoleName(String currentRoleName) {
        this.currentRoleName = currentRoleName;
    }
    @Override
    public String toString() {
        return "CheckRoleDTO [hasRole=" + hasRole + ", currentRoleName=" + currentRoleName
                + "]";
    }

}