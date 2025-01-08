package tn.iit.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.RequiredArgsConstructor;
import tn.iit.entities.Client;
import tn.iit.exception.RibAlreadyExists;
import tn.iit.repository.CompteRepository;
import tn.iit.repository.ClientRepository;

import tn.iit.entities.Compte;
import tn.iit.exception.CompteNotFoundException;
import tn.iit.exception.ClientNotFoundException;


@RequiredArgsConstructor
@Service
public class CompteService {

    private final CompteRepository compteRepository;
    private final ClientRepository clientRepository;
    private static final Logger logger = LoggerFactory.getLogger(CompteService.class);

    public void saveOrUpdate(Compte compte) {
        compteRepository.save(compte);
    }



    public List<Compte> findAll() {
        return compteRepository.findAll();
    }

    public void deleteById(Integer rib) {
        compteRepository.deleteById(rib);
    }

    public Compte findById(Integer rib) {
        return compteRepository.findById(rib)
                .orElseThrow(() -> new CompteNotFoundException("Compte with rib= " + rib + " not found!"));
    }

    public Compte findByIdInversed(Integer rib) {
        Optional<Compte> compteOptional = compteRepository.findById(rib);
        if (compteOptional.isPresent()) {
            throw new RibAlreadyExists("Compte with rib= " + rib + " found!");
        }
        return null;
    }


    public List<String> searchClients(String query) {
        List<Client> clients = clientRepository.findByNameContainingIgnoreCase(query);
        List<String> clientNames = new ArrayList<>();
        for (Client client : clients) {
            clientNames.add(client.getName());
        }
        return clientNames;
    }

}
