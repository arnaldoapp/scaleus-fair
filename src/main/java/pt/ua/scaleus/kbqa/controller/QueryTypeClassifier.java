package pt.ua.scaleus.kbqa.controller;

import java.util.List;

import org.aksw.qa.commons.load.Dataset;
import org.aksw.qa.commons.load.QALD_Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ua.scaleus.kbqa.datastructures.KBQAQuestion;
import pt.ua.scaleus.kbqa.datastructures.KBQAQuestionFactory;

public class QueryTypeClassifier {
	static Logger log = LoggerFactory.getLogger(QueryTypeClassifier.class);

	public Boolean isASKQuery(String question) {
		// Compare to source from:
		// src/main/java/org/aksw/pt.ua.scaleus.test/controller/Cardinality.java

		// From train query set: (better to use keyword list!)
		// (Root [-> first child])
		// VBG -> VBZ (Does)
		// VBZ (Is)
		// ADD -> VB (Do)
		// VBP (Are)
		// VBD (Was)
		// VB -> VBD (Did)
		// VBN -> VBD (Was)
		// VB -> VBZ (Does)
		// VBN -> VBZ (Is)

		// regex: ^(Are|D(id|o(es)?)|Is|Was)( .*)$
		return question.startsWith("Are ") || question.startsWith("Did ") || question.startsWith("Do ") || question.startsWith("Does ") || question.startsWith("Is ") || question.startsWith("Was ");
	}

	public static void main(String args[]) {
		log.info("Test QueryType classification ...");
		log.debug("Initialize components ...");
		QALD_Loader datasetLoader = new QALD_Loader();
		QueryTypeClassifier queryTypeClassifier = new QueryTypeClassifier();

		log.info("Run queries through components ...");
		for (Dataset d : Dataset.values()) {
			log.debug("Load data file: " + d);
			List<KBQAQuestion> questions = KBQAQuestionFactory.createInstances(datasetLoader.load(d));
			int counter = 0;
			int counterASK = 0;
			int counterClassifiedWrong = 0;

			for (KBQAQuestion q : questions) {
				// Classify query type
				q.setIsClassifiedAsASKQuery(queryTypeClassifier.isASKQuery(q.getLanguageToQuestion().get("en")));

				if (log.isDebugEnabled()) {
					log.debug("Question ID=" + q.getId() + ": isASK=" + q.getIsClassifiedAsASKQuery() + " - " + q.getLanguageToQuestion().get("en"));
				}

				if (q.getIsClassifiedAsASKQuery()) {
					++counterASK;
				}

				++counter;
				if (q.getIsClassifiedAsASKQuery().booleanValue() != q.getLoadedAsASKQuery().booleanValue()) {
					log.warn("Expected ASK query classification: " + q.getLoadedAsASKQuery() + ", got: " + q.getIsClassifiedAsASKQuery() + ", for: " + q.getLanguageToQuestion().get("en"));
					++counterClassifiedWrong;
				}
			}

			log.info("Classified " + counterClassifiedWrong + " wrong from " + counter + " queries. (" + counterASK + " are ASK)");
		}
	}
}
