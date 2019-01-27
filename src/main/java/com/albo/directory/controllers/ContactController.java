package com.albo.directory.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.albo.directory.models.Contact;
import com.albo.directory.models.User;
import com.albo.directory.repos.ContactRepository;
import com.albo.directory.repos.UserRepository;

import io.swagger.annotations.Api;

@RestController
@Api(produces = "application/json", tags = { "Contact" }, description = "User's contact management")
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
            if (contactFromDb == null) {
                contactFromDb = contactRepopsitory.save(contact);
            }
            user.get().getContacts().add(contactFromDb);
            userRepository.saveAndFlush(user.get());
            return ResponseEntity.ok(contactFromDb);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(path = "/usr/{userId}/receipt/{contactId}", consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Contact> update(@PathVariable(name = "userId") Long userId, @PathVariable(name = "contactId") Long contactId,
            @Valid @RequestBody Contact contact) {
        Contact contactFromDb = findContactById(userId, contactId);
        if (contactFromDb != null) {
            contact.setId(contactFromDb.getId());
            contactRepopsitory.saveAndFlush(contact);
            return ResponseEntity.ok(contact);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/usr/{userId}/receipt", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Set<Contact>> findContacsByUserId(@PathVariable("userId") Long userId,
            @RequestParam(required = false, name = "startLetter") String startsWith) {
        Optional<User> usr = userRepository.findById(userId);
        if (usr.isPresent()) {
            Set<Contact> contacts = null;
            if (!StringUtils.isEmpty(startsWith)) {
                contacts = usr.get().getContacts().stream().filter(c -> c.getName().startsWith(startsWith)).collect(Collectors.toSet());
            } else {
                contacts = usr.get().getContacts();
            }
            if (!contacts.isEmpty()) {
                return ResponseEntity.ok(contacts);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping(path = "/usr/{userId}/receipt/{contactId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Contact> getContactDetail(@PathVariable(name = "userId") Long userId,
            @PathVariable(name = "contactId") Long contactId) {
        Contact contact = findContactById(userId, contactId);
        if(contact != null) 
            return ResponseEntity.ok(contact);
        return ResponseEntity.notFound().build();
    }
    
    
    @DeleteMapping(path = "/usr/{userId}/receipt/{contactId}")
    public ResponseEntity<List<Contact>> delete(@PathVariable("userId") Long userId,
            @PathVariable("contactId") Long contactId) {
        Optional<User> usr = userRepository.findById(userId);
        if(usr.isPresent()) {
            Optional<Contact> contact = contactRepopsitory.findById(contactId);
            if(contact.isPresent()) {
                usr.get().getContacts().remove(contact.get());
                userRepository.saveAndFlush(usr.get());              
                List<User> usrs = userRepository.findByContacts_Id(contactId);
                if(usrs.size() == 0) {
                    contactRepopsitory.delete(contact.get());
                }                
                return ResponseEntity.noContent().build();
            }
        }        
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Find contact by user id and contact id.
     * @param userId
     * @param contactId
     * @return
     */
    private Contact findContactById(Long userId, Long contactId){
        Optional<User> usr = userRepository.findById(userId);
        if (usr.isPresent()) {
            List<Contact> contacts = usr.get().getContacts().stream().filter(c -> c.getId() == contactId).collect(Collectors.toList());
            if (!contacts.isEmpty()) {
                return contacts.get(0);
            }
        }
        return null;
    }
}
