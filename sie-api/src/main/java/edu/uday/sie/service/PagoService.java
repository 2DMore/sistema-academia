package edu.uday.sie.service;

import edu.uday.sie.dto.client.AlumnoDTO;
import edu.uday.sie.dto.client.MateriaDTO;
import edu.uday.sie.entity.Pago;
import edu.uday.sie.error.COAException;
import edu.uday.sie.repository.PagoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PagoService {

	@Autowired
	private Environment env;
	
    @Autowired
    private PagoRepository pagoRepository;
    
    public List<Pago> getAllPagos() throws Exception {
        List<Pago> pagoList = pagoRepository.findAll();

        if (pagoList.isEmpty()) {
            throw new COAException("No se encontraron datos");
        }

        return pagoList;
    }

    public Pago createPago(Pago pago) {
        log.info("Se crea pago: " + pago.toString());
        return pagoRepository.save(pago);
    }

    public Pago createPagoConClaveYMatricula(Pago pago, String claveMat, String matricula) {
    	RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<AlumnoDTO>response=restTemplate.exchange(env.getProperty("URL_COE")+"/alumno/"
        		+matricula,HttpMethod.GET, entity, AlumnoDTO.class);
        AlumnoDTO dtoAlumno=response.getBody();
        pago.setAlumnoId(dtoAlumno.getAlumnoId());
        RestTemplate restTemplateMat = new RestTemplate();
        HttpHeaders headersMat = new HttpHeaders();
        HttpEntity entityMat = new HttpEntity(headers);
        ResponseEntity<MateriaDTO> responseMat = restTemplate.exchange(env.getProperty("URL_COA")+"/materia/"
                        +claveMat,
                HttpMethod.GET, entity, MateriaDTO.class);
        MateriaDTO dtoMat=responseMat.getBody();
        pago.setMateriaId(dtoMat.getIdMateria());
		return createPago(pago);
	}
    
    public Pago updatePago(Pago pago) throws Exception {
        Optional<Pago> pagoOptional = pagoRepository.findByAlumnoIdAndMateriaId(pago.getAlumnoId(), pago.getMateriaId());

        if (pagoOptional.isPresent()) {
            log.info("Actualizando pago: " + pago.toString());
            return pagoRepository.save(pago);
        }

        throw new COAException("No se encontró el pago: " + pago.toString());
    }

    public void deletePago(Long id) {
        pagoRepository.deleteById(id);
    }

	
}
