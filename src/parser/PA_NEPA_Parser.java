package parser;

import components.Formatter;
import components.Main.Carrier;

// TODO: Auto-generated Javadoc
/**
 * The Class PA_NEPA_Parser.
 */
public class PA_NEPA_Parser extends Parser {
	
	/**
	 * Instantiates a new p A NEP A parser.
	 *
	 * @param tokens the tokens
	 * @param carrier the carrier
	 */
	public PA_NEPA_Parser(String[] tokens, Carrier carrier) {
		super(tokens, carrier);
	}

	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName() {
		String product_name = "";
		int index = 1;
		while(!tokens[index-1].equals("of")){
			index++;
		}
		while(!tokens[index].equals("Benefits")){
			product_name += tokens[index] + " ";
			index++;
		}
		return product_name;
	}

	/**
	 * Gets the deductible.
	 *
	 * @return the deductible
	 */
	public String getDeductible() {
		int index = 10;
		while(!tokens[index].equals("Family")){
			index++;
		}
		return Formatter.formatValue(tokens[index+1]).toString();
	}
	
}
