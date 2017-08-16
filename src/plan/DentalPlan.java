package plan;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DentalPlan.
 */
public class DentalPlan implements Plan {
	
	/** The group. */
	public String group;
	
	/** The carrier. */
	public String carrier;
	
	/** The carrier id. */
	public int carrier_id;
	
	/** The product name. */
	public String product_name;
	
	/** The sic level. */
	public String sic_level;
	
	/** The start date. */
	public String start_date;
	
	/** The end date. */
	public String end_date;
	
	/** The states. */
	public String states;
	
	/** The group rating areas. */
	public String group_rating_areas;
	
	/** The zip codes. */
	public String zip_codes;
	
	/** The contribution type. */
	public String contribution_type;
	
	/** The minimum enrolled. */
	public String minimum_enrolled;
	
	/** The minimum participation. */
	public String minimum_participation;
	
	/** The class I diagnostic preventive. */
	public String class_I_diagnostic_preventive;
	
	/** The class I I basic. */
	public String class_II_basic;
	
	/** The class II I major. */
	public String class_III_major;
	
	/** The endodonitcs. */
	public String endodonitcs;
	
	/** The periodontics. */
	public String periodontics;
	
	/** The annual max. */
	public String annual_max;
	
	/** The office visit copay. */
	public String office_visit_copay;
	
	/** The deductible ind fam. */
	public String deductible_ind_fam;
	
	/** The orthodontics. */
	public String orthodontics;
	
	/** The orthodonitics lifetime maximum. */
	public String orthodonitics_lifetime_maximum;
	
	/** The waiting period. */
	public String waiting_period;
	
	/** The rc mac. */
	public String rc_mac;
	
	/** The one tier. */
	public String one_tier;
	
	/** The two tier e. */
	public String two_tier_e;
	
	/** The two tier f. */
	public String two_tier_f;
	
	/** The three tier e. */
	public String three_tier_e;
	
	/** The three tier ed. */
	public String three_tier_ed;
	
	/** The three tier f. */
	public String three_tier_f;
	
	/** The four tier e. */
	public String four_tier_e;
	
	/** The four tier ea. */
	public String four_tier_ea;
	
	/** The four tier ec. */
	public String four_tier_ec;
	
	/** The four tier f. */
	public String four_tier_f;
	
	/**
	 * Instantiates a new dental plan.
	 */
	public DentalPlan() {
		this.group = "";
		this.carrier = "";
		this.carrier_id = 0;
		product_name = "";
		sic_level = "";
		start_date = "";
		end_date = "";
		states = "";
		group_rating_areas = "";
		zip_codes = "";
		contribution_type = "";
		minimum_enrolled = "";
		minimum_participation = "";
		class_I_diagnostic_preventive = "";
		class_II_basic = "";
		class_III_major = "";
		endodonitcs = "";
		periodontics = "";
		annual_max = "";
		office_visit_copay = "";
		deductible_ind_fam = "";
		orthodontics = "";
		orthodonitics_lifetime_maximum = "";
		waiting_period = "";
		rc_mac = "";
		one_tier = "";
		two_tier_e = "";
		two_tier_f = "";
		three_tier_e = "";
		three_tier_ed = "";
		three_tier_f = "";
		four_tier_e = "";
		four_tier_ea = "";
		four_tier_ec = "";
		four_tier_f = "";
	}
	
	/* (non-Javadoc)
	 * @see plan.Plan#printPlan()
	 */
	@Override
	public void printPlan() {
		System.out.println("Group: " + this.group);
		System.out.println("Carrier: " + this.carrier);
		System.out.println("Carrier ID: " + this.carrier_id);
		System.out.println("Product name: " + this.product_name);
		System.out.println("Sic level: " + this.sic_level);
		System.out.println("Start date: " + this.start_date);
		System.out.println("End date: " + this.end_date);
		System.out.println("States: " + this.states);
		System.out.println("Group rating areas: " + this.group_rating_areas);
		System.out.println("Zip codes: " + this.zip_codes);
		System.out.println("Contribution type: " + this.contribution_type);
		System.out.println("Minimum enrolled: " + this.minimum_enrolled);
		System.out.println("Minimum participation: " + this.minimum_participation);
		System.out.println("Class I Diagnostic & Preventative: " + this.class_I_diagnostic_preventive);
		System.out.println("Class II Basic: " + this.class_II_basic);
		System.out.println("Class II Major: " + this.class_III_major);
		System.out.println("Endodonitcs: " + this.endodonitcs);
		System.out.println("Periodontics: " + this.periodontics);
		System.out.println("Annual max: " + this.annual_max);
		System.out.println("Office visit copay: " + this.office_visit_copay);
		System.out.println("Deductible Individual/Family: " + this.deductible_ind_fam);
		System.out.println("Orthodontics: " + this.orthodontics);
		System.out.println("Orthodontics lifetime maximum: " + this.orthodonitics_lifetime_maximum);
		System.out.println("Waiting period: " + this.waiting_period);
		System.out.println("R&C/MAC: " + this.rc_mac);
		System.out.println("One Tier: " + this.one_tier);
		System.out.println("Two Tier E: " + this.two_tier_e);
		System.out.println("Two Tier F: " + this.two_tier_f);
		System.out.println("Three Tier E: " + this.three_tier_e);
		System.out.println("Three Tier ED: " + this.three_tier_ed);
		System.out.println("Three Tier F: " + this.three_tier_f);
		System.out.println("Four Tier E: " + this.four_tier_e);
		System.out.println("Four Tier EA: " + this.four_tier_ea);
		System.out.println("Four Tier EC: " + this.four_tier_ec);
		System.out.println("Four Tier F: " + this.four_tier_f);
	}
	
	/* (non-Javadoc)
	 * @see plan.Plan#format()
	 */
	public void format(){
		
	}

	/* (non-Javadoc)
	 * @see plan.Plan#computeStatistics()
	 */
	@Override
	public void computeStatistics() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see plan.Plan#hasErrors()
	 */
	@Override
	public boolean hasErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see plan.Plan#getErrors()
	 */
	@Override
	public ArrayList<PlanError> getErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see plan.Plan#addError(plan.PlanError)
	 */
	@Override
	public void addError(PlanError e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see plan.Plan#hasTobaccoRates()
	 */
	@Override
	public boolean hasTobaccoRates() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see plan.Plan#getWarnings()
	 */
	@Override
	public ArrayList<PlanWarning> getWarnings() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see plan.Plan#hasWarnings()
	 */
	@Override
	public boolean hasWarnings() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see plan.Plan#addWarning(plan.PlanWarning)
	 */
	@Override
	public void addWarning(PlanWarning w) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see plan.Plan#hasPDFUrl()
	 */
	@Override
	public boolean hasPDFUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see plan.Plan#getProductName()
	 */
	@Override
	public String getProductName() {
		// TODO Auto-generated method stub
		return null;
	}
}
