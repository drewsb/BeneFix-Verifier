package plan;

import components.Attribute;
import components.Attribute.AttributeType;
import plan.PlanError.RateType;

public class PlanWarning {

	final Warning warningType;
	final AttributeType attribute;
	final RateType type;
	final String incorrectVal;
	final String correctVal;
	
	String ageBand = "";
	AttributeType secondAtt;
	
	public enum Warning{
		MISSING_ATTRIBUTE, METAL_NOT_FOUND, PLAN_TYPE_NOT_FOUND, INVALID_PDF_LINK, UNRECOGNIZED_FORMAT
	}

	public PlanWarning(Warning warning, AttributeType attribute, RateType type, String incorrectVal, String correctVal) {
		this.warningType = warning;
		this.attribute = attribute;
		this.type = type;
		this.incorrectVal = incorrectVal;
		this.correctVal = correctVal;
	}
	
	public Warning getWarningType(){
		return warningType;
	}
	
	public AttributeType getAttributeType(){
		return attribute;
	}
	
	public void setAgeBand(String age){
		this.ageBand = age;
	}
	
	public String getWarningMessage(){
		switch(warningType){
		case MISSING_ATTRIBUTE:
			return String.format("This report is missing the %s attribute.", attribute.toString().toLowerCase());
		case METAL_NOT_FOUND:
			return String.format("Could not determine metal from product name %s.", incorrectVal);
		case PLAN_TYPE_NOT_FOUND:
			return String.format("Could not determine plan type from product name %s.", incorrectVal);
		case INVALID_PDF_LINK:
			return String.format("Invalid PDF link: %s.", incorrectVal);
		case UNRECOGNIZED_FORMAT:
			return String.format("Could not recognize format of %s: %s", attribute.toString().toLowerCase(), incorrectVal);
		}
		
		return "N/A";
	}

	
}
