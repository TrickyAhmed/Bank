package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iit.entities.Role;


public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);
}
