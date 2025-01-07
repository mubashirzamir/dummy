package com.example.SmartCity.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Setter
@Getter
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

}