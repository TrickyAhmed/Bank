package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.entities.User;



public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
