package es.baki.proglang;

import java.util.ArrayList;
import java.util.Stack;

public class GrammarRule {
	private static ArrayList<GrammarRule> rules = new ArrayList<>();

	private final String substitute;
	private ArrayList<String> search;

	public GrammarRule(String substitute, String... search) {
		this.substitute = substitute;
		for (String s : search)
			this.search.add(s);

		rules.add(this);
	}

	public void addSearchString(String s) {
		search.add(s);
	}

	public static String getSubstitute(String s) {

		return null;
	}

	public static void checkStack(Stack<String> grammarCheckStack) {
		// TODO Auto-generated method stub

	}

}
