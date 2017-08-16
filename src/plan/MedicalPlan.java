package plan;

import java.net.MalformedURLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import components.Main.Carrier;

// TODO: Auto-generated Javadoc
/**
 * Page class for a PDF page. Holds necessary data to populate an excel sheet.
 */
public class MedicalPlan implements Plan {
	
	/** The carrier. */
	public Carrier carrier;
	
	/** The carrier id. */
	public int carrier_id;
	
	/** The carrier plan id. */
	public String carrier_plan_id;
	
	/** The start date. */
	public String start_date;
	
	/** The end date. */
	public String end_date;
	
	/** The product name. */
	public String product_name;
	
	/** The plan pdf file name. */
	public String plan_pdf_file_name;
	
	/** The deductible indiv. */
	public String deductible_indiv;
	
	/** The deductible family. */
	public String deductible_family;
	
	/** The oon deductible indiv. */
	public String oon_deductible_indiv;
	
	/** The oon deductible family. */
	public String oon_deductible_family;
	
	/** The coinsurance. */
	public String coinsurance;
	
	/** The dr visit copay. */
	public String dr_visit_copay;
	
	/** The specialist visit copay. */
	public String specialist_visit_copay;
	
	/** The er copay. */
	public String er_copay;
	
	/** The urgent care copay. */
	public String urgent_care_copay;
	
	/** The rx copay. */
	public String rx_copay;
	
	/** The rx mail copay. */
	public String rx_mail_copay;
	
	/** The oop max indiv. */
	public String oop_max_indiv;
	
	/** The oop max family. */
	public String oop_max_family;
	
	/** The oon oop max indiv. */
	public String oon_oop_max_indiv;
	
	/** The oon oop max family. */
	public String oon_oop_max_family;
	
	/** The in patient hospital. */
	public String in_patient_hospital;
	
	/** The outpatient diagnostic lab. */
	public String outpatient_diagnostic_lab;
	
	/** The outpatient surgery. */
	public String outpatient_surgery;
	
	/** The outpatient diagnostic x ray. */
	public String outpatient_diagnostic_x_ray;
	
	/** The outpatient complex imaging. */
	public String outpatient_complex_imaging;
	
	/** The physical occupational therapy. */
	public String physical_occupational_therapy;
	
	/** The plan pdf url. */
	public String plan_pdf_url;
	
	/** The group rating area. */
	public String group_rating_area;
	
	/** The service zones. */
	public String service_zones;
	
	/** The plan name. */
	public String plan_name;
	
	/** The state. */
	public String state;
	
	/** The non tobacco dict. */
	public HashMap<String, Double> non_tobacco_dict;
	
	/** The non tobacco diff dict. */
	public HashMap<String, Double> non_tobacco_diff_dict;
	
	/** The tobacco dict. */
	public HashMap<String, Double> tobacco_dict;
	
	/** The tobacco diff dict. */
	public HashMap<String, Double> tobacco_diff_dict;
	
	/** The non tobacco stats. */
	public PlanStatistics non_tobacco_stats;
	
	/** The tobacco stats. */
	public PlanStatistics tobacco_stats;
	
	/** The errors. */
	public ArrayList<PlanError> errors;
	
	/** The warnings. */
	public ArrayList<PlanWarning> warnings;

