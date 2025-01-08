package tn.iit.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tn.iit.exception.EmailAlreadyExistsException;
import tn.iit.services.ClientService;
import tn.iit.entities.Client;
import tn.iit.services.CompteService;

@Controller
@RequestMapping("/clients")
public class ClientController {

	private final ClientService clientService;
	private static final Logger logger = LoggerFactory.getLogger(CompteService.class);

	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	// GET: Retrieve all clients

	@GetMapping({ "/all", "/", "/index" })
	public String findAll(Model model) {
		model.addAttribute("Clients", clientService.getAllClients());
		return "clients";
	}

	@GetMapping("/edit")
	public String edit(@RequestParam int id, Model model) {
		Client client = clientService.getClientById(id);
		model.addAttribute("client", client);
		return "clients-edit";
	}

	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestParam("Name") String esm, @RequestParam("Email") String mail,
									   @RequestParam("Phone") String tlf) {
		try {
			Client client = new Client(esm, mail, tlf);
			clientService.saveOrUpdate(client);
			return new ResponseEntity<>("Client saved successfully", HttpStatus.OK);
		} catch (EmailAlreadyExistsException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}




	@PostMapping("/update")
	public String update(@RequestParam("id") int id,@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber){
		logger.error("inside update method post of client controller");

		Client client = new Client(name, email , phoneNumber);
		clientService.updateClient(id ,client);
		return "redirect:/clients/";
	}

	// DELETE: Delete a client by ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteClient(@PathVariable Integer id) {
		clientService.deleteClient(id);
		return ResponseEntity.noContent().build();
	}
}