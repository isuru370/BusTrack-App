package com.dinithi_creation.passengerdetails;

public class passdetails {

    private String passeuid;
    private String passefname;
    private String passelname;
    private String passeemail;
    private String passepassword;
    private String passehint;
    private String contact;
    private String address;

    public passdetails(String passeuid, String passefname, String passelname, String passeemail,
                       String passepassword, String passehint, String contact, String address) {
        this.passeuid = passeuid;
        this.passefname = passefname;
        this.passelname = passelname;
        this.passeemail = passeemail;
        this.passepassword = passepassword;
        this.passehint = passehint;
        this.contact = contact;
        this.address = address;
    }

    public passdetails() {
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPasseuid() {
        return passeuid;
    }

    public void setPasseuid(String passeuid) {
        this.passeuid = passeuid;
    }

    public String getPassefname() {
        return passefname;
    }

    public void setPassefname(String passefname) {
        this.passefname = passefname;
    }

    public String getPasselname() {
        return passelname;
    }

    public void setPasselname(String passelname) {
        this.passelname = passelname;
    }

    public String getPasseemail() {
        return passeemail;
    }

    public void setPasseemail(String passeemail) {
        this.passeemail = passeemail;
    }

    public String getPassepassword() {
        return passepassword;
    }

    public void setPassepassword(String passepassword) {
        this.passepassword = passepassword;
    }

    public String getPassehint() {
        return passehint;
    }

    public void setPassehint(String passehint) {
        this.passehint = passehint;
    }
}
