package plan;

import components.Attribute;
import components.Attribute.AttributeType;

public class PlanError {

	final Error errorType;
	private final AttributeType attribute;
	private final RateType type;
	final String incorrectVal;
	final String correctVal;

	String ageBand = "";
	String secondPlanName = "";

	public enum Error {
		INVALID_FILE, MONOTONOCITY, CV, PDF_METAL_MISMATCH, PDF_PLAN_MISMATCH, MISSING_ATTRIBUTE
	}

	public PlanError(Error error, AttributeType attribute, RateType type, String incorrectVal, String correctVal) {
		this.errorType = error;
		this.attribute = attribute;
		this.type = type;
		this.incorrectVal = incorrectVal;
		this.correctVal = correctVal;
	}

	public String getErrorType() {
		return errorType.toString();
	}

	public void setAgeBand(String age) {
		this.ageBand = age;
	}

	public void setSecondPlanName(String name) {
		this.secondPlanName = name;
	}

	public String getErrorMessage() {
		switch (errorType) {
		case INVALID_FILE:
			return String.format("Invalid file.");
		case MONOTONOCITY:
			return String.format("Negative rate difference found: %s difference across ages %s.", incorrectVal,
					ageBand);
		case CV:
			return String.format("CV: %s. Does not match previous plan's CV: %s", incorrectVal, correctVal);
		case PDF_METAL_MISMATCH:
			return String.format("Could not found %s metal in attached PDF", incorrectVal);
		case PDF_PLAN_MISMATCH:
			return String.format("Could not found %s plan type in attached PDF", incorrectVal);
		case MISSING_ATTRIBUTE:
			return String.format("Missing attribute %s.", getAttribute().toString().toLowerCase());
		}
		return "N/A";
	}

	public AttributeType getAttribute() {
		return attribute;
	}

	public RateType getType() {
		return type;
	}

	public enum RateType {
		NONE, NON_TOBACCO, TOBACCO
	}
}
