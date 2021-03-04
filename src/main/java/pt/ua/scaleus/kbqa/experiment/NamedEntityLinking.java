package pt.ua.scaleus.kbqa.experiment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aksw.gerbil.io.nif.NIFWriter;
import org.aksw.gerbil.io.nif.impl.TurtleNIFWriter;
import org.aksw.gerbil.transfer.nif.Document;
import org.aksw.gerbil.transfer.nif.data.Annotation;
import org.aksw.gerbil.transfer.nif.data.DocumentImpl;
import org.aksw.qa.commons.datastructure.IQuestion;
import org.aksw.qa.commons.load.Dataset;
import org.aksw.qa.commons.load.QALD_Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

import pt.ua.scaleus.kbqa.datastructures.KBQAQuestion;
import pt.ua.scaleus.kbqa.datastructures.KBQAQuestionFactory;

public class NamedEntityLinking {
	static Logger log = LoggerFactory.getLogger(NamedEntityLinking.class);

	public static void main(String args[]) throws IOException {

		QALD_Loader datasetLoader = new QALD_Loader();

		Dataset dataset = Dataset.QALD6_Train_Multilingual;
		List<IQuestion> IQuestions = datasetLoader.load(dataset);
		List<KBQAQuestion> questions = KBQAQuestionFactory.createInstances(IQuestions);
		List<Document> documents = new ArrayList<Document>();
		for (KBQAQuestion q : questions) {
			String questionString = q.getLanguageToQuestion().get("en");
			log.info(questionString);

			// get entities from gold sparql query
			String sparql = q.getSparqlQuery();
			Pattern pattern = Pattern.compile("<http://dbpedia.org/resource/(.*?)>");
			Matcher matcher = pattern.matcher(sparql);
			Set<String> goldEntities = new HashSet<String>();
			while (matcher.find()) {
				goldEntities.add(matcher.group(0).substring(1, matcher.group(0).length() - 1));
			}
			log.info(Joiner.on("\t").join(goldEntities));

			// MAKE NIF out of it
			Document document = new DocumentImpl(questionString, "http://example.org/document" + q.getId());
			for (String ent : goldEntities) {
				document.addMarking(new Annotation(ent));
			}
			documents.add(document);
		}
		
		NIFWriter writer = new TurtleNIFWriter();
		String nifString = writer.writeNIF(documents);
		log.debug(nifString);
		
		Files.write(Paths.get(dataset.name()+"_NIF.ttl"), nifString.getBytes());
		
//		http://gerbil.aksw.org/gerbil/experiment?id=201605300005 
	}
}