package edu.uday.sie.service;

import edu.uday.sie.dto.client.MateriaDTO;
import edu.uday.sie.entity.CostoMateria;
import edu.uday.sie.error.COAException;
import edu.uday.sie.repository.CostoMateriaRepository;
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
public class CostoMateriaService {

	@Autowired
	private Environment env;
	
    @Autowired
    private CostoMateriaRepository costoMateriaRepository;

    public List<CostoMateria> getAllCostoMaterias() throws Exception {
        List<CostoMateria> costoMateriaList = costoMateriaRepository.findAll();

        if (costoMateriaList.isEmpty()) {
            throw new COAException("No se encontraron datos");
        }

        return costoMateriaList;
    }

    public CostoMateria createCostoMateria(CostoMateria costoMateria) {
        log.info("Se crea costo materia: " + costoMateria.toString());
        return costoMateriaRepository.save(costoMateria);
    }

    public CostoMateria createCostoMateriaNombre(CostoMateria costoMateria, String claveMat) {
    	RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<MateriaDTO> response = restTemplate.exchange(env.getProperty("URL_COA")+"/materia/"
                        +claveMat,
                HttpMethod.GET, entity, MateriaDTO.class);
        MateriaDTO dtoMat=response.getBody();
        costoMateria.setMateriaId(dtoMat.getIdMateria());
        return  createCostoMateria(costoMateria);
    }
    
    public CostoMateria updateCostoMateria(CostoMateria costoMateria) throws Exception {
        Optional<CostoMateria> costoMateriaOptional = costoMateriaRepository.findCostoMateriaById(costoMateria.getMateriaId());

        if (costoMateriaOptional.isPresent()) {
            log.info("Actualizando costo materia: " + costoMateria.toString());
            return costoMateriaRepository.save(costoMateria);
        }

        throw new COAException("No se encuentra el costo de la materia: " + costoMateria.toString());
    }
    
    

    public void deleteCostoMateria(Long id) {
        costoMateriaRepository.deleteById(id);
    }

}
