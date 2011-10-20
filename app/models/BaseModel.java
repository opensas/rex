package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.JPABase;
import play.db.jpa.Model;

@Entity
public class BaseModel extends Model {
	
	public Date fechaAlta;
	public Date fechaModificacion;
	
	public BaseModel() {
		super();
		this.fechaAlta = new Date();
		this.fechaModificacion = this.fechaAlta;
	}

	@Override
	public <T extends JPABase> T save() {
		this.fechaModificacion = new Date();
		return super.save();
	}
	
	public Boolean validate() {
		return true;
	}
	
}
