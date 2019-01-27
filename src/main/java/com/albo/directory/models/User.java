package com.albo.directory.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "USERS_TBL")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Name aren't blank.")
    @Column(nullable = false)
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "user_contact",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "contact_id") })
    private List<Contact> contacts = new ArrayList<>();

    
    /*
     * Constructor of the class.
     */
    public User() {
        super();    
    }

    /**
     * Constructor of the class.
     *
     * @param id
     * @param name
     * @param contacts
     */
    public User(Long id, @NotBlank(message = "Name aren't blank.") String name, List<Contact> contacts) {
        super();
        this.id = id;
        this.name = name;
        this.contacts = contacts;
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
     * @return the contacts
     */
    public List<Contact> getContacts() {
        return contacts;
    }

    /**
     * @param contacts the contacts to set
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    

}
