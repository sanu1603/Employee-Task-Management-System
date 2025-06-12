package com.task_managemnet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task_managemnet.entities.User;
import com.task_managemnet.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findFirstByEmail(String username);

    Optional<User> findByUserRole(UserRole admin);

}
