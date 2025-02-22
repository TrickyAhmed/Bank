package tn.iit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.entities.Compte;

@Repository
public interface CompteRepository extends JpaRepository<Compte, Integer> {
  
}
