package controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import lib.DataHelper;
import lib.DataHelper.FieldType;
import lib.StringHelper;
import lib.crud.Action;
import lib.crud.Actions;
import models.TestModel;
import play.mvc.Controller;
import play.mvc.Router.ActionDefinition;

public class Test extends Controller {

	public static void index() {
		
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		
		data.put("url1", lib.TemplateHelper.reverse("controllers.Test.index()").url);
		data.put("url2", lib.TemplateHelper.reverse("Test.index()").url);
		data.put("url3", lib.TemplateHelper.reverse("index()").url);
		data.put("url4", lib.TemplateHelper.reverse("controllers.Test.index()").url);
		data.put("url5", lib.TemplateHelper.reverse("controllers.Test.index()").url);
		data.put("url6", lib.TemplateHelper.reverse("controllers.Test.index()").url);
		data.put("url7", lib.TemplateHelper.reverse("controllers.Test.testRouteWithParam(id:'4',sas)").url);
		
		render(data);
	}
	
	public static void setVariable() {
		render();
	}
	
	public static void testCrudActions() {

		Actions actions = new Actions();
		
		actions
			.add("create()",	"create", 	"alta")
			.add("update()",	"update", 	"modificación", "", true, false)
			.add("read()",		"read", 	"consulta")
			.add("delete()", 	"delete", 	"eliminar", "", true, false)
			.add("prunte()",	"prune", 	"pruner", "icon", false, true);
		
		StringBuilder sb = new StringBuilder();
		
		for(Action action : actions.values()) {
			sb.append("name: ").append(action.name).append(", label: ").append(action.label);
		}
		renderArgs.put("actionString", sb.toString());
		
		render(actions);
	}
	
	public static void testRouteWithParam(Long id, String name) {
		
		Map<String, Object> params = new LinkedHashMap<String, Object>();
		params.put("id", "22");
		params.put("name", "sas");
		
		ActionDefinition def = lib.TemplateHelper.reverse("Test.testRouteWithParam", params);
		
		String defUrl = def.url;
		
		render(id, name, def, defUrl);
	}
	
	public static void testToMap() {
		
		Map<String, Map<String, Object>> maps = new LinkedHashMap<String, Map<String, Object>>();
		
		maps.put("map1", StringHelper.toMap("key1:value1,key2:value2"));
		maps.put("map2", StringHelper.toMap("key1:value1,key2:value2,key3:value3"));
		
		render(maps);
		
	}
	
	public static void testFieldType() throws Exception {
		
		FieldType type1 = DataHelper.fieldType(TestModel.class, "stringField");
		FieldType type2 = DataHelper.fieldType(TestModel.class, "integerField");
		FieldType type3 = DataHelper.fieldType(TestModel.class, "longField");
		FieldType type4 = DataHelper.fieldType(TestModel.class, "booleanField");
		FieldType type5 = DataHelper.fieldType(TestModel.class, "dateField");
		
		render(type1, type2, type3, type4, type5);
		
	}
	
}
