package edu.uady.escolar.repository;

import edu.uady.escolar.entity.Kardex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import edu.uady.escolar.entity.Alumno;


public interface KardexRepository extends JpaRepository<Kardex, Long> {


    List<Kardex> findAllByAlumno_Matricula(String matricula);
    //Kardex findByAlumnoAndMateria(Alumno alumno, Long materia);
}
