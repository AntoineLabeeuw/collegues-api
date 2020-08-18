package dev.collegues.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dev.collegues.entites.Collegue;
import dev.collegues.exceptions.CollegueNonTrouveException;
import dev.collegues.repository.CollegueRepository;
@Service
public class CollegueService {
	private CollegueRepository collegueRepo;

	/**
	 * Constructeur
	 * 
	 * @param collegueRepo
	 */
	public CollegueService(CollegueRepository collegueRepo) {
		super();
		this.collegueRepo = collegueRepo;
	}

	public List<String> findByNom(String nom) {
		return collegueRepo.findByNom(nom);
	}
	
	public Collegue findByMatricule(String matricule) { 
		return collegueRepo.findByMatricule(matricule).orElseThrow(() -> new CollegueNonTrouveException("Collegue non trouvé"));
	}
	@Transactional
	public Collegue creerCollegue(String nom, String prenoms, LocalDate dateDeNaissance, String photoUrl) {
		Collegue newCollegue = new Collegue();
		newCollegue.setNom(nom);
		newCollegue.setPrenoms(prenoms);
		newCollegue.setDateDeNaissance(dateDeNaissance);
		newCollegue.setPhotoUrl(photoUrl);
		String emailGenere = nom+prenoms+"@gmail.com";
		newCollegue.setEmail(emailGenere);
		// generation random d'un matricule
		newCollegue.setMatricule(UUID.randomUUID().toString());
		return collegueRepo.save(newCollegue);
	}
	public List<Collegue> findAll() {
		return collegueRepo.findAll();
	}
}
