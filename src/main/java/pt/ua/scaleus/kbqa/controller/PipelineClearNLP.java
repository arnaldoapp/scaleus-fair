package pt.ua.scaleus.kbqa.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ua.scaleus.kbqa.cache.CachedParseTreeClearnlp;
import pt.ua.scaleus.kbqa.datastructures.Answer;
import pt.ua.scaleus.kbqa.datastructures.KBQAQuestion;
import pt.ua.scaleus.kbqa.nlp.MutableTreePruner;
import pt.ua.scaleus.kbqa.nlp.SentenceToSequence;
import pt.ua.scaleus.kbqa.querybuilding.Annotater;
import pt.ua.scaleus.kbqa.querybuilding.SPARQL;
import pt.ua.scaleus.kbqa.querybuilding.SPARQLQueryBuilder;
import pt.ua.scaleus.kbqa.spotter.Fox;

public class PipelineClearNLP extends AbstractPipeline {
	static Logger log = LoggerFactory.getLogger(PipelineClearNLP.class);
	private Fox nerdModule;
	private CachedParseTreeClearnlp cParseTree;
	private SentenceToSequence sentenceToSequence;
	private MutableTreePruner pruner;
	private Annotater annotater;
	private SPARQLQueryBuilder queryBuilder;
	private Cardinality cardinality;
	private QueryTypeClassifier queryTypeClassifier;

	public PipelineClearNLP() {
		queryTypeClassifier = new QueryTypeClassifier();

		nerdModule = new Fox();
		// controller.nerdModule = new Spotlight();
		// controller.nerdModule =new TagMe();
		// controller.nerdModule = new MultiSpotter(fox, tagMe, wiki, spot);

		// cParseTree = new CachedParseTreeClearnlp();
		cParseTree = new CachedParseTreeClearnlp();
		cardinality = new Cardinality();

		sentenceToSequence = new SentenceToSequence();

		pruner = new MutableTreePruner();

		SPARQL sparql = new SPARQL();
		annotater = new Annotater(sparql);

		queryBuilder = new SPARQLQueryBuilder(sparql);
	}

	@Override
	public List<Answer> getAnswersToQuestion(KBQAQuestion q) {
		log.info("Question: " + q.getLanguageToQuestion().get("en"));

		log.info("Classify question type.");
		q.setIsClassifiedAsASKQuery(queryTypeClassifier.isASKQuery(q.getLanguageToQuestion().get("en")));

		// Disambiguate parts of the query
		log.info("Named entity recognition.");
		q.setLanguageToNamedEntites(nerdModule.getEntities(q.getLanguageToQuestion().get("en")));

		// Noun combiner, decrease #nodes in the DEPTree
		log.info("Noun phrase combination.");
		// TODO make this method return the combine sequence and work on this,
		// i.e., q.sequence = sentenceToSequence.combineSequences(q);
		sentenceToSequence.combineSequences(q);

		// Build trees from questions and cache them
		log.info("Dependency parsing.");
		q.setTree(cParseTree.process(q));

		// Cardinality identifies the integer i used for LIMIT i
		log.info("Cardinality calculation.");
		q.setCardinality(cardinality.cardinality(q));

		// Apply pruning rules
		log.info("Pruning tree.");
		q.setTree(pruner.prune(q));

		// Annotate tree
		log.info("Semantically annotating the tree.");
		annotater.annotateTree(q);

		// Calculating all possible SPARQL BGPs with given semantic annotations
		log.info("Calculating SPARQL representations.");
		List<Answer> answers = queryBuilder.build(q);

		return answers;
	}

}
