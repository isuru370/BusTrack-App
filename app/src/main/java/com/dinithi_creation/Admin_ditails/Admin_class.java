package com.dinithi_creation.Admin_ditails;

public class Admin_class {

    private String adminId;
    private String adminName;
    private String adminEmail;
    private String adminPhone;
    private String adminPassword;
    private String adminImageId;

    public Admin_class(String adminId, String adminName, String adminEmail, String adminPhone, String adminPassword, String adminImageId) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.adminPhone = adminPhone;
        this.adminPassword = adminPassword;
        this.adminImageId = adminImageId;
    }

    public Admin_class() {
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminImageId() {
        return adminImageId;
    }

    public void setAdminImageId(String adminImageId) {
        this.adminImageId = adminImageId;
    }
}
