package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.entities.Client;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    List<Client> findByNameContainingIgnoreCase(String query);
    Client findByName(String name);
    Client findByEmail(String email);  // Finds a client by email


}
