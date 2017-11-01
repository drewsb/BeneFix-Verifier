package names;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;

import components.Main.Carrier;

// TODO: Auto-generated Javadoc
/**
 * The Class Product_Name.
 */
public class ProductName {

	/** The original name. */
	public final String original_name;

	/** The name tokens. */
	public final String[] name_tokens;

	/** The state. */
	public State state;

	/** The metal. */
	public Metal metal;

	/** The plan. */
	public Plan plan;

	/** The carrier. */
	public Carrier carrier;

	/** The Plan Attribute */
	public PlanAttribute planAtt;

	/** The rx copay. */
	public String rx_copay;

	/** The deductible. */
	public String deductible;

	/** The coinsurance. */
	public String coinsurance;

	/** is HSA plan. */
	public boolean isHSAPlan;

	/** is HRA plan. */
	public boolean isHRAPlan;

	/** is Extra plan. */
	public boolean isExtraPlan;

	/** The is plus plan. */
	public boolean isPlusPlan;

	/** The is Premier plan. */
	public boolean isPremierPlan;

	/** The is advantage plan. */
	public boolean isAdvantagePlan;

	/** The is off exchange plan. */
	public boolean isOffExchangePlan;

	/** The Constant states. */
	public final static ArrayList<State> states = new ArrayList<State>(Arrays.asList(State.values()));

	/** The Constant plans. */
	public final static ArrayList<Plan> plans = new ArrayList<Plan>(Arrays.asList(Plan.values()));

	/** The Constant metals. */
	public final static ArrayList<Metal> metals = new ArrayList<Metal>(Arrays.asList(Metal.values()));

	/** The Constant carriers. */
	public final static ArrayList<Carrier> carriers = new ArrayList<Carrier>(Arrays.asList(Carrier.values()));

	public final static PlanAttribute[] aetnaAttributes = { PlanAttribute.Health_Savings, PlanAttribute.Savings_Plus,
			PlanAttribute.WellSpan, PlanAttribute.LVHN, PlanAttribute.Pinnacle, PlanAttribute.Commonwealth };

	public final static PlanAttribute[] geisingerAttributes = { PlanAttribute.Marketplace };

	/** The metal abbrev map. */
	public final HashMap<Metal, String[]> metalAbbrevMap = new HashMap<Metal, String[]>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(Metal.Bronze, new String[] { "brz" });
			put(Metal.Silver, new String[] { "slv" });
			put(Metal.Gold, new String[] { "gld" });
			put(Metal.Platinum, new String[] { "plt" });
			put(Metal.None, new String[] {});
		}
	};

	/** The metal abbrev map. */
	public final HashMap<PlanAttribute, String[]> planAttAbbrevMap = new HashMap<PlanAttribute, String[]>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(PlanAttribute.Small_Business_Advantage, new String[] { "sba" });
		}
	};

	/**
	 * Instantiates a new product name.
	 *
	 * @param original_name
	 *            the original name
	 */
	public ProductName(String original_name) {
		super();
		this.original_name = original_name;
		this.name_tokens = original_name.toLowerCase().split("\\s");
		this.carrier = getCarrier();
		this.metal = getMetal();
		this.plan = getPlan();
		this.planAtt = getPlanAttribute();
		this.isHSAPlan = hasHSAAttribute();
		this.isHRAPlan = hasHRAAttribute();
		this.isExtraPlan = hasExtraAttribute();
		this.isPlusPlan = hasPlusAttribute();
		this.isPremierPlan = hasPremierAttribute();
	}

	/**
	 * The Enum State.
	 */
	public enum State {
		PA, OH, NJ, CA
	}

	/**
	 * The Enum Metal.
	 */
	public enum Metal {
		Bronze, Silver, Gold, Platinum, None
	}

	/**
	 * The Enum Plan.
	 */
	public enum Plan {
		CareConnect, Choice, EPO, DPOS, HMO, HNOption, HNOnly, Indemnity, POS, PPO, QPOS, None
	}

	public enum PlanAttribute {
		Balance, Health_Savings, High_Deductible, Custom, Savings_Plus, WellSpan, LVHN, Pinnacle, Commonwealth, Highlands, Marketplace, Flex, QHD, Small_Business_Advantage, None
	}
	
	public enum ProductNameAttribute {
		Carrier, Metal, Plan, PlanAtt, HSA, HRA, Plus
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return original_name;
	}

	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}

	public Carrier getCarrier() {
		for (String t : name_tokens) {
			for (Carrier c : Carrier.values()) {
				if (t.contains(c.toString().toLowerCase())) {
					return c;
				}
			}
		}
		return Carrier.NONE;
	}

	/**
	 * Gets the metal.
	 *
	 * @return the metal
	 */
	public Metal getMetal() {
		for (String t : name_tokens) {
			for (Metal m : metals) {
				if (t.contains(m.toString().toLowerCase())) {
					return m;
				}
				for (String abbrev : metalAbbrevMap.get(m)) {
					if (t.contains(abbrev)) {
						return m;
					}
				}
			}
		}
		return Metal.None;
	}

	/**
	 * Gets the plan.
	 *
	 * @return the plan
	 */
	public Plan getPlan() {
		String[] strToks = original_name.toLowerCase().split("\\s");
		for (String str : strToks) {
			for (Plan p : plans) {
				if (p.toString().toLowerCase().equals(str)) {
					return p;
				}
			}
		}
		return Plan.None;
	}

	public PlanAttribute getPlanAttribute() {
		int index = 0;
		while(index < name_tokens.length) {
			for (PlanAttribute p : PlanAttribute.values()) {
				int temp_index = index;
				Boolean doesMatch = true;
				String[] planToks = p.toString().toLowerCase().split("_");
				for (String planTok : planToks) {
					if (!planTok.equals(name_tokens[temp_index++])) {
						doesMatch = false;
						break;
					}
				}
				if (planAttAbbrevMap.containsKey(p)) {
					doesMatch = true;
					temp_index = index;
					for (String abbrev : planAttAbbrevMap.get(p)) {
						planToks = abbrev.toString().split(" ");
						for (String planTok : planToks) {
							if (!planTok.equals(name_tokens[temp_index++])) {
								doesMatch = false;
								break;
							}
						}
					}
				}
				if (doesMatch) {
					return p;
				}

			}
			index++;
		}
		return PlanAttribute.None;
	}

	public Boolean hasHSAAttribute() {
		for (String nameTok : name_tokens) {
			if (nameTok.contains("hsa")) {
				return true;
			}
		}
		return false;
	}

	public Boolean hasHRAAttribute() {
		for (String nameTok : name_tokens) {
			if (nameTok.contains("hra")) {
				return true;
			}
		}
		return false;
	}

	public Boolean hasExtraAttribute() {
		for (String nameTok : name_tokens) {
			if (nameTok.equals("extra")) {
				return true;
			}
		}
		return false;
	}

	public Boolean hasPlusAttribute() {
		for (String nameTok : name_tokens) {
			if (nameTok.contains("plus") || nameTok.contains("+")) {
				return true;
			}
		}
		return false;
	}

	public Boolean hasPremierAttribute() {
		for (String nameTok : name_tokens) {
			if (nameTok.equals("premier")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Matches.
	 *
	 * @param p_name
	 *            the p name
	 * @return true, if successful
	 */
	public boolean matches(String p_name) {
		String[] temp_tokens = p_name.toLowerCase().split("\\s");
		int index = 0;
		for (String s : name_tokens) {
			if (!s.equals(temp_tokens[index])) {
				return false;
			}
			index++;
		}
		return true;
	}

}
