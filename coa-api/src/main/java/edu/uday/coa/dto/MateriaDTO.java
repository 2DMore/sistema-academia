package edu.uday.coa.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MateriaDTO {
	private Long idMateria;
    private String claveMateria;
    private String materia;
    private int creditos;
    private int semestre;
}
