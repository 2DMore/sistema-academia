# Principios SOLID en Spring Boot
## Single responsibility principle
El primer principio se refiere a que una clase sólo debe tener una responsabilidad y no debe tener más de una razón para cambiar.  

Ejemplo:  
```java
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
```
Esta clase tiene el propósito de llevar una acción relacionada a la entidad alumno dependiendo del tipo de solicitud que se realice. En otras palabras: Administrar peticiones relacionadas con la entidad "Alumno".  

## Open-Closed principle
El segundo principio trata de  

Ejemplo:
## Liskov Substitution principle
El tercer principio indica que  

Ejemplo:  
```java
package edu.uady.escolar.error;

public class ControlEscolarException extends Exception{
    private int code;
    private String message;

    public ControlEscolarException (String message){
        super(message);
    }
}
```
```java
public class KardexService {
	@Autowired
	private Environment env;
	
    @Autowired
    private KardexRepository kardexRepository;

    public List<Kardex> getAllKardexs() throws Exception{
        List<Kardex> kardexes = kardexRepository.findAll();
        if(kardexes.isEmpty()){
            throw new ControlEscolarException("no se encontraron datos");
        }
        return  kardexes;
    }
}
```

## Interface Segregation principle
El cuarto principio da a notar  

Ejemplo:
## Dependency Inversion principle
El último principio habla sobre  

Ejemplo:
