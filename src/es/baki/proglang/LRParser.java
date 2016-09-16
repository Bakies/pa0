package es.baki.proglang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class LRParser {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Incorrect Usage");
			return;
		}

		// Parse Grammar
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
			if (grammarLine.startsWith("|")) {
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

		// Check grammar of file
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

		if (grammarCheckStack.size() == 1)
			System.out.println("Grammar Check Successful");
		else {
			String output = "";
			while (grammarCheckStack.size() != 0)
				output = grammarCheckStack.pop() + " " + output;
			System.out.println("Grammar Check Failed, resulting stack: \n" + output);
		}
	}
}
