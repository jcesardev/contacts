package com.albo.directory.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.albo.directory.models.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{
    
    public Contact findByPhoneNumber(Long phoneNumber);
    
}
