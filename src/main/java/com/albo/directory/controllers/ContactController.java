package com.albo.directory.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.albo.directory.models.Contact;
import com.albo.directory.models.User;
import com.albo.directory.repos.ContactRepository;
import com.albo.directory.repos.UserRepository;

import io.swagger.annotations.Api;

@RestController
@Api(produces = "application/json", tags = {"Contact"}, description= "User's contact management")
public class ContactController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepopsitory;

    @PostMapping(path = "/usr/{userId}/receipt", consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Contact> save(@PathVariable(name = "userId") Long userId, @Valid @RequestBody Contact contact) {
        Optional<User> user = userRepository.findById(userId);        
        if (user.isPresent()) {            
            Contact contactFromDb = contactRepopsitory.findByPhoneNumber(contact.getPhoneNumber());
            if(contactFromDb == null) {
                contactFromDb = contactRepopsitory.save(contact);
            }
            user.get().getContacts().add(contactFromDb);
            userRepository.saveAndFlush(user.get());
            return ResponseEntity.ok(contactFromDb);
        }        
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/usr/{userId}/receipt/{contactId}")
    public ResponseEntity<Contact> getContactDetail(@PathVariable(name = "userId") Long userId,
            @PathVariable(name = "contactId") Long contactId) {
        return null;
    }

    @GetMapping(path = "/usr/{userId}/receipt", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Contact>> findContacsByUserId(@PathVariable("userId") Long userId,
            @RequestParam(required = false, name = "startLetter") String startsWith) {

        return null;

    }
}