	/**
	 * Instantiates a new medical plan.
	 *
	 * @param carrier the carrier
	 * @param carrier_id the carrier id
	 * @param carrier_plan_id the carrier plan id
	 * @param start_date the start date
	 * @param end_date the end date
	 * @param product_name the product name
	 * @param plan_pdf_file_name the plan pdf file name
	 * @param deductible_indiv the deductible indiv
	 * @param deductible_family the deductible family
	 * @param oon_deductible_indiv the oon deductible indiv
	 * @param oon_deductible_family the oon deductible family
	 * @param coinsurance the coinsurance
	 * @param dr_visit_copay the dr visit copay
	 * @param specialist_visit_copay the specialist visit copay
	 * @param er_copay the er copay
	 * @param urgent_care_copay the urgent care copay
	 * @param rx_copay the rx copay
	 * @param rx_mail_copay the rx mail copay
	 * @param oop_max_indiv the oop max indiv
	 * @param oop_max_family the oop max family
	 * @param oon_oop_max_indiv the oon oop max indiv
	 * @param oon_oop_max_family the oon oop max family
	 * @param in_patient_hospital the in patient hospital
	 * @param outpatient_diagnostic_lab the outpatient diagnostic lab
	 * @param outpatient_surgery the outpatient surgery
	 * @param outpatient_diagnostic_x_ray the outpatient diagnostic x ray
	 * @param outpatient_complex_imaging the outpatient complex imaging
	 * @param physical_occupupational_therapy the physical occupupational therapy
	 * @param pdf_url the pdf url
	 * @param state the state
	 * @param group_rating_area the group rating area
	 * @param service_zones the service zones
	 * @param non_tob_dict the non tob dict
	 * @param non_tob_diff_dict the non tob diff dict
	 * @param tob_dict the tob dict
	 * @param tob_diff_dict the tob diff dict
	 */
	public MedicalPlan(Carrier carrier, int carrier_id, String carrier_plan_id, String start_date, String end_date,
			String product_name, String plan_pdf_file_name, String deductible_indiv, String deductible_family,
			String oon_deductible_indiv, String oon_deductible_family, String coinsurance, String dr_visit_copay,
			String specialist_visit_copay, String er_copay, String urgent_care_copay, String rx_copay,
			String rx_mail_copay, String oop_max_indiv, String oop_max_family, String oon_oop_max_indiv,
			String oon_oop_max_family, String in_patient_hospital, String outpatient_diagnostic_lab,
			String outpatient_surgery, String outpatient_diagnostic_x_ray, String outpatient_complex_imaging,
			String physical_occupupational_therapy, String pdf_url, String state, String group_rating_area,
			String service_zones, HashMap<String, Double> non_tob_dict, HashMap<String, Double> non_tob_diff_dict,
			HashMap<String, Double> tob_dict, HashMap<String, Double> tob_diff_dict) {
		this.carrier = carrier;
		this.carrier_id = carrier_id;
		this.carrier_plan_id = carrier_plan_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.product_name = product_name;
		this.plan_pdf_file_name = plan_pdf_file_name;
		this.deductible_indiv = deductible_indiv;
		this.deductible_family = deductible_family;
		this.oon_deductible_indiv = oon_deductible_indiv;
		this.oon_deductible_family = oon_deductible_family;
		this.coinsurance = coinsurance;
		this.dr_visit_copay = dr_visit_copay;
		this.specialist_visit_copay = specialist_visit_copay;
		this.er_copay = er_copay;
		this.urgent_care_copay = urgent_care_copay;
		this.rx_copay = rx_copay;
		this.rx_mail_copay = rx_mail_copay;
		this.oop_max_indiv = oop_max_indiv;
		this.oop_max_family = oop_max_family;
		this.oon_oop_max_indiv = oon_oop_max_indiv;
		this.oon_oop_max_family = oon_oop_max_family;
		this.in_patient_hospital = in_patient_hospital;
		this.outpatient_diagnostic_lab = outpatient_diagnostic_lab;
		this.outpatient_surgery = outpatient_surgery;
		this.outpatient_diagnostic_x_ray = outpatient_diagnostic_x_ray;
		this.outpatient_complex_imaging = outpatient_complex_imaging;
		this.physical_occupational_therapy = physical_occupupational_therapy;
		this.plan_pdf_url = pdf_url;
		this.group_rating_area = group_rating_area;
		this.service_zones = service_zones;
		this.state = state;
		this.non_tobacco_dict = non_tob_dict;
		this.non_tobacco_diff_dict = non_tob_diff_dict;
		this.tobacco_dict = tob_dict;
		this.tobacco_diff_dict = tob_dict;

		/*
		 * Optional: Compute the statistics for this plan. By default these
		 * values will be null, call computeStatistics() to generate these
		 * values after the rate dictionaries have been completely filled.
		 */
		this.non_tobacco_stats = null;
		this.tobacco_stats = null;
		this.errors = new ArrayList<PlanError>();
		this.warnings = new ArrayList<PlanWarning>();
	}

