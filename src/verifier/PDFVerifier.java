package verifier;

import components.Main.Carrier;
import names.ProductName;
import names.ProductName.Metal;
import names.ProductName.Plan;
import names.ProductName.PlanAttribute;

// TODO: Auto-generated Javadoc
/**
 * The Class Parser.
 */
public class PDFVerifier {

	/** The tokens. */
	static String[] tokens;

	/** The carrier. */
	static Carrier carrier;

	public static int estimatedIndex;

	public int matchesCarrier;

	public int matchesMetal;

	public int matchesPlan;

	public int matchesPlanAttribute;

	public int matchesHSAAttribute;

	public int matchesHRAAttribute;

	public int matchesPlusAttribute;

	public Carrier pdfCarrier;

	public Metal pdfMetal;

	public Plan pdfPlan;

	public PlanAttribute pdfPlanAtt;

	/**
	 * Instantiates a new parser.
	 *
	 * @param tokens
	 *            the tokens
	 * @param carrier
	 *            the carrier
	 */
	public PDFVerifier(String[] tokens) {
		super();
		PDFVerifier.tokens = tokens;
		estimatedIndex = 0;
	}

	public void verifyAttributes(ProductName productName) {
		matchesCarrier = matchesCarrier(productName.carrier);
		matchesMetal = matchesMetal(productName.metal);
		matchesPlan = matchesPlan(productName.plan);
		matchesPlanAttribute = matchesPlanAttribute(productName.planAtt);
		matchesHSAAttribute = matchesHSAAttribute(productName.hasHSAAttribute());
		matchesHRAAttribute = matchesHRAAttribute(productName.hasHRAAttribute());
		matchesPlusAttribute = matchesPlusAttribute(productName.hasPlusAttribute());
	}

	public int matchesCarrier(Carrier nameCarrier) {
		pdfCarrier = findCarrier();
		if (nameCarrier == pdfCarrier) {
			return 0;
		} else if (pdfCarrier != Carrier.NONE) {
			return 1;
		} else {
			return -1;
		}
	}

	public int matchesMetal(Metal nameMetal) {
		pdfMetal = findMetal();
		if (nameMetal == pdfMetal) {
			return 0;
		} else if (pdfMetal != Metal.None) {
			return 1;
		} else {
			return -1;
		}
	}

	public int matchesPlan(Plan namePlan) {
		pdfPlan = findPlan();
		if (namePlan == pdfPlan) {
			return 0;
		} else if (pdfPlan != Plan.None) {
			return 1;
		} else {
			return -1;
		}
	}

	public int matchesPlanAttribute(PlanAttribute planAtt) {
		pdfPlanAtt = findPlanAttribute();
		if (planAtt == pdfPlanAtt) {
			return 0;
		} else if (pdfPlanAtt != PlanAttribute.None) {
			return 1;
		} else {
			return -1;
		}
	}

	public int matchesPlusAttribute(Boolean planPlusBool) {
		if (findPlusAttribute() == planPlusBool) {
			return 0;
		}
		return planPlusBool ? -1 : 1;
	}

	public int matchesHSAAttribute(Boolean planHSABool) {
		if (findHSAAttribute() == planHSABool) {
			return 0;
		}
		return planHSABool ? -1 : 1;
	}

	public int matchesHRAAttribute(Boolean planHRABool) {
		if (findHRAAttribute() == planHRABool) {
			return 0;
		}
		return planHRABool ? -1 : 1;
	}

	/**
	 * Find carrier
	 *
	 * @param carrier
	 *            the carrier
	 * @return true if carrier was found, false otherwise
	 */
	public Carrier findCarrier() {
		int buffer = 200;
		int index = 0;
		while (index < buffer) {
			for (Carrier c : Carrier.values()) {
				if (tokens[index].contains(c.toString().toLowerCase())) {
					return c;
				}
			}
			index++;
		}
		return Carrier.NONE;
	}

