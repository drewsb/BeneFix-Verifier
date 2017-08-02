package parser;

import components.Formatter;
import components.Main.Carrier;

public class PA_NEPA_Parser extends Parser {
	
	public PA_NEPA_Parser(String[] tokens, Carrier carrier) {
		super(tokens, carrier);
	}

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

	public String getDeductible() {
		int index = 10;
		while(!tokens[index].equals("Family")){
			index++;
		}
		return Formatter.formatValue(tokens[index+1]).toString();
	}
	
}
