package com.yyd.semantic.test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.tdb.TDBFactory;

public class JenaLibrary implements AutoCloseable {
	private Dataset dt;
	private final static String RULES = 
			"@prefix yyd: <http://www.yydrobot.com/ontologies/recipe.owl#>.\n"
			+ "[r1: (?a yyd:isIngredientOf ?b) (?b yyd:hasTaste yyd:甜) -> (?a yyd:haoChi ?b)]";
	
	private final static String QUERY =
			"PREFIX yyd: <http://www.yydrobot.com/ontologies/recipe.owl#>"
			+ "SELECT ?ingredient WHERE {yyd:西红柿 yyd:haoChi ?ingredient}";

	public JenaLibrary() {
		dt = TDBFactory.createDataset("C:/Users/hongxinzhao/Desktop/owl/yyd_full_recipe_data");
	}

	@Override
	public void close() throws Exception {
		dt.close();
	}

	public void show() {
		BufferedReader bufferedReader = new BufferedReader(new StringReader(RULES));
		List<Rule> rules = Rule.parseRules(Rule.rulesParserFromReader(bufferedReader));
		Reasoner reasoner = new GenericRuleReasoner(rules);
		Model model = dt.getNamedModel("test");
		InfModel infModel = ModelFactory.createInfModel(reasoner, model);
		Query query = QueryFactory.create(QUERY);
		QueryExecution qe = QueryExecutionFactory.create(query, infModel);
		ResultSet rs = qe.execSelect();
		ResultSetFormatter.out(rs);
	}

}