	/**
	 * Instantiates a new medical plan.
	 *
	 * @throws MalformedURLException the malformed URL exception
	 */
	public MedicalPlan() throws MalformedURLException {
		this.carrier_id = 0;
		this.carrier_plan_id = "";
		this.start_date = "";
		this.end_date = "";
		this.product_name = "";
		this.plan_pdf_file_name = "";
		this.deductible_indiv = "";
		this.deductible_family = "";
		this.oon_deductible_indiv = "";
		this.oon_deductible_family = "";
		this.coinsurance = "";
		this.dr_visit_copay = "";
		this.specialist_visit_copay = "";
		this.er_copay = "";
		this.urgent_care_copay = "";
		this.rx_copay = "";
		this.rx_mail_copay = "";
		this.oop_max_indiv = "";
		this.oop_max_family = "";
		this.oon_oop_max_indiv = "";
		this.oon_oop_max_family = "";
		this.in_patient_hospital = "";
		this.outpatient_diagnostic_lab = "";
		this.outpatient_surgery = "";
		this.outpatient_diagnostic_x_ray = "";
		this.outpatient_complex_imaging = "";
		this.physical_occupational_therapy = "";
		this.plan_pdf_url = "";
		this.group_rating_area = "";
		this.service_zones = "";
		this.state = "";
		this.non_tobacco_dict = new HashMap<String, Double>();
		this.non_tobacco_diff_dict = new HashMap<String, Double>();
		this.tobacco_dict = new HashMap<String, Double>();
		this.tobacco_diff_dict = new HashMap<String, Double>();
		this.errors = new ArrayList<PlanError>();
		this.warnings = new ArrayList<PlanWarning>();
	}

	/* (non-Javadoc)
	 * @see plan.Plan#printPlan()
	 */
	/*
	 * Print method used for debugging purposes.
	 */
	public void printPlan() {
		System.out.printf("Carrier id: %s\n", this.carrier_id);
		System.out.printf("Carrier plan id: %s\n", this.carrier_plan_id);
		System.out.printf("Start date: %s\n", this.start_date);
		System.out.printf("End date: %s\n", this.end_date);
		System.out.printf("Product Name: %s\n", this.product_name);
		System.out.printf("Plan pdf filename: %s\n", this.plan_pdf_file_name);
		System.out.printf("Deductible Individual: %s\n", this.deductible_indiv);
		System.out.printf("Deductible Family: %s\n", this.deductible_family);
		System.out.printf("OON Deductible Individual: %s\n", this.oon_deductible_indiv);
		System.out.printf("OON Deductible Family: %s\n", this.oon_deductible_family);
		System.out.printf("Coinsurance: %s\n", this.coinsurance);
		System.out.printf("Dr Visit Copay: %s\n", this.dr_visit_copay);
		System.out.printf("Specialist Visit Copay: %s\n", this.specialist_visit_copay);
		System.out.printf("Er Copay: %s\n", this.er_copay);
		System.out.printf("Urgent Care Copay: %s\n", this.urgent_care_copay);
		System.out.printf("Rx Copay: %s\n", this.rx_copay);
		System.out.printf("Rx Mail Copay: %s\n", this.rx_mail_copay);
		System.out.printf("OOP Max Individual: %s\n", this.oop_max_indiv);
		System.out.printf("OOP Max Family: %s\n", this.oop_max_family);
		System.out.printf("OON OOP Max Individual: %s\n", this.oon_oop_max_indiv);
		System.out.printf("OON OOP Max Family: %s\n", this.oon_oop_max_family);
		System.out.printf("Inpatient Hospital: %s\n", this.in_patient_hospital);
		System.out.printf("Outpatient Diagnostic Lab: %s\n", this.outpatient_diagnostic_lab);
		System.out.printf("Outpatient Surgery: %s\n", this.outpatient_surgery);
		System.out.printf("Outpatient Diagnostic X Ray: %s\n", this.outpatient_diagnostic_x_ray);
		System.out.printf("Outpatient Diagnostic Complex Imaging: %s\n", this.outpatient_complex_imaging);
		System.out.printf("Physical Occupational Therapy: %s\n", this.physical_occupational_therapy);
		System.out.printf("State: %s\n", this.state);
		System.out.printf("Group Rating Area: %s\n", this.group_rating_area);
		System.out.printf("Service Zones: %s\n", this.service_zones);
		System.out.printf("Non-Tobacco Rates\n");
		for (Map.Entry<String, Double> key : this.non_tobacco_dict.entrySet()) {
			System.out.printf("Key: %s  |  Value: %.2f\n", key.getKey(), key.getValue());
		}
		System.out.printf("Non-Tobacco Diff Rates\n");
		for (Map.Entry<String, Double> key : this.non_tobacco_diff_dict.entrySet()) {
			System.out.printf("Key: %s  |  Value: %.2f\n", key.getKey(), key.getValue());
		}
		System.out.printf("Tobacco Rates\n");
		for (Map.Entry<String, Double> key : this.tobacco_dict.entrySet()) {
			System.out.printf("Key: %s  |  Value: %.2f\n", key.getKey(), key.getValue());
		}
		System.out.printf("Tobacco Diff Rates\n");
		for (Map.Entry<String, Double> key : this.tobacco_diff_dict.entrySet()) {
			System.out.printf("Key: %s  |  Value: %.2f\n", key.getKey(), key.getValue());
		}
	}

