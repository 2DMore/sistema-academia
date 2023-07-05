package edu.uady.escolar.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.uady.escolar.dto.client.LicenciaturaMateriaDTO;

@FeignClient(name = "coa-api", path = "/coa-api/plan-estudios", url = "")
public interface IPlanEstudiosClient {
	@GetMapping(value = ("/{licenciatura-id}"))
    ResponseEntity<LicenciaturaMateriaDTO> findByLicenciaturaId(@PathVariable(value = "licenciatura-id") long licenciatruaId);
}
