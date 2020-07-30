package dev.collegues.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.collegues.service.CollegueService;

/**
 * Contrôleur de collègues
 * 
 * @author antoinelabeeuw
 *
 */
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
	 * prend en entrée un nom, et retourne une liste de matricules
	 * 
	 * @param nom : le nom a recherche
	 * @return : le ou les matricules correspondants
	 */
	@GetMapping
	public ResponseEntity<?> rechercheParNom(@RequestParam("nom") String nom) {
		if (nom == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Donnez un nom.");
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(collegueService.findByNom(nom));
		}
	}
}
