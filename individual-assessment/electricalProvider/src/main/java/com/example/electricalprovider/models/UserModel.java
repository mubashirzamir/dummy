package com.example.electricalprovider.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Model class representing a user.
 * This class is mapped to the "citizenInfo" collection in MongoDB.
 */
@Document(collection = "citizenInfo")
public class UserModel {
    @Id
    private String id; // MongoDB will automatically generate this ID

    @Field("Provider ID")
    private String providerId; //providerID is the ID of the provider who is providing the service
    @Field("Database No")
    private Integer databaseNo; //databaseNo is the number of the database in which the citizen is stored for testing purposes
    @Field("name")
    private String name;
    @Field("email")
    private String email;
    @Field("phone")
    private String phone;
    @Field("city")
    private String city;
    @Field("state")
    private String state;
    @Field("country")
    private String country;
    @Field("postalCode")
    private String postalCode;
    @Field("address")
    private String address;

    /**
     * Default constructor.
     */
    public UserModel(){}

    /**
     *  Parameterized constructor.
     * @param providerId
     * @param name
     * @param databaseNo
     * @param email
     * @param phone
     * @param city
     * @param state
     * @param country
     * @param postalCode
     * @param address
     */
    public UserModel(String providerId, String name, Integer databaseNo, String email, String phone, String city, String state, String country, String postalCode, String address) {
        this.providerId = providerId;
        this.name = name; // Ensure this.name is used
        this.databaseNo = databaseNo;
        this.email = email;
        this.phone = phone;
        this.city = city; // Ensure this.city is used
        this.state = state; // Ensure this.state is used
        this.country = country; // Ensure this.country is used
        this.postalCode = postalCode;
        this.address = address;
    }



    public String getProviderId(){
        return providerId;
    }
    public void setProviderId(String providerId){
        this.providerId=providerId;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String PostalCode) {
        this.postalCode = PostalCode;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


    public void setId(String id) {
        this.id = id;
    }

    public int getDatabaseNo() {
        return databaseNo;
    }

    public void setDatabaseNo(int databaseNo) {
        this.databaseNo = databaseNo;
    }
}
