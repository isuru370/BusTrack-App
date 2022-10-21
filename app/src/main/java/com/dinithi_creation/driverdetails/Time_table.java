package com.dinithi_creation.driverdetails;

public class Time_table {

    private String busNumber;
    private String startcity;
    private String endCity;
    private String startTime;
    private String endTime;
    private String time01;
    private String time02;
    private String time03;
    private String time04;
    private String time05;
    private String time06;
    private String time07;

    public Time_table(String busNumber, String startcity, String endCity, String startTime, String endTime, String time01, String time02, String time03, String time04, String time05, String time06, String time07) {
        this.busNumber = busNumber;
        this.startcity = startcity;
        this.endCity = endCity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.time01 = time01;
        this.time02 = time02;
        this.time03 = time03;
        this.time04 = time04;
        this.time05 = time05;
        this.time06 = time06;
        this.time07 = time07;
    }

    public Time_table() {

    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getStartcity() {
        return startcity;
    }

    public void setStartcity(String startcity) {
        this.startcity = startcity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTime01() {
        return time01;
    }

    public void setTime01(String time01) {
        this.time01 = time01;
    }

    public String getTime02() {
        return time02;
    }

    public void setTime02(String time02) {
        this.time02 = time02;
    }

    public String getTime03() {
        return time03;
    }

    public void setTime03(String time03) {
        this.time03 = time03;
    }

    public String getTime04() {
        return time04;
    }

    public void setTime04(String time04) {
        this.time04 = time04;
    }

    public String getTime05() {
        return time05;
    }

    public void setTime05(String time05) {
        this.time05 = time05;
    }

    public String getTime06() {
        return time06;
    }

    public void setTime06(String time06) {
        this.time06 = time06;
    }

    public String getTime07() {
        return time07;
    }

    public void setTime07(String time07) {
        this.time07 = time07;
    }
}
