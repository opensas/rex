package models;

import javax.persistence.Entity;

import play.data.validation.Validation;

@Entity
public class Variable extends BaseModel {

	public String codigo;
	public String contenido;
	public String descripcion;

	@Override
	public String toString() {
		return codigo;
	}

	@Override
	public Boolean validate() {
		
		Boolean valid = true;
		
		//codigo
		if ("".equals(codigo)) {
			Validation.addError("variable.codigo", "Debe especificar el código de la variable."); valid = false;
		} else if (codigo.length() < 3 ) {
			Validation.addError("variable.codigo", "El código de la variable debe tener por lo menos tres caracteres."); valid = false;
		} else if (Variable.count("codigo = ? and id != ?", codigo, id==null? 0: id)!=0) {
			Validation.addError("variable.codigo", "Ya existe una variable con el código '%2$s'.", codigo); valid = false;
		}
		
		//descripción
		if ("".equals(descripcion)) {
			Validation.addError("variable.descripcion", "Debe especificar la descripción de la variable."); valid = false;
		}
		
		return valid;
	}
	
}
