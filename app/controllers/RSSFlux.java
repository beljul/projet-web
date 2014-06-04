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

	/**
	 * Function which create the RSS feed with 
	 * basic informations (author, language...)
	 */
	@Before
	public static void create() {
		feed.setAuthor("Scrumch");
		feed.setFeedType("rss_2.0");
		feed.setCopyright("Scrumch - ENSIMAG All right reserved 2014");
		feed.setDescription("RSS Feed of your product from scrumch web site.");
		feed.setLink(request.getBase() + "/Application/dashboard");
		feed.setTitle("Scrumch product feed");
		feed.setLanguage("fr");
		feed.setPublishedDate(new Date());
	}
	
	/**
	 * Function which add a specific information of the RSS feed
	 * Add information of a new product when it has been created or modified
	 */
	public static void add() {
		Collection<models.Product> products = models.User.getByEmail(session.get("username"))
													.getProducts().values();
		List entries = new ArrayList();
		/* 
		 * for each product of current user
		 * add in the RSS feed
		 */
		for (models.Product product : products) {
			SyndEntry item = new SyndEntryImpl();
			item.setPublishedDate(product.getCreated());
			item.setTitle(product.getName());
			SyndContent content = new SyndContentImpl();
			content.setType("text/plain");
			content.setValue(product.getDescription());
			item.setDescription(content);
			item.setLink(request.getBase() + "/Product/show");
			entries.add(item);
		}
		feed.setEntries(entries);
		redirect("/Application/dashboard");
	}
	
	/**
	 * Function which let possibility for the user
	 * to subscribe the RSS feed
	 */
	public static void serialize() {
		StringWriter writer = new StringWriter();
		SyndFeedOutput out = new SyndFeedOutput();
		
		try {
			out.output(feed, writer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.contentType = "application/rss+xml";
		renderXml(writer.toString());
	}
}
