package models;

import javax.persistence.Entity;

import play.data.validation.Validation;

@Entity
public class Plantilla extends BaseModel {

	public String codigo;
	public String descripcion;
	public String contenido;

	@Override
	public String toString() {
		return codigo;
	}

	@Override
	public Boolean validate() {
		
		Boolean valid = true;
		
		//codigo
		if ("".equals(codigo)) {
			Validation.addError("plantilla.codigo", "Debe especificar el código de la plantilla."); valid = false;
		} else if (codigo.length() < 3 ) {
			Validation.addError("plantilla.codigo", "El código de la plantilla debe tener por lo menos tres caracteres."); valid = false;
		} else if (Plantilla.count("codigo = ? and id != ?", codigo, id==null? 0: id)!=0) {
			Validation.addError("plantilla.codigo", "Ya existe una plantilla con el código '%2$s'.", codigo); valid = false;
		}
		
		//descripción
		if ("".equals(descripcion)) {
			Validation.addError("plantilla.descripcion", "Debe especificar la descripción de la plantilla."); valid = false;
		}
		
		return valid;
	}
	
}
