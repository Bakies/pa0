package es.baki.proglang;

import java.util.ArrayList;
import java.util.Stack;

public class GrammarRule {
	private static ArrayList<GrammarRule> rules = new ArrayList<>();

	private final String replace;
	private ArrayList<String> search = new ArrayList<String>();

	/**
	 * Make a new Grammar Rule with an initial substitute string and as many
	 * search string as desired
	 *
	 * @param substitute
	 * @param search
	 */
	public GrammarRule(String substitute, String... search) {
		this.replace = substitute;
		for (String s : search)
			this.search.add(s);
		rules.add(this);
	}

	/**
	 * Add String s to the list of search strings
	 *
	 * @param s
	 */
	public void addSearchString(String s) {
		search.add(s);
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
			for (String search : rule.search)
				if (s.equals(search))
					return rule.replace;
		return null;
	}

	/**
	 * Get the list of all the grammar rules
	 *
	 * @return
	 */
	public static ArrayList<GrammarRule> getRules() {
		return rules;
	}

	/**
	 * a recursive function to check a stack of strings against any existing
	 * rules and substitute according to any rules, recurses to check the stack
	 * after a substitution
	 *
	 * @param grammarCheckStack
	 */
	public static void checkAndReplaceStack(Stack<String> grammarCheckStack) {
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
				checkAndReplaceStack(grammarCheckStack);
				return;
			}
		}
		for (String s : token.split(" "))
			grammarCheckStack.push(s);
	}

	/**
	 * Returns the rule's replace string
	 *
	 * @return
	 */
	public String getReplacementString() {
		return replace;
	}
}
