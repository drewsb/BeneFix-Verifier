package plan;

import components.Attribute.AttributeType;
import plan.PlanError.RateType;

// TODO: Auto-generated Javadoc
/**
 * The Class PlanWarning.
 */
public class PlanWarning {

	/** The warning type. */
	final Warning warningType;
	
	/** The attribute. */
	final AttributeType attribute;
	
	/** The type. */
	final RateType type;
	
	/** The incorrect val. */
	final String incorrectVal;
	
	/** The correct val. */
	final String correctVal;
	
	/** The age band. */
	String ageBand = "";
	
	/** The second att. */
	AttributeType secondAtt;
	
	/**
	 * The Enum Warning.
	 */
	public enum Warning{
		
		/** The missing attribute. */
		MISSING_ATTRIBUTE, 
 /** The metal not found. */
 METAL_NOT_FOUND, 
 /** The plan type not found. */
 PLAN_TYPE_NOT_FOUND, 
 /** The invalid pdf link. */
 INVALID_PDF_LINK, 
 /** The unrecognized format. */
 UNRECOGNIZED_FORMAT
	}

	/**
	 * Instantiates a new plan warning.
	 *
	 * @param warning the warning
	 * @param attribute the attribute
	 * @param type the type
	 * @param incorrectVal the incorrect val
	 * @param correctVal the correct val
	 */
	public PlanWarning(Warning warning, AttributeType attribute, RateType type, String incorrectVal, String correctVal) {
		this.warningType = warning;
		this.attribute = attribute;
		this.type = type;
		this.incorrectVal = incorrectVal;
		this.correctVal = correctVal;
	}
	
	/**
	 * Gets the warning type.
	 *
	 * @return the warning type
	 */
	public Warning getWarningType(){
		return warningType;
	}
	
	/**
	 * Gets the attribute type.
	 *
	 * @return the attribute type
	 */
	public AttributeType getAttributeType(){
		return attribute;
	}
	
	/**
	 * Sets the age band.
	 *
	 * @param age the new age band
	 */
	public void setAgeBand(String age){
		this.ageBand = age;
	}
	
	/**
	 * Gets the warning message.
	 *
	 * @return the warning message
	 */
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
