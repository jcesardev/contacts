package com.albo.directory.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.albo.directory.models.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long>{

}
