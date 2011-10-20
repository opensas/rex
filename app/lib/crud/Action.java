package lib.crud;

import lib.TemplateHelper;
import play.mvc.Router.ActionDefinition;

public class Action {

	public String jsAction;
	public ActionDefinition routeAction;
	public String name = "new action";
	public String label = "new action";
	public String icon = "";
	
	public Boolean isVisible = true;
	public Boolean isEnabled = true;
	
	public Action() {
	}

	public Action(String action, String name, String label, String icon, Boolean isVisible, Boolean isEnabled) {
		setAction(action);
		this.name = name;
		this.label = label;
		this.icon = icon;
		this.isVisible = isVisible;
		this.isEnabled = isEnabled;
	}

	public Action(ActionDefinition action, String name, String label, String icon, Boolean isVisible, Boolean isEnabled) {
		setAction(action);
		this.name = name;
		this.label = label;
		this.icon = icon;
		this.isVisible = isVisible;
		this.isEnabled = isEnabled;
	}
	
	public Action(ActionDefinition action, String name, String label) {
		this(action, name, label, name, true, true);
	}

	public Action(String action, String name, String label) {
		this(TemplateHelper.reverse(action), name, label);
	}
	
	public Action setAction(String action) {
		
		if (action.matches("^js:.*")) {
			jsAction = "";
			routeAction = null;
		} else {
			jsAction = "";
			routeAction = TemplateHelper.reverse(action);
		}
		
		return this;
	}
	
	public Action setAction(ActionDefinition action) {
		jsAction = "";
		routeAction = action;
		return this;
	}

	@Override
	public String toString() {
		return "Action [jsAction=" + jsAction + ", routeAction=" + routeAction
				+ ", name=" + name + ", label=" + label + ", icon=" + icon
				+ ", isVisible=" + isVisible + ", isEnabled=" + isEnabled + "]";
	}
	
}
