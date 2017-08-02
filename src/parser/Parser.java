package parser;

import components.Main.Carrier;
import names.Product_Name.Metal;
import names.Product_Name.Plan;

public class Parser {

	final String[] tokens;
	
	final Carrier carrier;
	
	public Parser(String[] tokens, Carrier carrier) {
		super();
		this.tokens = tokens;
		this.carrier = carrier;
	}
	
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
