package com.albo.directory.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank(message = "The name are required.")
    private String name;

    @NotNull(message = "The phone number are required")
    private Long phoneNumber;
    
    /**
     * Constructor of the class.
     */
    public Contact() {
        super();
    }

    /**
     * Constructor of the class.
     *
     * @param id
     * @param name
     * @param phoneNumber
     */
    public Contact(Long id, @NotBlank(message = "The name are required.") String name,
            @NotNull(message = "The phone number are required") Long phoneNumber) {
        super();
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phoneNumber
     */
    public Long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }    
    
}