	/* (non-Javadoc)
	 * @see plan.Plan#format()
	 */
	@Override
	public void format() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see plan.Plan#hasTobaccoRates()
	 */
	public boolean hasTobaccoRates() {
		return !tobacco_dict.isEmpty();
	}

	/* (non-Javadoc)
	 * @see plan.Plan#computeStatistics()
	 */
	@Override
	public void computeStatistics() {
		List<Map.Entry<String, Double>> entryList;
		if (!non_tobacco_dict.isEmpty()) {
			entryList = new ArrayList<>(non_tobacco_dict.entrySet());
			ArrayList<Double> values = new ArrayList<Double>();
			for (Map.Entry<String, Double> entry : entryList) {
				values.add(entry.getValue());
			}

			Double min = Double.MAX_VALUE;
			Double max = Double.MIN_VALUE;
			Double median = 0.0;
			Double mean = 0.0;
			Double stdDev = 0.0;

			for (Double d : values) {
				min = Math.min(d, min);
				max = Math.max(d, max);
				mean += d;
			}

			median = values.get(24);
			mean = mean / values.size();

			for (Double d : values) {
				stdDev += Math.pow(d - mean, 2);
			}
			stdDev = Math.pow(stdDev / (values.size() - 1), .5);

			non_tobacco_stats = new PlanStatistics(min, max, median, mean, stdDev);
		}
		if (!tobacco_diff_dict.isEmpty()) {
			entryList = new ArrayList<>(tobacco_dict.entrySet());
			ArrayList<Double> values = new ArrayList<Double>();
			for (Map.Entry<String, Double> entry : entryList) {
				values.add(entry.getValue());
			}

			Double min = Double.MAX_VALUE;
			Double max = Double.MIN_VALUE;
			Double median = 0.0;
			Double mean = 0.0;
			Double stdDev = 0.0;

			for (Double d : values) {
				min = Math.min(d, min);
				max = Math.max(d, max);
				mean += d;
			}

			median = values.get(24);
			mean = mean / values.size();

			for (Double d : values) {
				stdDev += Math.pow(d - mean, 2);
			}
			stdDev = Math.pow(stdDev / (values.size() - 1), .5);

			tobacco_stats = new PlanStatistics(min, max, median, mean, stdDev);
		}
	}

	/* (non-Javadoc)
	 * @see plan.Plan#hasErrors()
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	/* (non-Javadoc)
	 * @see plan.Plan#getErrors()
	 */
	public ArrayList<PlanError> getErrors() {
		return errors;
	}

	/* (non-Javadoc)
	 * @see plan.Plan#addError(plan.PlanError)
	 */
	@Override
	public void addError(PlanError e) {
		errors.add(e);
	}

	/* (non-Javadoc)
	 * @see plan.Plan#getWarnings()
	 */
	@Override
	public ArrayList<PlanWarning> getWarnings() {
		return warnings;
	}

	/* (non-Javadoc)
	 * @see plan.Plan#hasWarnings()
	 */
	@Override
	public boolean hasWarnings() {
		return !warnings.isEmpty();
	}

	/* (non-Javadoc)
	 * @see plan.Plan#addWarning(plan.PlanWarning)
	 */
	@Override
	public void addWarning(PlanWarning w) {
		warnings.add(w);
	}

	/* (non-Javadoc)
	 * @see plan.Plan#hasPDFUrl()
	 */
	@Override
	public boolean hasPDFUrl() {
		return this.plan_pdf_url != null;
	}

	/* (non-Javadoc)
	 * @see plan.Plan#getProductName()
	 */
	@Override
	public String getProductName() {
		return product_name;
	}
}
