package edu.uady.escolar.controller;

import edu.uady.escolar.entity.Alumno;
import edu.uady.escolar.error.ControlEscolarException;
import edu.uady.escolar.service.AlumnoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/alumno")
@Log4j2
public class AlumnoController {
    @Autowired
    private AlumnoService alumnoService;

    @GetMapping
    public List<Alumno> getAllAlumnos() {
        return alumnoService.getAllAlumnos();
    }
    @GetMapping("/{matricula}")
    public ResponseEntity<?> getAlumnoByMatricula(@PathVariable(value="matricula")String matricula) {
    	try {
            return ResponseEntity.ok().body(alumnoService.getAlumnoDTOByMatricula(matricula));
        }catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public Alumno createAlumno(@RequestBody Alumno alumno){
        log.info("Alumno  a guardar: "+alumno.toString());
        return alumnoService.createAlumno(alumno);
    }
    
    
    @PostMapping("/{lic}")
    public ResponseEntity<?> createAlumnoConKardex(@RequestBody Alumno alumno, @PathVariable(value="lic") Long licenciaturaId) {
    	try {
            return ResponseEntity.ok().body(alumnoService.createAlumnoConKardex(alumno, licenciaturaId));
        }catch (Exception e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    @PutMapping
    public Alumno updateAlumno(@RequestBody Alumno alumno) {
        log.info("Alumno a actualizar :"+alumno.toString());
        return alumnoService.updateAlumno(alumno);
    }

    @DeleteMapping("/{id}")
    public void deletealumno(@PathVariable (value = "id") Long id){
         alumnoService.deleteAlumno(id);
    }
}
