package com.example.electricalprovider.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Data Transfer Object (DTO) for updating electrical provider information.
 * This class is used to transfer data between processes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Ignore null fields
public class ElectricalProviderUpdateDTO {

    private String companyName;
    private String companyEmail;
    private String companyAddress;
    private String companyPhoneNumber;

    /**
     * Default constructor.
     */
    public ElectricalProviderUpdateDTO() {
    }

    /**
     * Parameterized constructor.
     *
     * @param companyName the name of the company
     * @param companyEmail the email of the company
     * @param companyAddress the address of the company
     * @param companyPhoneNumber the phone number of the company
     */
    public ElectricalProviderUpdateDTO(String companyName, String companyEmail, String companyAddress, String companyPhoneNumber) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.companyAddress = companyAddress;
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }


}

