package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	
    	String title = play.Play.configuration.getProperty("application.htmlKeywords");
    	
        render(title);
    }

    public static void quit() {
        render();
    }
    
}