package plan;

import java.net.URL;

import components.Attribute.AttributeType;
import components.Formatter;
import names.ProductName.ProductNameAttribute;

// TODO: Auto-generated Javadoc
/**
 * The Class PlanError.
 */
public class PlanError {

	/** The error type. */
	private final Error errorType;

	/** The attribute. */
	private final AttributeType attribute;

	/** The type. */
	private final RateType type;

	/** The incorrect val. */
	final String incorrectVal;

	/** The correct val. */
	final String correctVal;

	/** The age band. */
	String ageBand = "";

	/** The second plan name. */
	String secondPlanName = "";

	/** The second incorrect val. */
	String secondIncorrectVal;

	/** The second attribute. */
	AttributeType secondAttribute;

	ProductNameAttribute nameAtt;

	String pdfURL;

	/**
	 * The Enum Error.
	 */
	public enum Error {
		PDF_ATTRIBUTE_MISMATCH, PDF_MISMATCH, INVALID_FILE, MONOTONOCITY, CV, MISSING_ATTRIBUTE, INDIV_FAMILY_INCREMENT, OON_INCREMENT, TOBACCO_INCREMENT
	}

	/**
	 * Instantiates a new plan error.
	 *
	 * @param error
	 *            the error
	 * @param attribute
	 *            the attribute
	 * @param type
	 *            the type
	 * @param incorrectVal
	 *            the incorrect val
	 * @param correctVal
	 *            the correct val
	 */
	public PlanError(Error error, AttributeType attribute, RateType type, String incorrectVal, String correctVal) {
		this.errorType = error;
		this.attribute = attribute;
		this.type = type;
		this.incorrectVal = incorrectVal;
		this.correctVal = correctVal;
	}

	/**
	 * Gets the error type.
	 *
	 * @return the error type
	 */
	public String getErrorType() {
		return errorType.toString();
	}

	/**
	 * Sets the age band.
	 *
	 * @param age
	 *            the new age band
	 */
	public void setAgeBand(String age) {
		this.ageBand = age;
	}

	public void setProductNameAttribute(ProductNameAttribute nameAtt) {
		this.nameAtt = nameAtt;
	}

	public void setPDFURL(String s) {
		this.pdfURL = s;
	}

	public String getPDFURL() {
		return this.pdfURL;
	}

	/**
	 * Adds the second plan name.
	 *
	 * @param name
	 *            the name
	 */
	public void addSecondPlanName(String name) {
		this.secondPlanName = name;
	}

	/**
	 * Adds the second attribute.
	 *
	 * @param att
	 *            the att
	 */
	public void addSecondAttribute(AttributeType att) {
		this.secondAttribute = att;
	}

	/**
	 * Adds the second incorrect val.
	 *
	 * @param s
	 *            the s
	 */
	public void addSecondIncorrectVal(String s) {
		secondIncorrectVal = s;
	}

	/**
	 * Checks for second attribute.
	 *
	 * @return the boolean
	 */
	public Boolean hasSecondAttribute() {
		return secondAttribute != null;
	}

	/**
	 * Gets the second attribute.
	 *
	 * @return the second attribute
	 */
	public AttributeType getSecondAttribute() {
		return secondAttribute;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		switch (errorType) {
		case INVALID_FILE:
			return String.format("Invalid file.");
		case MONOTONOCITY:
			return String.format("Negative rate difference found in %s rates: %s difference across ages %s.",
					type.toString().toLowerCase().replaceAll("_", "-"), incorrectVal, ageBand);
		case CV:
			return String.format("Inconsistent CV across %s rates. " + "Current plan's CV: %s. Previous plan's CV: %s",
					type.toString().toLowerCase().replaceAll("_", "-"), incorrectVal, correctVal);
		case MISSING_ATTRIBUTE:
			return String.format("Missing attribute %s.", getAttribute().toString().toLowerCase());
		case PDF_MISMATCH:
			return String.format("PDF link does not match other PDF's with the same plan name");
		case PDF_ATTRIBUTE_MISMATCH:
			if (nameAtt == ProductNameAttribute.HRA || nameAtt == ProductNameAttribute.HSA
					|| nameAtt == ProductNameAttribute.Plus) {
				return String.format("Found %s attribute in PDF that doesn't appear in spreadsheet product name.",
						nameAtt.toString());
			} else if (nameAtt == ProductNameAttribute.PlanAtt) {
				return String.format(
						"Mismatch in plan attribute. Found %s attribute in attached PDF.",
						incorrectVal);
			} else {
				return String.format("Mismatch in %s attribute. Found %s %s in attached PDF.",
						nameAtt.toString().toLowerCase(), incorrectVal,
						nameAtt.toString().toLowerCase());
			}
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

	/**
	 * Gets the attribute.
	 *
	 * @return the attribute
	 */
	public AttributeType getAttribute() {
		return attribute;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public RateType getType() {
		return type;
	}

	/**
	 * The Enum RateType.
	 */
	public enum RateType {

		/** The none. */
		NONE,
		/** The non tobacco. */
		NON_TOBACCO,
		/** The tobacco. */
		TOBACCO,
		/** The both. */
		BOTH
	}
}
