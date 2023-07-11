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
El segundo principio trata de que las entidades deben ser abiertas para extensión, pero cerradas para modificación.

Ejemplo:
```java
package edu.uday.coa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "materias")
@Data
@NoArgsConstructor
public class Materia {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "materia_seq")
    @SequenceGenerator(name = "materia_seq", sequenceName = "materia_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    @Column(name = "clave_materia")
    private String claveMateria;
    @Column(name ="nombre")
    private String nombreMateria;
    @JsonIgnore
    @JsonProperty(value = "OtroNombreDelPlanDeEstudios")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "materia")
    @ToString.Exclude
    private List<PlanEstudio> planEstudios;
}
```
Esta entidad está abierta a que se realicen nuevos tipos de materias con nuevas funcionalidades y atributos (por ejemplo: obligatorias y optativas) sin que sea necesario realizar modificaciones a esta entidad.  
## Liskov Substitution principle
El tercer principio indica que las clases hijas deben tener el mismo comportamiento que las clases padres. 

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
La clase ControlEscolarException cumple con el Principio de Sustitución de Liskov al poder hacer las acciones que hace la clase a la cual está heredando sin operar de una forma diferente a Exception.

## Interface Segregation principle
El cuarto principio da a notar que los clientes no deberían ser forzados a depender de métodos que no usan.

Ejemplo:  
```java
package edu.uady.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class UadyProducerApplication implements CommandLineRunner{
	
	@Autowired
	KafkaTemplate kafkaTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(UadyProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while(true) {
			kafkaTemplate.send("uady-topic", "Mensaje enviado: " + UUID.randomUUID());
		}
	}
	
	

}
```
Se observa que esta clase está utilizando todos los métodos de la interfaz CommandLineRunner, por lo que está cumpliendo el Principio de Segregación de Interfaces.
## Dependency Inversion principle
El último principio habla sobre  

Ejemplo:
