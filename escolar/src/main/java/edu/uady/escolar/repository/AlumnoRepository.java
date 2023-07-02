package edu.uady.escolar.repository;

import edu.uady.escolar.entity.Alumno;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
	
}
