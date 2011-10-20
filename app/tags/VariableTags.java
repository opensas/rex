package tags;

import groovy.lang.Closure;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import lib.TemplateHelper;

import play.exceptions.TagInternalException;
import play.exceptions.TemplateExecutionException;
import play.templates.BaseTemplate;
import play.templates.FastTags;
import play.templates.GroovyTemplate.ExecutableTemplate;

public class VariableTags extends FastTags {

	public static void _setVariable(Map<Object, Object> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
		Boolean additive = false;
		Boolean ifEmpty = false;
		
		if (args.containsKey("additive")) {
			additive = (Boolean) args.get("additive");
			args.remove("additive");
		}
		if (args.containsKey("ifEmpty")) {
			ifEmpty = (Boolean) args.get("ifEmpty");
			args.remove("ifEmpty");
		}
		if (args.containsKey("default")) {
			ifEmpty = (Boolean) args.get("default");
			args.remove("default");
		}
		
		if (args.get("arg") != null && body == null) {
			throw new TemplateExecutionException(template.template, fromLine, "Please specify setVariable tag body", new TagInternalException("Please specify setVariable tag body"));
		}
		if (body != null && args.get("arg") == null) {
			throw new TemplateExecutionException(template.template, fromLine, "Please specify setVariable body variable name", new TagInternalException("Please specify setVariable body variable name"));
		}
		if (args.size() == 0) {
			throw new TemplateExecutionException(template.template, fromLine, "Please specify at least one variable", new TagInternalException("Please specify at least one variable"));
		}
		
		//body case
		if (args.get("arg") != null && body != null ) {
			args.put(args.get("arg").toString(), TemplateHelper.bodyToString(body));
		}
		for (Map.Entry<?, ?> entry : args.entrySet()) {
			String key = entry.getKey().toString();
			if (!key.equals("arg")) {
				TemplateHelper.setVariable(key, entry.getValue(), additive, ifEmpty);
			}
		}
        return;
	}

}
