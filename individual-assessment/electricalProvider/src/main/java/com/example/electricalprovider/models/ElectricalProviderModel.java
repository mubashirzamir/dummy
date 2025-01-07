package com.example.electricalprovider.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Model class representing an electrical provider.
 * This class is mapped to the "ElectricalProviderData" collection in MongoDB.
 */
@Document(value="ElectricalProviderData")
public class ElectricalProviderModel {

    /**
     * The unique identifier for the electrical provider.
     */
    @Id
    private String id;

    /**
     * The name of the company.
     */
    @Field("companyName")
    private String companyName;

    /**
     * The address of the company.
     */
    @Field("companyAddress")
    private String companyAddress;

    /**
     * The phone number of the company.
     */
    @Field("companyPhoneNumber")
    private String companyPhoneNumber;

    /**
     * The email address of the company.
     */
    @Field("companyEmail")
    private String companyEmail;

    /**
     * Default constructor.
     */
    public ElectricalProviderModel() {
    }

    /**
     * Parameterized constructor.
     *
     * @param id the unique identifier for the electrical provider
     * @param companyName the name of the company
     * @param companyAddress the address of the company
     * @param companyPhoneNumber the phone number of the company
     * @param companyEmail the email address of the company
     */
    public ElectricalProviderModel(String id, String companyName, String companyAddress, String companyPhoneNumber, String companyEmail) {
        this.id = id;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyPhoneNumber = companyPhoneNumber;
        this.companyEmail = companyEmail;
    }
    //setter and getter methods

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getCompanyEmail() {
        return companyEmail;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
