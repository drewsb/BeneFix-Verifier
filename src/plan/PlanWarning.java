package plan;

import components.Attribute;
import components.Attribute.AttributeType;

public class PlanWarning {

	final Warning warningType;
	final AttributeType attribute;
	final RateType type;
	final String incorrectVal;
	final String correctVal;
	
	String ageBand = "";
	String secondPlanName = "";
	
	public enum Warning{
		MISSING_ATTRIBUTE, METAL_NOT_FOUND, PLAN_TYPE_NOT_FOUND
	}

	public PlanWarning(Warning warning, AttributeType attribute, RateType type, String incorrectVal, String correctVal) {
		this.warningType = warning;
		this.attribute = attribute;
		this.type = type;
		this.incorrectVal = incorrectVal;
		this.correctVal = correctVal;
	}
	
	public String getWarningType(){
		return warningType.toString();
	}
	
	public void setAgeBand(String age){
		this.ageBand = age;
	}
	
	public void setSecondPlanName(String name){
		this.secondPlanName = name;
	}
	
	public String getWarningMessage(){
		switch(warningType){
		case MISSING_ATTRIBUTE:
			return String.format("This report is missing the %s attribute.", attribute.toString().toLowerCase());
		case METAL_NOT_FOUND:
			return String.format("Could not determine metal from product name %s.", incorrectVal);
		case PLAN_TYPE_NOT_FOUND:
			return String.format("Could not determine plan type from product name %s.", incorrectVal);
		}
		
		return "N/A";
	}
	
	
	public enum RateType {
		NONE, NON_TOBACCO, TOBACCO
	}
}
