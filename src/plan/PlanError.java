package plan;

import components.Attribute;
import components.Attribute.AttributeType;
import components.Formatter;

public class PlanError {

	private final Error errorType;
	private final AttributeType attribute;
	private final RateType type;
	final String incorrectVal;
	final String correctVal;

	String ageBand = "";
	String secondPlanName = "";
	String secondIncorrectVal;
	AttributeType secondAttribute;

	public enum Error {
		INVALID_FILE, MONOTONOCITY, CV, PDF_METAL_MISMATCH, PDF_PLAN_MISMATCH, MISSING_ATTRIBUTE, INDIV_FAMILY_INCREMENT, OON_INCREMENT, TOBACCO_INCREMENT
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

	public void addSecondPlanName(String name) {
		this.secondPlanName = name;
	}

	public void addSecondAttribute(AttributeType att) {
		this.secondAttribute = att;
	}

	public void addSecondIncorrectVal(String s) {
		secondIncorrectVal = s;
	}

	public Boolean hasSecondAttribute() {
		return secondAttribute != null;
	}

	public AttributeType getSecondAttribute() {
		return secondAttribute;
	}

	public String getErrorMessage() {
		switch (errorType) {
		case INVALID_FILE:
			return String.format("Invalid file.");
		case MONOTONOCITY:
			return String.format("Negative rate difference found: %s difference across ages %s.", incorrectVal,
					ageBand);
		case CV:
			return String.format("Inconsistent CV across %s rates. " + "Current plan's CV: %s. Previous plan's CV: %s",
					type.toString().toLowerCase().replaceAll("_", "-"), incorrectVal, correctVal);
		case PDF_METAL_MISMATCH:
			return String.format("Could not find %s metal in attached PDF.", incorrectVal);
		case PDF_PLAN_MISMATCH:
			return String.format("Could not find %s plan type in attached PDF.", incorrectVal);
		case MISSING_ATTRIBUTE:
			return String.format("Missing attribute %s.", getAttribute().toString().toLowerCase());
		case INDIV_FAMILY_INCREMENT:
			return String.format("%s is less than %s. %s: %s. %s: %s.",
					Formatter.capitalize(secondAttribute.toString()), attribute.toString().toLowerCase(),
					Formatter.capitalize(attribute.toString()), incorrectVal,
					Formatter.capitalize(secondAttribute.toString()), secondIncorrectVal);
		case OON_INCREMENT:
			return String.format("%s is less than %s. %s: %s. %s: %s.",
					Formatter.capitalize(secondAttribute.toString()), attribute.toString().toLowerCase(),
					Formatter.capitalize(attribute.toString()), incorrectVal,
					Formatter.capitalize(secondAttribute.toString()), secondIncorrectVal);
		case TOBACCO_INCREMENT:
			return String.format(
					"Incremental error across tobacco and non-tobacco rates at age %s. "
							+ "Non-tobacco rate: %s. Tobacco rate: %s.",
					attribute.toString().toLowerCase(), incorrectVal, secondIncorrectVal);
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
		NONE, NON_TOBACCO, TOBACCO, BOTH
	}
}
