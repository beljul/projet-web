package controllers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.internal.core.search.StringOperation;

import models.Product;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;

import play.mvc.Before;
import play.mvc.With;

@With(Secure.class)
public class RSSFlux extends WrapperController {
	private static SyndFeed feed = new SyndFeedImpl();

	@Before
	public static void create() {
		System.out.println("creation fluuuuuuuuuuuuuuuuuuuuuuuuuuux");
		feed.setAuthor("Scrumch");
		
		// Quelques types de flux supporté : rss_1.0, atom_1.0 (voir https://rome.dev.java.net/ pour tous les types)
		feed.setFeedType("rss_2.0");
		feed.setCopyright("Scrumch - ENSIMAG All right reserved 2014");
		feed.setDescription("RSS Feed of your product from scrumch web site.");
		// Dans play, request.getBase() retourne l'addresse web du site jusqu'à la racine
		// par exemple : http://localhost:9000/
		feed.setLink(request.getBase() + "/Application/dashboard");
		feed.setTitle("Scrumch product feed");
		feed.setLanguage("fr");
		feed.setPublishedDate(new Date());
	}
	
	public static void add() {
		Collection<models.Product> products = models.User.getByEmail(session.get("username"))
													.getProducts().values();
		List entries = new ArrayList();
		for (models.Product product : products) {
			SyndEntry item = new SyndEntryImpl();
			item.setPublishedDate(product.getCreated());
			item.setTitle(product.getName());
			SyndContent content = new SyndContentImpl();
			content.setType("text/plain");
			content.setValue(product.getDescription());
			item.setDescription(content);
			item.setLink(request.getBase() + "/Product/show/");
			entries.add(item);
		}
		feed.setEntries(entries);
		redirect("/Application/dashboard");
	}
	
	public static void serialize() {
		StringWriter writer = new StringWriter();
		SyndFeedOutput out = new SyndFeedOutput();
		
		try {
			out.output(feed, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Nécessaire pour certains lecteurs de fluxs...
		response.contentType = "application/rss+xml";
		// retourne un statut http 200 en plus du contenu xml en paramètre
		renderXml(writer.toString());
	}
}
