package com.albo.directory.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.albo.directory.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    List<User> findByNameStartingWithIgnoreCase(String name);
    
    List<User> findByContacts_Id(Long contactId);
    
}
