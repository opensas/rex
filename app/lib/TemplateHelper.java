package lib;

import groovy.lang.Closure;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import play.mvc.Http.Request;
import play.mvc.Router;
import play.mvc.Router.ActionDefinition;
import play.templates.BaseTemplate;
import play.templates.BaseTemplate.RawData;
import play.templates.Template;
import play.utils.HTML;

public class TemplateHelper {

	public static final Integer DEFAULT_PAGE_SIZE = 10;
	
	public static String checkFile(String fileName, String defaultPath) {

		String absolutePath;

		//normalize slashes
		fileName = fileName.replaceAll("\\\\", "/");
		defaultPath = defaultPath.replaceAll("\\\\", "/");
		
		if (defaultPath=="") {
			defaultPath = "/";
		} else {
			// add last slash
			if (!defaultPath.endsWith("/")) defaultPath += "/";
		}
		
		if (fileName.indexOf("/")==-1){
			fileName = defaultPath + fileName;
		}
	    try {
	        absolutePath = play.mvc.Router.reverseWithCheck(fileName, play.Play.getVirtualFile(fileName), false);
	    } catch (Exception ex) {
	        throw new play.exceptions.TagInternalException("File not found: " + fileName);
	    }
		return absolutePath;

	}
	public static String checkFile(String fileName) {
		return checkFile(fileName, "");
	}
	
	public static ActionDefinition reverse(String action, Map<String, Object> args, Boolean absolute) {
		
        if (action.startsWith("controllers.")) {
            action = action.substring(12);
        }

        //no controller specified, add current one
        if (action.indexOf(".") == -1) {
        	action = Request.current().controller + "." + action;
        }
		
        ActionDefinition def = args == null ? Router.reverse(action) : Router.reverse(action, args);  

        if (absolute) def.absolute();
		
		return def;
	}

	public static ActionDefinition reverse(String action) {
		return reverse(action, null, false);
	}

	public static ActionDefinition reverse(String action, Map<String, Object> args) {
		return reverse(action, args, false);
	}
	
	public static ActionDefinition reverse(String action, String args) {
		return reverse(action, StringHelper.toMap(args));
	}
	
	public static ActionDefinition reverseAbsolute(String action) {
		return reverse(action, null, true);
	}

	public static ActionDefinition reverseAbsolute(String action, Map<String, Object> args) {
		return reverse(action, args, true);
	}

	public static ActionDefinition reverseAbsolute(String action, String args) {
		return reverseAbsolute(action, StringHelper.toMap(args));
	}
	
	public static String serialize(LinkedHashMap<String, Object> values) {
		StringBuffer attrs = new StringBuffer();
		String key;
		Object value;
		
		for(Map.Entry<String, Object> entry : values.entrySet()) {
			key = entry.getKey();
			value = entry.getValue();
			if (value!=null && !"".equals(value.toString())) {
				attrs.append(" ");
				attrs.append(key);
				attrs.append("=\"");
				attrs.append(value.toString());
				attrs.append("\"");
			}
		}
		return attrs.toString();
	}

	public static Object getVariable(String name) {
		return BaseTemplate.layoutData.get().get(name);
	}
	
	public static void setVariable(String name, Object value) {
		
		if (value != null && value instanceof String) {
			BaseTemplate.layoutData.get().put(name, __safe(value));
		} else {
			BaseTemplate.layoutData.get().put(name, value);
		}
		
	}

	public static void setVariable(String name, Object value, Boolean additive, Boolean ifEmpty) {
		
		Object previousValue = getVariable(name);
		
		// if already assigned exit
		if (previousValue != null && ifEmpty) return;
		
		// if previous value is not a string, ignore additive
		if (previousValue == null || !(previousValue instanceof String)) additive = false;
		
		if (additive) value = previousValue.toString() + value.toString();

		setVariable(name, value);
	}
	
	public static String bodyToString( Closure body ) {
	    Object oldOut = body.getProperty("out");
	    StringWriter sw = new StringWriter();
	    body.setProperty("out", new PrintWriter(sw));
	    body.call();
	    body.setProperty("out", oldOut);
	    return sw.toString();
	}
	    
    static String __safe(Object val) {
        if (val instanceof RawData) {
            return ((RawData) val).data;
        }
        return HTML.htmlEscape(val.toString());
    }
    
    public static String getButtonLabel(String label) {
    	int pos = label.indexOf("&");
    	
    	if (pos==-1) return label;
    	if (pos==label.length()-1) return label.substring(0, label.length()-1); //indicator past last character

    	return label.substring(0,pos) + "<u>" + label.substring(pos+1,pos+2) + "</u>" + label.substring(pos+2);  
    }
    
    public static String getButtonAccessKey(String label) {
    	int pos = label.indexOf("&");
    	
    	if (pos==-1) return "";
    	if (pos==label.length()-1) return ""; //indicator past last character
    	
    	return label.substring(pos+1, pos+2).toLowerCase();
    }
     
}