	/**
	 * Find product name.
	 *
	 * @param name
	 *            the name
	 * @return true if product name was found, false otherwise
	 */
	public Boolean findProductName(String name) {
		String[] name_tokens = name.toLowerCase().split("\\s");
		int buffer = 200;
		int index = 0;
		boolean doesMatch = true;

		while (index < buffer) {
			if (tokens[index].contains(name_tokens[0])) {
				int temp_index = index;
				for (String s : name_tokens) {
					if (!tokens[temp_index].contains(s)) {
						doesMatch = false;
						break;
					}
					temp_index++;
				}
				if (doesMatch) {
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
	 * @param metal
	 *            the metal
	 * @return the boolean
	 */
	public static Metal findMetal() {
		int buffer = 200;
		int index = 0;

		while (index < buffer) {
			for (Metal metal : Metal.values()) {
				if (tokens[index].contains(metal.toString().toLowerCase())) {
					estimatedIndex = index;
					return metal;
				}
			}
			index++;
		}
		return Metal.None;
	}

	/**
	 * Find plan.
	 *
	 * @param planType
	 *            the plan type
	 * @return the boolean
	 */
	public static Plan findPlan() {
		int buffer;
		int index = 0;
		if (estimatedIndex != 0) {
			buffer = 30;
			int startIndex = estimatedIndex - buffer / 2 < 0 ? 0 : estimatedIndex - buffer / 2;
			while (index < startIndex + buffer) {
				for (Plan planType : Plan.values()) {
					if (tokens[index].contains(planType.toString().toLowerCase())) {
						return planType;
					}
				}
				index++;
			}
		} else {
			buffer = 200;
			while (index < buffer) {
				for (Plan planType : Plan.values()) {
					if (tokens[index].contains(planType.toString().toLowerCase())) {
						estimatedIndex = index;
						return planType;
					}
				}
				index++;
			}
		}
		return Plan.None;
	}

	/**
	 * Find plan.
	 *
	 * @param planType
	 *            the plan type
	 * @return the boolean
	 */
	public static PlanAttribute findPlanAttribute() {
		int buffer = 200;
		int index = 0;
		int startIndex = 0;
		if (estimatedIndex != 0) {
			buffer = 30;
			startIndex = estimatedIndex - buffer / 2 < 0 ? 0 : estimatedIndex - buffer / 2;
			index = startIndex;
		}
		while (index < startIndex + buffer) {
			for (PlanAttribute planAtt : PlanAttribute.values()) {
				String[] planAttToks = planAtt.toString().toLowerCase().split("_");
				if (tokens[index].contains(planAttToks[0])) {
					int temp_index = index;
					Boolean doesMatch = true;
					for (String planTok : planAttToks) {
						if (!tokens[temp_index].contains(planTok)) {
							doesMatch = false;
						}
						temp_index++;
					}
					if (doesMatch) {
						return planAtt;
					}
				}
			}
			index++;
		}
		return PlanAttribute.None;
	}

	public static Boolean findPlusAttribute() {
		int buffer;
		int index = 0;
		if (estimatedIndex != 0) {
			buffer = 30;
			int startIndex = estimatedIndex - buffer / 2 < 0 ? 0 : estimatedIndex - buffer / 2;
			while (index < startIndex + buffer) {
				if (tokens[index].contains("plus") || tokens[index].contains("+")) {
					return true;
				}
				index++;
			}
		} else {
			buffer = 200;
			while (index < buffer) {
				if (tokens[index].contains("plus") || tokens[index].contains("+")) {
					estimatedIndex = index;
					return true;
				}
				index++;
			}
		}
		return false;
	}

	public static Boolean findHSAAttribute() {
		int buffer;
		int index = 0;
		if (estimatedIndex != 0) {
			buffer = 30;
			int startIndex = estimatedIndex - buffer / 2;
			while (index < startIndex + buffer) {
				if (tokens[index].contains("hsa")) {
					return true;
				}
				index++;
			}
		} else {
			buffer = 200;
			while (index < buffer) {
				if (tokens[index].contains("hsa")) {
					estimatedIndex = index;
					return true;
				}
				index++;
			}
		}
		return false;
	}

	public static Boolean findHRAAttribute() {
		int buffer;
		int index = 0;
		if (estimatedIndex != 0) {
			buffer = 30;
			int startIndex = estimatedIndex - buffer / 2 < 0 ? 0 : estimatedIndex - buffer / 2;
			while (index < startIndex + buffer) {
				if (tokens[index].contains("hra")) {
					return true;
				}
				index++;
			}
		} else {
			buffer = 200;
			while (index < buffer) {
				if (tokens[index].contains("hra")) {
					estimatedIndex = index;
					return true;
				}
				index++;
			}
		}
		return false;
	}

}
