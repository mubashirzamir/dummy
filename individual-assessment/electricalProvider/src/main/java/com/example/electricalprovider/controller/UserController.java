package com.example.electricalprovider.controller;

import com.example.electricalprovider.models.UserModel;
import com.example.electricalprovider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user information.
 */
@RestController
@RequestMapping("/api/electricalProvider/{providerId}")
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService the service for managing user data
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new citizen based on providerId and userModel.
     *
     * @param providerId the ID of the provider
     * @param userModel the model containing citizen data
     * @return ResponseEntity with a success message or error message
     */
    @PostMapping("/citizen")
    public ResponseEntity<String> createCitizen(@PathVariable String providerId, @RequestBody UserModel userModel) {
        try {
            String message= userService.addCitizen(providerId,userModel);
            return ResponseEntity.ok(message);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating Citizen: " + e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Citizen: " + e.getMessage());
        }
    }

    /**
     * Retrieves all citizens for the specified provider.
     *
     * @param providerId the ID of the provider
     * @return ResponseEntity with the list of citizens or error message
     */
    @GetMapping("/citizen")
    public ResponseEntity<?> getAllCitizens(@PathVariable String providerId){
        try {
            List<UserModel> citizens = userService.getAllCitizens(providerId);
            return ResponseEntity.ok(citizens);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching Citizens: " + e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching Citizens: " + e.getMessage());
        }
    }

    /**
     * Retrieves a citizen by ID for the specified provider.
     *
     * @param providerId the ID of the provider
     * @param id the ID of the citizen to retrieve
     * @return ResponseEntity with the citizen data or error message
     */
    @GetMapping("/citizen/{id}")
    public ResponseEntity<?> getCitizenById(@PathVariable String providerId,@PathVariable String id){
        try{
            UserModel citizen= userService.getCitizenById(providerId,id);
            return ResponseEntity.ok(citizen);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error fetching Citizen: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching Citizen: " + e.getMessage());
        }
    }

    /**
     * Retrieves a citizen by ID for the specified provider.
     *
     * @param providerId the ID of the provider
     * @param id the ID of the citizen to retrieve
     * @return ResponseEntity with the citizen data or error message
     */
    @PutMapping("/citizen/{id}")
    public ResponseEntity<String> updateCitizenData(@PathVariable String providerId, @PathVariable String id, @RequestBody UserModel userModel){
        try {
            String message= userService.updateCitizenData(providerId, id, userModel);
            return ResponseEntity.ok(message);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating Citizen: " + e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Citizen: " + e.getMessage());
        }
    }

    /**
     * Deletes a citizen by ID for the specified provider.
     *
     * @param providerId the ID of the provider
     * @param id the ID of the citizen to delete
     * @return ResponseEntity with a success message or error message
     */
    @DeleteMapping("/citizen/{id}")
    public ResponseEntity<String> deleteCitizen(@PathVariable String providerId, @PathVariable String id){
        try {
            String message= userService.deleteCitizen(providerId, id);
            return ResponseEntity.ok(message);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error deleting Citizen: " + e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Citizen: " + e.getMessage());
        }
    }

}
