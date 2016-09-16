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
			String grammarLine = grammarScanner.next();
			if (grammarLine.startsWith("|")) {
				if (currentRule == null) {
					System.err.println("Trying to add to a null rule");
					continue;
				}
				currentRule.addSearchString(grammarLine.substring(2));
			} else if (grammarLine.split(" ")[1].equals("="))
				currentRule = new GrammarRule(grammarLine.substring(0, grammarLine.indexOf('=') - 1),
						grammarLine.substring(grammarLine.indexOf('=')).trim());
			else
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
		String stringToCheck = fileScanner.next();
		String[] splitStringToCheck = stringToCheck.split(" ");

		for (String token : splitStringToCheck) {
			grammarCheckStack.push(token);
			GrammarRule.checkStack(grammarCheckStack);
		}

		fileScanner.close();

		if (grammarCheckStack.size() == 1 && grammarCheckStack.contains("S"))
			System.out.println("Grammar Check Successful");
		else {
			System.out.println("Grammar Check Failed, resulting stack: ");
			while (grammarCheckStack.size() != 0)
				System.out.println(grammarCheckStack.pop());
		}

	}

}
