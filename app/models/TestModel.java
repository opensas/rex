package models;

import java.util.Date;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class TestModel extends Model {

	public String stringField;
	public Integer integerField;
	public Long longField;
	public Boolean booleanField;
	public Date dateField;
	
}
