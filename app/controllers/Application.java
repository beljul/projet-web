package controllers;

import play.*;

import play.mvc.*;

import java.util.*;

import org.codehaus.groovy.control.customizers.SecureASTCustomizer;

import models.*;

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
        render();
    }
}