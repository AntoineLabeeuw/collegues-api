package dev.collegues.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.collegues.entites.Collegue;
@Repository
public interface CollegueRepository extends JpaRepository<Collegue, Integer> {
	@Query("SELECT c.matricule FROM Collegue c WHERE LOWER(c.nom)=LOWER(?1)")
	public List<String> findByNom(String nom);
	
	public Optional<Collegue> findByMatricule(String matricule);
	
	
}
