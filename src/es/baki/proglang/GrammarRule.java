package es.baki.proglang;

import java.util.ArrayList;
import java.util.Stack;

public class GrammarRule {
	private static ArrayList<GrammarRule> rules = new ArrayList<>();

	private final String substitute;
	private ArrayList<String> search = new ArrayList<String>();

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
		for (GrammarRule rule : rules)
			for (String search : rule.search)
				if (s.equals(search))
					return rule.substitute;
		return null;
	}

	public static void checkStack(Stack<String> grammarCheckStack) {
		if (grammarCheckStack.size() == 0)
			return;

		String token = null;
		while (grammarCheckStack.size() != 0) {
			if (token == null)
				token = grammarCheckStack.pop();
			else
				token = grammarCheckStack.pop() + " " + token;
			String sub = getSubstitute(token);
			if (sub != null) {
				System.out.println("Replacing " + token + " with " + sub);
				grammarCheckStack.push(sub);
				checkStack(grammarCheckStack);
				return;
			}
		}
		for (String s : token.split(" "))
			grammarCheckStack.push(s);
	}
}
