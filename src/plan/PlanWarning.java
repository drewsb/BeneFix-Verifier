package plan;

import components.Formatter;
import components.Attribute.AttributeType;
import names.ProductName.ProductNameAttribute;
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

	ProductNameAttribute nameAtt;
	
	String pdfURL;

	/**
	 * The Enum Warning.
	 */
	public enum Warning {
		PDF_ATTRIBUTE_MISSING, UNRECOGNIZED_PRODUCT_NAME_FORMAT, MISSING_ATTRIBUTE, INVALID_PDF_LINK, UNRECOGNIZED_FORMAT
	}

	/**
	 * Instantiates a new plan warning.
	 *
	 * @param warning
	 *            the warning
	 * @param attribute
	 *            the attribute
	 * @param type
	 *            the type
	 * @param incorrectVal
	 *            the incorrect val
	 * @param correctVal
	 *            the correct val
	 */
	public PlanWarning(Warning warning, AttributeType attribute, RateType type, String incorrectVal,
			String correctVal) {
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
	public Warning getWarningType() {
		return warningType;
	}

	/**
	 * Gets the attribute type.
	 *
	 * @return the attribute type
	 */
	public AttributeType getAttributeType() {
		return attribute;
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
	 * Gets the warning message.
	 *
	 * @return the warning message
	 */
	public String getWarningMessage() {
		switch (warningType) {
		case MISSING_ATTRIBUTE:
			return String.format("This report is missing the %s attribute.", attribute.toString().toLowerCase());
		case PDF_ATTRIBUTE_MISSING:
			if (nameAtt == ProductNameAttribute.PlanAtt) {
				return String.format("Could not find Plan attribute value %s in attached PDF", incorrectVal);
			} else if (nameAtt == ProductNameAttribute.HRA || nameAtt == ProductNameAttribute.HSA
					|| nameAtt == ProductNameAttribute.Plus) {
				if (!Boolean.valueOf(incorrectVal)) {
					return String.format("Found %s attribute in attached PDF; does not match product name.",
							nameAtt.toString(), incorrectVal);
				} else {
					return String.format("Could not find HSA attribute in attached PDF; does not match product name",
							nameAtt.toString(), incorrectVal);
				}
			} else {
				return String.format("Could not find %s attribute value %s in attached PDF",
						Formatter.capitalize(nameAtt.toString()), incorrectVal);
			}
		case UNRECOGNIZED_PRODUCT_NAME_FORMAT:
			return String.format("Could not determine %s attribute from product name %s.",
					nameAtt.toString().toLowerCase(), incorrectVal);
		case INVALID_PDF_LINK:
			return String.format("Invalid PDF link: %s.", incorrectVal);
		case UNRECOGNIZED_FORMAT:
			return String.format("Could not recognize format of %s: %s", attribute.toString().toLowerCase(),
					incorrectVal);
		}

		return "N/A";
	}

}
