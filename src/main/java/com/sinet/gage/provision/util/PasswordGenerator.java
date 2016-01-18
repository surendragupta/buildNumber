package com.sinet.gage.provision.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Random Password generator with mixed case characters, symbols and numbers
 * 12 character random password generates with 2 symbols and number and 4
 * uppercase and lowercase characters.
 * 
 * @author Team Gage
 *
 */
public class PasswordGenerator {

	private static final List<Character> UPPER_CASE_LIST = new ArrayList<>();
	private static final List<Character> LOWER_CASE_LIST = new ArrayList<>();
	private static final List<Character> SYMBOL_LIST = new ArrayList<>();
	private static final List<Character> NUMBER_LIST = new ArrayList<>();
	
	private List<Template> templateList = new ArrayList<Template>();

	private PasswordGenerator() {
		build();
		this.lowercase(4)
        .uppercase(4)
        .symbols(2)
        .numbers(2);
	}
	
	/**
	 * Factory method to create our builder.
	 *
	 * @return New PasswordBuilder instance.
	 */
	public static PasswordGenerator builder() {
	    return new PasswordGenerator();
	}
	
	/**
	 * Adds lowercase letters to password.
	 *
	 * @param count Number of lowercase letters to add.
	 * @return This instance.
	 */
	public PasswordGenerator lowercase(int count) {
	    templateList.add(new Template(LOWER_CASE_LIST, count));
	    return this;
	}

	/**
	 * Adds uppercase letters to password.
	 *
	 * @param count Number of uppercase letters to add.
	 * @return This instance.
	 */
	public PasswordGenerator uppercase(int count) {
	    templateList.add(new Template(UPPER_CASE_LIST, count));
	    return this;
	}

	/**
	 * Adds numbers to password.
	 *
	 * @param count Number of numbers letters to add.
	 * @return This instance.
	 */
	public PasswordGenerator numbers(int count) {
	    templateList.add(new Template(NUMBER_LIST, count));
	    return this;
	}

	/**
	 * Adds symbols to password.
	 *
	 * @param count Number of symbols letters to add.
	 * @return This instance.
	 */
	public PasswordGenerator symbols(int count) {
	    templateList.add(new Template(SYMBOL_LIST, count));
	    return this;
	}
	
	/**
	 * Builds the character and number set from which to 
	 * generate the random password.
	 */
	private void build() {
		IntStream.range(0, 26).forEach(i -> {
			LOWER_CASE_LIST.add((char) (i + 'a'));
			UPPER_CASE_LIST.add((char) (i + 'A'));
		});
		IntStream.range(0, 11).forEach(s -> SYMBOL_LIST.add((char) (s + '!')));
		SYMBOL_LIST.add('@');
		IntStream.range(0, 10).forEach(s -> NUMBER_LIST.add((char) (s + '0')));
	}

	/**
	 * Builds the password.
	 *
	 * @return The password.
	 */
	public String generate() {
	    StringBuilder passwordBuilder = new StringBuilder();
	    List<Character> characters = new ArrayList<Character>();

	    for (Template template : templateList) {
	        characters.addAll(template.take());
	    }

        Collections.shuffle(characters);

	    for (char chr : characters) {
	        passwordBuilder.append(chr);
	    }

	    return passwordBuilder.toString();
	}

	/**
	 * Template class to holds character set and returns 
	 * number of characters to be included in the password
	 *  
	 * @author Team Gage
	 *
	 */
	public static class Template {
		private final List<Character> source;
		private final int count;

		private static final Random random = new Random();

		public Template(List<Character> source, int count) {
			this.source = source;
			this.count = count;
		}

		public List<Character> take() {
			List<Character> taken = new ArrayList<Character>(count);
			IntStream.range(0, count).forEach(
				i -> taken.add(source.get(random.nextInt(source.size())))
			);

			return taken;
		}
	}
}
