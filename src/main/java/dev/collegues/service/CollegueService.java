package dev.collegues.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
}
