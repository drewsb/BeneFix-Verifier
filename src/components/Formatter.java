package components;

import java.io.IOException;
import java.util.HashMap;

import java.util.Map;


// TODO: Auto-generated Javadoc
/**
* <h1>Formatter</h1>
* Class holding numerous formatting methods used for string manipulation and string parsing. 
* <p>
*
* @author  Drew Boyette
* @version 1.0
* @since   2017-08-16
*/
public class Formatter {
	
	/**
	   * Removes all occurrences of the specified string from a stringbuilder. 
	   * @param s the StringBuilder
	   * @param r the string to be removed entirely
	   * @return a reference to this object
	   */
	public static StringBuilder removeString(StringBuilder s, String r) {
		while (s.indexOf(r) != -1) {
			int index = s.indexOf(r);
			s.replace(index, index + r.length(), "");
		}
		return s;
	}

	/**
	   * Removes all occurrences of the specified string from a stringbuilder.
	   * @param s the string
	   * @param r the string to be removed entirely
	   * @return a reference to this object
	   */
	public static String removeString(String s, String r) {
		s = s.replaceAll(r, "");
		return s;
	}

	/**
	 * Removes all occurrences of all given delimiters from a string.
	 *
	 * @param s the string
	 * @param delims array of strings to be removed
	 * @return a reference to this object
	 */
	public static String removeStrings(String s, String[] delims) {
		for (String r : delims) {
			s = s.replaceAll(r, "");
		}
		return s;
	}

	/**
	 * Removes all occurrences of all given delimiters from a stringbuilder.
	 *
	 * @param s the StringBuilder
	 * @param delims array of strings to be removed
	 * @return a reference to this object
	 */
	public static StringBuilder removeStrings(StringBuilder s, String[] delims) {
		for (String r : delims) {
			while (s.indexOf(r) != -1) {
				int index = s.indexOf(r);
				s.replace(index, index + r.length(), "");
			}
		}
		return s;
	}

	/**
	 * Removes all commas from a stringbuilder.
	 *
	 * @param s the StringBuilder
	 * @return a reference to this object
	 */
	public static StringBuilder removeCommas(StringBuilder s) {
		while (s.indexOf(",") != -1) {
			int index = s.indexOf(",");
			s.replace(index, index + 1, "");
		}
		return s;
	}

	/**
	 * Replace all commas and dollar signs from string and parse into a Double.
	 *
	 * @param s the string
	 * @return a Double
	 */
	public static Double formatValue(String s) {
		return Double.parseDouble(s.replaceAll("[$,]", ""));
	}

	/**
	 * Removes all occurrences of the specified string from a stringbuilder. 
	 *
	 * @param s the s
	 * @return the altered stringbuilder
	 * @see IOException
	 */
	public static Boolean isPercentage(String s) {
		return s.contains("%");
	}

	/**
	 * Removes all occurrences of the specified string from a stringbuilder. 
	 *
	 * @param s the s
	 * @return the altered stringbuilder
	 * @see IOException
	 */
	public static Boolean isDollarValue(String s) {
		return s.contains("$");
	}

	/**
	 * Removes all occurrences of the specified string from a stringbuilder. 
	 *
	 * @param s the s
	 * @return the altered stringbuilder
	 * @see IOException
	 */
	public static String getPercentage(String s) {
		String[] arr = s.split("\\s");
		for (String r : arr) {
			if (isPercentage(r)) {
				return r;
			}
		}
		return "";
	}

	/**
	 * Removes all occurrences of the specified string from a stringbuilder. 
	 *
	 * @param s the s
	 * @return the altered stringbuilder
	 * @throws NumberFormatException the number format exception
	 * @see IOException
	 */
	public static Double getValue(String s) throws NumberFormatException {
		if (s.toLowerCase().equals("n/a") || s.isEmpty() || s.toLowerCase().equals("unlimited")) {
			return -2.0;
		}
		Double output = -1.0;
		s = s.replaceAll("[$,]", "");
		try {
			output = Double.parseDouble(s);
			return output;
		} catch (NumberFormatException e) {
		}
		String[] tokens = s.split("\\s");
		for (String r : tokens) {
			try {
				output = Math.max(Double.parseDouble(r), output);
			} catch (NumberFormatException e) {
			}
		}
		return output;
	}

	/**
	 * Removes all occurrences of the specified string from a stringbuilder. 
	 *
	 * @param map the map
	 * @return the altered stringbuilder
	 * @see IOException
	 */
	public static void printDictionary(HashMap<String, Double> map) {
		for (Map.Entry<String, Double> entry : map.entrySet()) {
			System.out.printf("Key: %s\n", entry.getKey());
			System.out.printf("Value: %s\n\n", entry.getValue());
		}

	}

	/**
	 * Removes all occurrences of the specified string from a stringbuilder. 
	 *
	 * @param r the r
	 * @return the altered stringbuilder
	 * @see IOException
	 */
	public static String formatRatingArea(String r) {
		String[] delims = { "Rating Area ", "Area " };
		r = r.replaceAll(", ", "/");
		r = r.replaceAll(",", "/");
		r = removeStrings(r, delims);
		return r;
	}

	/**
	 * Removes all occurrences of the specified string from a stringbuilder. 
	 *
	 * @param s the s
	 * @return the altered stringbuilder
	 * @see IOException
	 */
	public static String capitalize(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.toLowerCase().substring(1);
	}

}
