package parser;

import components.Main.Carrier;
import names.Product_Name.Metal;
import names.Product_Name.Plan;

// TODO: Auto-generated Javadoc
/**
 * The Class Parser.
 */
public class Parser {

	/** The tokens. */
	final String[] tokens;
	
	/** The carrier. */
	final Carrier carrier;
	
	/**
	 * Instantiates a new parser.
	 *
	 * @param tokens the tokens
	 * @param carrier the carrier
	 */
	public Parser(String[] tokens, Carrier carrier) {
		super();
		this.tokens = tokens;
		this.carrier = carrier;
	}
	
	/**
	 * Find product name.
	 *
	 * @param name the name
	 * @return the boolean
	 */
	public Boolean findProductName(String name){
		String[] name_tokens = name.toLowerCase().split("\\s");
		int buffer = 200;
		int index = 0;
		boolean doesMatch = true;
		
		while(index < buffer){
			if(tokens[index].toLowerCase().contains(name_tokens[0])){
				int temp_index = index;
				for(String s : name_tokens){
					if(!tokens[temp_index].toLowerCase().contains(s)){
						doesMatch = false;
						break;
					}
					temp_index++;
				}
				if(doesMatch){
					return true;
				}
			}
			index++;
		}
		return false;
	}
	
	/**
	 * Find metal.
	 *
	 * @param metal the metal
	 * @return the boolean
	 */
	public Boolean findMetal(Metal metal){
		int buffer = 200;
		int index = 0;
		
		while(index < buffer){
			if(tokens[index].toLowerCase().contains(metal.toString().toLowerCase())){
				return true;
			}
			index++;
		}
		return false;
	}
	
	/**
	 * Find plan.
	 *
	 * @param planType the plan type
	 * @return the boolean
	 */
	public Boolean findPlan(Plan planType){
		int buffer = 200;
		int index = 0;
		
		while(index < buffer){
			if(tokens[index].toLowerCase().contains(planType.toString().toLowerCase())){
				return true;
			}
			index++;
		}
		return false;
	}
	
}
