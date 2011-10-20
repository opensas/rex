package controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.mvc.Controller;

public class TestRegExp extends Controller {

	public static void index(String text, String expression) {
		
		Pattern pattern;
		Matcher matcher;
		Match match;
		
		Set<Match> result = new LinkedHashSet<Match>();
		
		if (expression==null) expression = "";
		
		if (!expression.equals("")) {
			pattern = Pattern.compile(expression);
			matcher = pattern.matcher(text);
			
			if (matcher.find()) {
				for(int i=0; i<=matcher.groupCount(); i++) {
					match = new TestRegExp.Match(matcher.start(i), matcher.end(i), matcher.group(i));
					result.add(match);
				}
			}
		}
		
		render(text, expression, result);
	}
	
	public static class Match {
		int start;
		int end;
		String group;
		
		public Match(int start, int end, String group) {
			this.start = start;
			this.end = end;
			this.group = group;
		}
		
	}
	
}
