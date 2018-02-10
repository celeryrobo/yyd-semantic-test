package com.yyd.semantic.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;

public class LoadLibrary {
	private Connection conn;
	private Map<String, Forest> forests;

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public LoadLibrary() throws Exception {
		forests = new HashMap<>();
		initMusicCategory();
	}

	private void initMusicCategory() throws Exception {
		conn = DriverManager.getConnection("jdbc:mysql://172.16.1.229:3306/music?characterEncoding=utf8", "test",
				"123456");
		PreparedStatement ps = conn.prepareStatement("SELECT name FROM music_category");
		ResultSet rs = ps.executeQuery();
		List<Value> values = new LinkedList<>();
		while (rs.next()) {
			String name = rs.getString("name");
			values.add(new Value(name, "category", "1"));
		}
		forests.put("musicCategory", Library.makeForest(values));
	}

	public List<Term> parse(String text, String... forestNames) {
		Forest[] forestArr = new Forest[forestNames.length];
		for (int i = 0; i < forestNames.length; i++) {
			forestArr[i] = forests.get(forestNames[i]);
		}
		Result rs = DicAnalysis.parse(text, forestArr);
		List<Term> terms = new ArrayList<>();
		for (Term term : rs) {
			if (terms.isEmpty()) {
				terms.add(term);
			} else {
				Term tm = terms.get(terms.size() - 1);
				if (tm.getNatureStr().equals(term.getNatureStr())) {
					tm.setName(tm.getName() + term.getName());
					tm.setRealName(tm.getName());
				} else {
					terms.add(term);
				}
			}
		}
		return terms;
	}

	public String convert(List<Term> result) {
		StringBuilder sb = new StringBuilder();
		for (Term term : result) {
			sb.append(" ").append("$").append(term.getNatureStr());
		}
		return sb.toString().trim();
	}
}
