package edu.uday.coa.repository;

import edu.uday.coa.entity.Materia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface MateriaRepository extends JpaRepository<Materia, Long> {
	Materia findByClaveMateria(String claveMateria);
}
