package names;

import java.util.HashMap;


import components.Main.Carrier;

// TODO: Auto-generated Javadoc
/**
 * The Class PA_NEPA_Name.
 */
public class PA_NEPA_Name extends ProductName {

	/** The lower name. */
	String lower_name = original_name.toLowerCase();

	/** The tokens. */
	String[] tokens = lower_name.split("\\s");


	/**
	 * Instantiates a new p A NEP A name.
	 *
	 * @param original_name
	 *            the original name
	 */
	public PA_NEPA_Name(String original_name) {
		super(original_name);
		this.carrier = getCarrier();
		this.state = getState();
		this.metal = getMetal();
		this.plan = getPlan();
		this.rx_copay = getRxCopay();
		this.deductible = getDeductible();
		this.coinsurance = getCoinsurance();
		this.isPlusPlan = hasPlusAttribute();
	}

//	/** The plan type abbrev map. */
//	public final HashMap<Plan_Attribute, String[]> planTypeAbbrevMap = new HashMap<Plan_Attribute, String[]>() {
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 1L;
//
//		{
//			put(Plan_Attribute.National, new String[] { "ntl" });
//			put(Plan_Attribute.Preferred, new String[] { "pfd, prefd" });
//			put(Plan_Attribute.Value, new String[] { "val" });
//		}
//	};


	/**
	 * Gets the carrier.
	 *
	 * @return the carrier
	 */
	public Carrier getCarrier() {
		for (Carrier c : carriers) {
			if (lower_name.contains(c.toString().toLowerCase())) {
				return c;
			}
		}
		return Carrier.NONE;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public State getState() {
		return null;
	}

	/**
	 * Gets the rx copay.
	 *
	 * @return the rx copay
	 */
	public String getRxCopay() {
		// for(String s : tokens){
		// if(s.contains("rx")){
		//
		// }
		//
		// }
		return "";
	}

	/**
	 * Gets the deductible.
	 *
	 * @return the deductible
	 */
	public String getDeductible() {
		return "";
	}

	/**
	 * Gets the coinsurance.
	 *
	 * @return the coinsurance
	 */
	public String getCoinsurance() {
		return "";
	}

	/**
	 * Checks for HSA attribute.
	 *
	 * @return true, if successful
	 */
	public Boolean hasHSAAttribute() {
		String[] tokens = lower_name.split("\\s");
		for (String s : tokens) {
			if (s.equals("hsa")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks for advantage attribute.
	 *
	 * @return true, if successful
	 */
	public boolean hasAdvantageAttribute() {
		for (String s : tokens) {
			if (s.equals("hsa")) {
				return true;
			}
		}
		return false;
	}


}
