package controllers;

import java.util.List;
import java.util.Map;

import lib.TemplateHelper;
import models.Variable;
import play.mvc.Controller;
import play.test.Fixtures;

public class PlantillaController extends Controller {

	//basic filter page
	public static void read() {
		renderArgs.put("state", "reading");
		render();
	}

	public static void doRead(Map<String, String> variable, 
			String orden, Long id, Integer pagina, Integer paginado) {

		if (id==null) id = 0L;
		if (pagina==null) pagina = 1;
		if (paginado==null) paginado = TemplateHelper.DEFAULT_PAGE_SIZE;
		
		List<PlantillaController> variables = Variable.all().fetch(pagina, paginado);
		Long count = Variable.count();
		
		renderArgs.put("state", "read");
		
		render(variable, variables, count, orden, id, pagina, paginado);
	}
	
	public static void create() {
		renderArgs.put("state", "creating");
		Variable variable = new Variable();
		render(variable);
	}

	public static void doCreate(Variable variable) {
		renderArgs.put("state", "created");
		
		if (!variable.validate()) {
			render("@create", variable);
		}
		variable.create();
		flash.success("La variable ha sido creada.");
		update(variable.id);
	}
	
	public static void delete() {
		renderArgs.put("state", "deleting");
		renderTemplate("Variable/read.html");
	}

	public static void doDelete() {
		renderArgs.put("state", "deleted");
		renderTemplate("Variable/read.html");
	}
	
	public static void update(Long id) {
		Variable variable = Variable.findById(id);
		renderArgs.put("state", "updating");
		render(variable);
	}

	public static void doUpdate(Variable variable) {
		if (!variable.validate()) {
			render("@update", variable);
		}
		variable.save();
		flash.success("La variable ha sido modificada.");
		update(variable.id);
	}
	
	public static void export() {
		renderArgs.put("state", "exported");
		renderTemplate("Variable/read.html");
	}

	public static void doPrint() {
		renderArgs.put("state", "printed");
		renderTemplate("Variable/read.html");
	}
	
}
