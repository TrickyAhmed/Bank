package tn.iit.services;

import org.springframework.stereotype.Service;
import tn.iit.entities.Compte;
import tn.iit.exception.ClientNotFoundException;
import tn.iit.exception.EmailAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tn.iit.entities.Client;
import tn.iit.repository.ClientRepository;

import java.util.List;

@Service
public class ClientService {
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClientById(Integer id) {
        return clientRepository.findById(id).orElseThrow(() ->
                new ClientNotFoundException("Client with ID " + id + " not found")
        );
    }
    public Client findByName(String name) {
        Client client = clientRepository.findByName(name);
        if (client == null) {
            logger.error("im inside the findbyname of clientservice");
            throw new ClientNotFoundException("Client with name '" + name + "' not found");
        }
        return client;
    }


    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Integer id, Client clientDetails) {
        Client existingClient = getClientById(id); // Fetch the client by ID

        if (!existingClient.getEmail().equals(clientDetails.getEmail()) && doesEmailExist(clientDetails.getEmail())) {
            throw new EmailAlreadyExistsException("Email '" + clientDetails.getEmail() + "' already exists!");
        }
        existingClient.setName(clientDetails.getName()); // Update fields as needed
        existingClient.setEmail(clientDetails.getEmail());
        existingClient.setPhoneNumber(clientDetails.getPhoneNumber());// Example field to update

        return clientRepository.save(existingClient); // Save the updated client
    }
    public void saveOrUpdate(Client client) {

        if (doesEmailExist(client.getEmail())) {
            throw new EmailAlreadyExistsException("Email '" + client.getEmail() + "' already exists!");
        }

        clientRepository.save(client);
    }

    public boolean doesEmailExist(String email) {
        Client client = clientRepository.findByEmail(email); // Find client by email
        if (client == null) {
            logger.error("Client with name '{}' not found", email);
            return false;
        }
        return true;
    }



    public void deleteClient(Integer id) {
        Client client = getClientById(id); // Ensure the client exists
        clientRepository.delete(client); // Delete the client
    }

}
