package components;

import components.Attribute.AttributeType;

public class PlanError {
	
	final Error errorType;
	final AttributeType attribute;
	final RateType type;
	final String incorrectVal;
	final String correctVal;
	
	String ageBand = "";
	String secondPlanName = "";
	
	public enum Error{
		INVALID_FILE, MONOTONOCITY, CV, PDF_MISMATCH
	}

	public PlanError(Error error, AttributeType attribute, RateType type, String incorrectVal, String correctVal) {
		this.errorType = error;
		this.attribute = attribute;
		this.type = type;
		this.incorrectVal = incorrectVal;
		this.correctVal = correctVal;
	}
	
	public String getErrorType(){
		return errorType.toString();
	}
	
	public void setAgeBand(String age){
		this.ageBand = age;
	}
	
	public void setSecondPlanName(String name){
		this.secondPlanName = name;
	}
	
	public String getErrorMessage(){
		switch(errorType){
		case INVALID_FILE:
			return String.format("Invalid file.");
		case MONOTONOCITY:
			return String.format("Negative rate difference found: %s difference across ages %s.", incorrectVal, ageBand);
		case CV:
			return String.format("CV: %s. Does not match previous plan's CV: %s", incorrectVal, correctVal);
		case PDF_MISMATCH:
			return String.format("PDF Mismatch. %s does not match given data.", incorrectVal);
		}
		return "N/A";
	}
	
	
	public enum RateType {
		NONE, NON_TOBACCO, TOBACCO
	}

}
