package es.baki.proglang;

import java.util.ArrayList;
import java.util.Stack;

public class GrammarRule {
	private static ArrayList<GrammarRule> rules = new ArrayList<>();

	private final String substituteTo;
	private ArrayList<String> substituteWith = new ArrayList<String>();

	/**
	 * Make a new Grammar Rule with an initial substitute string and as many
	 * search string as desired
	 *
	 * @param substitute
	 * @param search
	 */
	public GrammarRule(String substitute, String... search) {
		this.substituteTo = substitute;
		for (String s : search)
			this.substituteWith.add(s);
		rules.add(this);
	}

	/**
	 * Add String s to the list of search strings
	 *
	 * @param s
	 */
	public void addSearchString(String s) {
		substituteWith.add(s);
	}

	/**
	 * Check to see if String s should be substituted based on any existing
	 * grammar rules made
	 *
	 * @param s
	 *            the string to be substituted
	 * @return the string that s gets substituted with
	 */
	private static String getSubstitute(String s) {
		for (GrammarRule rule : rules)
			for (String search : rule.substituteWith)
				if (s.equals(search))
					return rule.substituteTo;
		return null;
	}

	/**
	 * a recursive function to check a stack of strings against any existing
	 * rules and substitute according to any rules, recurses to check the stack
	 * after a substitution
	 *
	 * @param grammarCheckStack
	 */
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
				grammarCheckStack.push(sub);
				checkStack(grammarCheckStack);
				return;
			}
		}
		for (String s : token.split(" "))
			grammarCheckStack.push(s);
	}
}
