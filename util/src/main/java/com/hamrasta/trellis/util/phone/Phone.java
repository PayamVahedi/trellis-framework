package com.hamrasta.trellis.util.phone;


public class Phone {
    private Integer countryCode;

    private Long nationalNumber;

    private String localNumber;

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }

    public Long getNationalNumber() {
        return nationalNumber;
    }

    public void setNationalNumber(Long nationalNumber) {
        this.nationalNumber = nationalNumber;
    }

    public String getLocalNumber() {
        return localNumber;
    }

    public void setLocalNumber(String localNumber) {
        this.localNumber = localNumber;
    }

    public Phone() {
    }

    public Phone(Integer countryCode, Long nationalNumber, String localNumber) {
        this.countryCode = countryCode;
        this.nationalNumber = nationalNumber;
        this.localNumber = localNumber;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "countryCode=" + countryCode +
                ", nationalNumber=" + nationalNumber +
                ", localNumber='" + localNumber + '\'' +
                '}';
    }
}
