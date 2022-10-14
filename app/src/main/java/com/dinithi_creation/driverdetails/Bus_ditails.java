package com.dinithi_creation.driverdetails;

public class Bus_ditails {

    private String busId;
    private String busNumber;
    private String busRootNumber;
    private String busStartCity;
    private String busEndCity;
    private String busDistance;
    private String busFare;
    private String busStatus;

    public Bus_ditails(String busId,String busNumber, String busRootNumber, String busStartCity, String busEndCity, String busDistance, String busFare, String busStatus) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.busRootNumber = busRootNumber;
        this.busStartCity = busStartCity;
        this.busEndCity = busEndCity;
        this.busDistance = busDistance;
        this.busFare = busFare;
        this.busStatus = busStatus;
    }

    public Bus_ditails() {
    }


    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusRootNumber() {
        return busRootNumber;
    }

    public void setBusRootNumber(String busRootNumber) {
        this.busRootNumber = busRootNumber;
    }

    public String getBusStartCity() {
        return busStartCity;
    }

    public void setBusStartCity(String busStartCity) {
        this.busStartCity = busStartCity;
    }

    public String getBusEndCity() {
        return busEndCity;
    }

    public void setBusEndCity(String busEndCity) {
        this.busEndCity = busEndCity;
    }

    public String getBusDistance() {
        return busDistance;
    }

    public void setBusDistance(String busDistance) {
        this.busDistance = busDistance;
    }

    public String getBusFare() {
        return busFare;
    }

    public void setBusFare(String busFare) {
        this.busFare = busFare;
    }

    public String getBusStatus() {
        return busStatus;
    }

    public void setBusStatus(String busStatus) {
        this.busStatus = busStatus;
    }
}
