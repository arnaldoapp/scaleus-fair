package pt.ua.scaleus.kbqa.spotter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.aksw.qa.commons.datastructure.Entity;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

import pt.ua.scaleus.kbqa.datastructures.KBQAQuestion;

public class TagMe extends ASpotter {
	static Logger log = LoggerFactory.getLogger(TagMe.class);

	private String requestURL = "http://tagme.di.unipi.it/tag";
	private String key = "";
	private String lang = "en";
	private String include_all_spots = "true";
	private String include_categories = "true";

	public TagMe() {

		try {
			Properties prop = new Properties();
			InputStream input = getClass().getClassLoader().getResourceAsStream("pt.ua.scaleus.test.properties");
			prop.load(input);
			this.key = prop.getProperty("tagmekey");
		} catch (IOException e) {
			log.error("Could not create Tagme", e);
		}
	}

	private String doTASK(final String inputText) throws MalformedURLException, IOException, ProtocolException {

		String urlParameters = "text=" + URLEncoder.encode(inputText, "UTF-8");
		urlParameters += "&key=" + key;
		urlParameters += "&lang=" + lang;
		urlParameters += "&include_all_spots=" + include_all_spots;
		urlParameters += "&include_categories=" + include_categories;
		return requestPOST(urlParameters, requestURL);
	}

	@Override
	public Map<String, List<Entity>> getEntities(final String question) {
		HashMap<String, List<Entity>> tmp = new HashMap<>();
		try {
			String foxJSONOutput = doTASK(question);
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(foxJSONOutput);

			JSONArray resources = (JSONArray) jsonObject.get("annotations");

			ArrayList<Entity> tmpList = new ArrayList<>();
			for (Object res : resources.toArray()) {
				JSONObject next = (JSONObject) res;
				Entity ent = new Entity();
				ent.label = ((String) next.get("spot"));
				ent.uris.add(new ResourceImpl(((String) next.get("title")).replaceAll(",", "%2C")));
				JSONArray types = (JSONArray) next.get("dbpedia_categories");
				if (types != null) {
					for (Object type : types) {
						ent.posTypesAndCategories.add(new ResourceImpl((String) type));
					}
				}
				tmpList.add(ent);
			}
			String baseURI = "http://dbpedia.org/resource/";
			for (Entity entity : tmpList) {
				// hack to make underscores where spaces are
				Resource resource = entity.uris.get(0);
				if (resource.getURI() != null) {
					ResourceImpl e = new ResourceImpl(baseURI + resource.getURI().replace(" ", "_"));
					entity.uris.add(e);
					entity.uris.remove(0);
				}
			}

			tmp.put("en", tmpList);

		} catch (ParseException | IOException e) {
			log.error("Could not call TagMe for NER/NED", e);
		}
		return tmp;
	}

	public static void main(final String args[]) {
		KBQAQuestion q = new KBQAQuestion();
		// q.languageToQuestion.put("en", "Merkel met Obama?");
		q.getLanguageToQuestion().put("en", "Which buildings in art deco style did Shreve, Lamb and Harmon design?");
		ASpotter fox = new TagMe();
		q.setLanguageToNamedEntites(fox.getEntities(q.getLanguageToQuestion().get("en")));
		for (String key : q.getLanguageToNamedEntites().keySet()) {
			System.out.println(key);
			for (Entity entity : q.getLanguageToNamedEntites().get(key)) {
				System.out.println("\t" + entity.label + " ->" + entity.type);
				for (Resource r : entity.posTypesAndCategories) {
					System.out.println("\t\tpos: " + r);
				}
				for (Resource r : entity.uris) {
					System.out.println("\t\turi: " + r);
				}
			}
		}
	}
}
