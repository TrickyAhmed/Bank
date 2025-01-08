package tn.iit.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import tn.iit.entities.Client;
import tn.iit.entities.Compte;
import tn.iit.exception.ClientNotFoundException;
import tn.iit.exception.CompteNotFoundException;
import tn.iit.exception.RibAlreadyExists;
import tn.iit.services.CompteService;
import tn.iit.services.ClientService;


@AllArgsConstructor
@Controller
@RequestMapping("/comptes")
public class CompteController {
	private final CompteService compteService;
	private final ClientService clientService;
	private static final Logger logger = LoggerFactory.getLogger(CompteService.class);

	@GetMapping({ "/all", "/", "/index" })
	public String findAll(Model model) {
		model.addAttribute("comptes", compteService.findAll());
		return "comptes";
	}



	@GetMapping("/search")
	@ResponseBody
	public List<String> searchClients(@RequestParam String query) {
		return compteService.searchClients(query);
	}


	@ResponseBody
	@GetMapping("/all-json")
	public List<Compte> findAllJson() {
	return compteService.findAll();
	}
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestParam("rib") int rib,
									   @RequestParam("nomClient") String nomClient,
									   @RequestParam("solde") float solde) {
		try {
			logger.error("im inside the compteController");

			Client client;
			try {
				client = clientService.findByName(nomClient);
			} catch (ClientNotFoundException e) {
				logger.error("Client not found for name: {}", nomClient);
				return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
			}

			try {
				Compte compte1 = compteService.findByIdInversed(rib);
			} catch (RibAlreadyExists e) {
				logger.error("Compte found for RIB: {}", rib);
				return new ResponseEntity<>("Compte found", HttpStatus.BAD_REQUEST);
			}
			Compte compte = new Compte(rib, solde, client);
			compteService.saveOrUpdate(compte);

			return new ResponseEntity<>("Compte created successfully", HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Unexpected error occurred: ", e);
			return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}





	@ResponseBody
	@PostMapping("/delete-ajax")
	public void deleteAjax(@RequestParam Integer rib) {
		compteService.deleteById(rib);
		
	}

	@GetMapping("/edit")
	public String edit(@RequestParam int rib, Model model) {
		Compte compte = compteService.findById(rib);
		model.addAttribute("compte", compte);
		return "comptes-edit";  // This is the view name for your editing page
	}


	@PostMapping("/update")
	public String update(@RequestParam("rib") int rib ,@RequestParam("nomClient") String nomClient, @RequestParam("solde") float solde) {
		Client client = clientService.findByName(nomClient);
		Compte compte = new Compte(rib, solde, client);
		compteService.saveOrUpdate(compte);
		return "redirect:/comptes/";
	}
}
