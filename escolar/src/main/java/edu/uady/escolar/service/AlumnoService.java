package edu.uady.escolar.service;

import edu.uady.escolar.dto.AlumnoDTO;
import edu.uady.escolar.dto.KardexAlumno;
import edu.uady.escolar.dto.MateriasKardex;
import edu.uady.escolar.dto.client.LicenciaturaMateriaDTO;
import edu.uady.escolar.entity.Alumno;
import edu.uady.escolar.entity.Kardex;
import edu.uady.escolar.repository.AlumnoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Log4j2
public class AlumnoService {
    @Autowired
    private AlumnoRepository alumnoRepository;
    
    @Autowired
    private KardexService kardexService;
    

    public Alumno createAlumno(Alumno alumno){
        log.info("crea alumno: "+alumno.toString());
        return alumnoRepository.save(alumno);
    }
    
    
    public Alumno createAlumnoConKardex(Alumno alumno, Long licenciaturaId) throws Exception{
    	Random random=new Random();
    	int randomNum=0;
    	KardexAlumno kardexAlumno =kardexService.findByKardexByNuevoAlumno(alumno, licenciaturaId);
    	for(MateriasKardex materia:kardexAlumno.getMateriasKardex()) {
    		Kardex kardex=new Kardex();
    		kardex.setMateria(materia.getIdMateria());
    		kardex.setCalificacion(0.0);
    		kardex.setAlumno(alumno);
    		alumnoRepository.save(alumno);
    		randomNum=random.nextInt(10000);
    		kardex.setFolioKardex(String.valueOf(randomNum));
    		log.info(kardex.toString());
    		kardexService.createKardex(kardex);
    	}
    	
        return alumnoRepository.save(alumno);
    }

    public Alumno updateAlumno(Alumno alumno){
        log.info("actualiza alumno: "+alumno.toString());
        return alumnoRepository.save(alumno);
    }

    public List<Alumno> getAllAlumnos(){
        return alumnoRepository.findAll();
    }
    
    public Alumno getAlumnoByMatricula(String matricula) {
    	return alumnoRepository.findByMatricula(matricula);
    }
    
    public AlumnoDTO getAlumnoDTOByMatricula(String matricula) {
    	Alumno alumno=alumnoRepository.findByMatricula(matricula);
    	AlumnoDTO dto=new AlumnoDTO();
    	dto.setAlumnoId(alumno.getId());
    	return dto;
    }

    public void deleteAlumno(Long id){
        alumnoRepository.deleteById(id);
    }
}
