package es.baki.proglang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Parser {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Incorrect Usage");
			return;
		}

		/*
		 * Parse the grammar rule file supplied in args and import it to the
		 * GrammarRule class
		 */
		File grammarFile = new File(args[0]);
		Scanner grammarScanner;
		try {
			grammarScanner = new Scanner(grammarFile);
		} catch (FileNotFoundException e) {
			System.err.println("Can't find the grammar file specified");
			return;
		}
		GrammarRule currentRule = null;
		while (grammarScanner.hasNext()) {
			String grammarLine = grammarScanner.nextLine();
			if (grammarLine.startsWith("|")) { // Add to the previous line's
												// rule
				if (currentRule == null) {
					System.err.println("Trying to add to a null rule, valid grammar file?");
					return;
				} else
					currentRule.addSearchString(grammarLine.substring(2));
			} else if (grammarLine.matches("\\w* =.*")) {
				String replace, with;
				replace = grammarLine.substring(grammarLine.indexOf('=') + 1).trim();
				with = grammarLine.substring(0, grammarLine.indexOf(' ')).trim();
				currentRule = new GrammarRule(with, replace);
			} else
				System.err.println("Not sure how to handle this grammar line:\n" + grammarLine);

		}
		grammarScanner.close();

		/*
		 * Check the other file supplied with args and push onto the stack,
		 * checking the stack for grammar rules every push
		 */
		File fileToCheck = new File(args[1]);
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(fileToCheck);
		} catch (FileNotFoundException e) {
			System.err.println("Can't find the file to check specified");
			return;
		}
		Stack<String> grammarCheckStack = new Stack<>();
		String stringToCheck = fileScanner.nextLine();
		String[] splitStringToCheck = stringToCheck.split(" ");

		for (String token : splitStringToCheck) {
			grammarCheckStack.push(token);
			GrammarRule.checkStack(grammarCheckStack);
		}

		fileScanner.close();

		if (grammarCheckStack.size() == 1) {
			// TODO Check to see if this appears on the left side of an equal
			// sign
		} else
			System.out.println("string is invalid!");
	}
}