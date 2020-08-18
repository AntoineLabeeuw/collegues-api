package dev.collegues.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.collegues.dto.CollegueDto;
import dev.collegues.entites.Collegue;
import dev.collegues.exceptions.CollegueNonTrouveException;
import dev.collegues.service.CollegueService;

/**
 * Contrôleur de collègues
 * 
 * @author antoinelabeeuw
 *
 */
@CrossOrigin
@RestController
@RequestMapping("collegues")
public class CollegueController {
	private CollegueService collegueService;

	/**
	 * Constructeur
	 * 
	 * @param collegueService
	 */
	public CollegueController(CollegueService collegueService) {
		super();
		this.collegueService = collegueService;
	}

	/**
	 * CollegueNonTouve exception catcher
	 * 
	 * @param ex : l'excpetion
	 * @return : la responseEntity
	 */
	@ExceptionHandler(CollegueNonTrouveException.class)
	public ResponseEntity<String> onCollegueNonTrouveException(CollegueNonTrouveException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	/**
	 * prend en entrée un nom, et retourne une liste de matricules
	 * 
	 * @param nom : le nom a recherche
	 * @return : le ou les matricules correspondants
	 */
	@GetMapping
	public ResponseEntity<?> rechercheParNom(@RequestParam(value="nom", required=false) String nom) {
		if (nom == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(collegueService.findAll());
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(collegueService.findByNom(nom));
		}
	}

	/**
	 * recherche un collegue par son matricule. Renvoie une exception si non trouvé,
	 * catchée par l'exceptionHandler
	 * 
	 * @param matricule : le matricule a rechercher
	 * @return le collègue
	 */
	@GetMapping("/{matricule}")
	public ResponseEntity<Collegue> findByMatricule(@PathVariable String matricule) {
		return ResponseEntity.status(HttpStatus.OK).body(collegueService.findByMatricule(matricule));
	}

	/**
	 * prend en entrée des données provenant d'un body JSON, et crée un client avec
	 * 
	 * @param collegue : le body JSON, un ClientDto
	 * @param result   : BindingResult
	 * @return : une ResponseEntity
	 */
	@PostMapping
	public ResponseEntity<?> creerCollegue(@RequestBody @Valid CollegueDto collegue, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tous les champs doivent etre valorises.");
		}
		Collegue newCollegue = collegueService.creerCollegue(collegue.getNom(), collegue.getPrenoms(),
				collegue.getDateDeNaissance(), collegue.getPhotoUrl());
		String body = "Collègue bien créé avec le matricule" + newCollegue.getMatricule();
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}
}
