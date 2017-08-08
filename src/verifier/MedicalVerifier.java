package verifier;

import java.io.*;

import java.net.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import parser.*;
import components.*;
import components.Main.*;
import components.Attribute.*;
import plan.*;
import plan.PlanWarning.*;
import plan.PlanError.*;
import names.*;
import names.Product_Name.Metal;

public class MedicalVerifier implements Verifier<MedicalPlan> {

	final XSSFWorkbook workbook;
	Carrier carrier;

	ArrayList<MedicalPlan> plans;
	ArrayList<PlanStatistics> planStats;
	ArrayList<PlanWarning> warnings;
	HashMap<String, Integer> attributeIndexMap;

	String[] ageBands = { "0-18", "19-20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
			"34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51",
			"52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65+" };

	public MedicalVerifier(XSSFWorkbook workbook, Carrier carrier) {
		super();
		this.workbook = workbook;
		this.carrier = carrier;
		plans = new ArrayList<MedicalPlan>();
		warnings = new ArrayList<PlanWarning>();
	}

	@Override
	public void verifyMonotonicity() {
		for (MedicalPlan plan : plans) {
			System.out.println(plan.product_name);
			for (Map.Entry<String, Double> rateEntry : plan.non_tobacco_diff_dict.entrySet()) {
				if (rateEntry.getValue() < 0) {
					System.out.println(rateEntry.getKey());
					System.out.println(rateEntry.getValue());

					String fullKey = Attribute.getFullAge(rateEntry.getKey()).toUpperCase();
					AttributeType att = Attribute.AttributeType.valueOf(fullKey);
					PlanError newError = new PlanError(PlanError.Error.MONOTONOCITY, att,
							PlanError.RateType.NON_TOBACCO, rateEntry.getValue().toString(), "");
					String prevBand;
					if (rateEntry.getKey().equals("19-20")) {
						prevBand = "0-18";
					} else if (rateEntry.getKey().equals("65+")) {
						prevBand = "64";
					} else {
						prevBand = String.valueOf(Integer.parseInt(rateEntry.getKey()) - 1);
					}
					newError.setAgeBand(prevBand + " and " + rateEntry.getKey());
					plan.addError(newError);
				}
			}
			if (plan.hasTobaccoRates()) {
				for (Map.Entry<String, Double> rateEntry : plan.tobacco_diff_dict.entrySet()) {
					if (rateEntry.getValue() < 0) {
						String fullKey = Attribute.getFullAge(rateEntry.getKey()).toUpperCase();
						AttributeType att = Attribute.AttributeType.valueOf(fullKey);
						PlanError newError = new PlanError(PlanError.Error.MONOTONOCITY, att,
								PlanError.RateType.TOBACCO, rateEntry.getValue().toString(), "");
						String prevBand;
						if (rateEntry.getKey().equals("0-18")) {
							prevBand = "0-18";
						} else if (rateEntry.getKey().equals("65+")) {
							prevBand = "64";
						} else {
							prevBand = String.valueOf(Integer.parseInt(rateEntry.getKey()) - 1);
						}
						newError.setAgeBand(prevBand + " and " + rateEntry.getKey());
						plan.addError(newError);
					}
				}
			}
		}
		return;
	}

	/*
	 * The program then verifies that tobacco rates > non-tobacco rates, family
	 * rates = 2*individual rates, and oon rates > normal rates
	 */
	@Override
	public void verifyIncrements() {
		for (MedicalPlan plan : plans) {
			if (!plan.deductible_indiv.isEmpty()) {
				/*
				 * getValue() method retrieves a value from the attribute data
				 * returns -1.0 if format is not recognized, -2 if the value if
				 * n/a
				 */
				Double ded_indiv = Formatter.getValue(plan.deductible_indiv);
				Double ded_family = Formatter.getValue(plan.deductible_family);
				Double oon_ded_indiv = Formatter.getValue(plan.oon_deductible_indiv);
				Double oon_ded_family = Formatter.getValue(plan.oon_deductible_family);
				Double oop_max_indiv = Formatter.getValue(plan.oop_max_indiv);
				Double oop_max_family = Formatter.getValue(plan.oop_max_family);
				Double oon_oop_max_indiv = Formatter.getValue(plan.oon_oop_max_indiv);
				Double oon_oop_max_family = Formatter.getValue(plan.oon_oop_max_family);

				// Check if the formats of the above attributes are recognized
				if (ded_indiv == -1.0) {
					PlanWarning warning = new PlanWarning(PlanWarning.Warning.UNRECOGNIZED_FORMAT,
							Attribute.AttributeType.DEDUCTIBLE_INDIV, null, plan.deductible_indiv, "");
					plan.addWarning(warning);
				}

				if (ded_family == -1.0) {
					PlanWarning warning = new PlanWarning(PlanWarning.Warning.UNRECOGNIZED_FORMAT,
							Attribute.AttributeType.DEDUCTIBLE_FAMILY, null, plan.deductible_family, "");
					plan.addWarning(warning);
				}

				if (oon_ded_indiv == -1.0) {
					PlanWarning warning = new PlanWarning(PlanWarning.Warning.UNRECOGNIZED_FORMAT,
							Attribute.AttributeType.OON_DEDUCTIBLE_INDIVIDUAL, null, plan.oon_deductible_indiv, "");
					plan.addWarning(warning);
				}

				if (oon_ded_family == -1.0) {
					PlanWarning warning = new PlanWarning(PlanWarning.Warning.UNRECOGNIZED_FORMAT,
							Attribute.AttributeType.OON_DEDUCTIBLE_FAMILY, null, plan.oon_deductible_family, "");
					plan.addWarning(warning);
				}

				if (oop_max_indiv == -1.0) {
					PlanWarning warning = new PlanWarning(PlanWarning.Warning.UNRECOGNIZED_FORMAT,
							Attribute.AttributeType.OOP_MAX_INDIV, null, plan.oop_max_indiv, "");
					plan.addWarning(warning);
				}

				if (oop_max_family == -1.0) {
					PlanWarning warning = new PlanWarning(PlanWarning.Warning.UNRECOGNIZED_FORMAT,
							Attribute.AttributeType.OOP_MAX_FAMILY, null, plan.oop_max_family, "");
					plan.addWarning(warning);
				}

				if (oon_oop_max_indiv == -1.0) {
					PlanWarning warning = new PlanWarning(PlanWarning.Warning.UNRECOGNIZED_FORMAT,
							Attribute.AttributeType.OON_OOP_MAX_INDIVIDUAL, null, plan.oon_oop_max_indiv, "");
					plan.addWarning(warning);
				}

				if (oon_oop_max_family == -1.0) {
					PlanWarning warning = new PlanWarning(PlanWarning.Warning.UNRECOGNIZED_FORMAT,
							Attribute.AttributeType.OON_OOP_MAX_FAMILY, null, plan.oon_oop_max_family, "");
					plan.addWarning(warning);
				}

				// Check if all family rates are twice the individual rates
				if (ded_indiv >= 0 & ded_family >= 0 & ded_family < ded_indiv) {
					PlanError newError = new PlanError(PlanError.Error.INDIV_FAMILY_INCREMENT,
							Attribute.AttributeType.DEDUCTIBLE_INDIV, null, plan.deductible_indiv, "");
					newError.addSecondAttribute(Attribute.AttributeType.DEDUCTIBLE_FAMILY);
					newError.addSecondIncorrectVal(plan.deductible_family);
					plan.addError(newError);
				}

				if (oon_ded_indiv >= 0 & oon_ded_family >= 0 & oon_ded_family < oon_ded_indiv) {
					PlanError newError = new PlanError(PlanError.Error.INDIV_FAMILY_INCREMENT,
							Attribute.AttributeType.OON_DEDUCTIBLE_INDIVIDUAL, null, plan.oon_deductible_indiv, "");
					newError.addSecondAttribute(Attribute.AttributeType.OON_DEDUCTIBLE_FAMILY);
					newError.addSecondIncorrectVal(plan.oon_deductible_family);
					plan.addError(newError);
				}

				if (oop_max_indiv >= 0 & oop_max_family >= 0 & oop_max_family < oop_max_indiv) {
					PlanError newError = new PlanError(PlanError.Error.INDIV_FAMILY_INCREMENT,
							Attribute.AttributeType.OOP_MAX_INDIV, null, plan.oop_max_indiv, "");
					newError.addSecondAttribute(Attribute.AttributeType.OOP_MAX_FAMILY);
					newError.addSecondIncorrectVal(plan.oop_max_family);
					plan.addError(newError);
				}

				if (oon_oop_max_indiv >= 0 & oon_oop_max_family >= 0 & oon_oop_max_family < oon_oop_max_indiv) {
					PlanError newError = new PlanError(PlanError.Error.INDIV_FAMILY_INCREMENT,
							Attribute.AttributeType.OON_OOP_MAX_INDIVIDUAL, null, plan.oon_oop_max_indiv, "");
					newError.addSecondAttribute(Attribute.AttributeType.OON_OOP_MAX_FAMILY);
					newError.addSecondIncorrectVal(plan.oon_oop_max_family);
					plan.addError(newError);
				}

				// Check if all out-of-network rates are greater than in-network
				// rates
				if (ded_indiv >= 0 & oon_ded_indiv >= 0 & oon_ded_indiv < ded_indiv) {
					PlanError newError = new PlanError(PlanError.Error.OON_INCREMENT,
							Attribute.AttributeType.DEDUCTIBLE_INDIV, null, plan.deductible_indiv, "");
					newError.addSecondAttribute(Attribute.AttributeType.OON_DEDUCTIBLE_INDIVIDUAL);
					newError.addSecondIncorrectVal(plan.oon_deductible_indiv);
					plan.addError(newError);
				}

				if (ded_family >= 0 & oon_ded_family >= 0 & oon_ded_family < ded_family) {
					PlanError newError = new PlanError(PlanError.Error.OON_INCREMENT,
							Attribute.AttributeType.DEDUCTIBLE_FAMILY, null, plan.deductible_family, "");
					newError.addSecondAttribute(Attribute.AttributeType.OON_DEDUCTIBLE_FAMILY);
					newError.addSecondIncorrectVal(plan.oon_deductible_family);
					plan.addError(newError);
				}

				if (oon_oop_max_indiv >= 0 & oop_max_indiv >= 0 & oon_oop_max_indiv < oop_max_indiv) {
					PlanError newError = new PlanError(PlanError.Error.OON_INCREMENT,
							Attribute.AttributeType.OOP_MAX_INDIV, null, plan.oop_max_indiv, "");
					newError.addSecondAttribute(Attribute.AttributeType.OON_OOP_MAX_INDIVIDUAL);
					newError.addSecondIncorrectVal(plan.oon_oop_max_indiv);
					plan.addError(newError);
				}

				if (oon_oop_max_family >= 0 & oop_max_family >= 0 & oon_oop_max_family < oop_max_family) {
					PlanError newError = new PlanError(PlanError.Error.OON_INCREMENT,
							Attribute.AttributeType.OOP_MAX_FAMILY, null, plan.oop_max_family, "");
					newError.addSecondAttribute(Attribute.AttributeType.OON_OOP_MAX_FAMILY);
					newError.addSecondIncorrectVal(plan.oon_oop_max_family);
					plan.addError(newError);
				}
			}
			/*
			 * If plan has tobacco rates, iterate through both rate dictionaries
			 * to ensure tobacco rates > non-tobacco rates
			 */

			if (plan.hasTobaccoRates()) {
				for (Map.Entry<String, Double> rateEntry : plan.tobacco_diff_dict.entrySet()) {
					if (plan.non_tobacco_dict.get(rateEntry.getKey()) > rateEntry.getValue()) {
						String fullKey = Attribute.getFullAge(rateEntry.getKey()).toUpperCase();
						AttributeType att = Attribute.AttributeType.valueOf(fullKey);

						PlanError newError = new PlanError(PlanError.Error.TOBACCO_INCREMENT, att, RateType.BOTH,
								plan.non_tobacco_dict.get(rateEntry.getKey()).toString(), "");
						newError.addSecondIncorrectVal(rateEntry.getValue().toString());
						plan.addError(newError);
					}
				}
			}
		}

	}

	@Override
	public void verifyCV() {
		for (int i = 1; i < plans.size(); i++) {
			Double firstVal = plans.get(i - 1).non_tobacco_stats.getCV();
			Double secondVal = plans.get(i).non_tobacco_stats.getCV();
			if (Double.compare(firstVal, secondVal) != 0) {

				PlanError newError = new PlanError(PlanError.Error.CV, Attribute.AttributeType.NONE,
						PlanError.RateType.NON_TOBACCO, secondVal.toString(), firstVal.toString());
				plans.get(i).addError(newError);
				i++;
			}

			if (plans.get(i).hasTobaccoRates()) {
				Double firstTobVal = plans.get(i - 1).tobacco_stats.getCV();
				Double secondTobVal = plans.get(i).tobacco_stats.getCV();
				if (Double.compare(firstTobVal, secondTobVal) != 0) {
					PlanError newError = new PlanError(PlanError.Error.CV, Attribute.AttributeType.NONE,
							PlanError.RateType.TOBACCO, secondTobVal.toString(), firstTobVal.toString());
					plans.get(i).addError(newError);
					i++;
				}
			}

		}
		return;
	}

	@Override
	public void verifyPDFMapping(MedicalPlan plan) throws IOException {
		PlanError new_error;
		URL plan_url = null;
		if (plan.plan_pdf_url == null) {
			return;
		}
		File file = new File("tempFile");
		try {
			plan_url = new URL(plan.plan_pdf_url);
		} catch (MalformedURLException e) {
			PlanWarning new_warning = new PlanWarning(Warning.INVALID_PDF_LINK, AttributeType.PLAN_PDF_FILE_URL,
					null, plan.plan_pdf_url, "");
			plan.addWarning(new_warning);
		}
		System.out.println(plan_url.toString());
		while (true) {
			try {
				FileUtils.copyURLToFile(plan_url, file, 1000000, 1000000);
				break;
			} catch (IOException e) {
				System.out.println(e.getMessage());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		PDFManager pdfManager = new PDFManager(file);
		String text = pdfManager.ToText();
		String[] tokens = text.split("[\\s\\r\\n]+");

		Parser parser = new Parser(tokens, carrier);
		Product_Name p_name = new Product_Name(plan.product_name);

		if (p_name.metal == Metal.None) {
			PlanWarning new_warning = new PlanWarning(PlanWarning.Warning.METAL_NOT_FOUND,
					Attribute.AttributeType.PRODUCT_NAME, null, p_name.original_name, "");
			plan.addWarning(new_warning);
		}

		if (p_name.plan == Product_Name.Plan.None) {
			PlanWarning new_warning = new PlanWarning(PlanWarning.Warning.PLAN_TYPE_NOT_FOUND,
					Attribute.AttributeType.PRODUCT_NAME, null, p_name.original_name, "");
			plan.addWarning(new_warning);
		}

		if (!parser.findProductName(p_name.original_name)) {
			if (!parser.findMetal(p_name.metal) & p_name.metal != Metal.None) {
				new_error = new PlanError(PlanError.Error.PDF_METAL_MISMATCH, Attribute.AttributeType.PRODUCT_NAME,
						null, p_name.metal.toString(), "");
				plan.addError(new_error);
			}
			if (!parser.findPlan(p_name.plan) & p_name.plan != Product_Name.Plan.None) {
				new_error = new PlanError(PlanError.Error.PDF_PLAN_MISMATCH, Attribute.AttributeType.PRODUCT_NAME, null,
						p_name.plan.toString(), "");
				plan.addError(new_error);
			}
		}
		return;
	}

	public ArrayList<MedicalPlan> getPlans() {
		return plans;
	}

	public void generatePlans() throws MalformedURLException {
		Sheet sheet = workbook.getSheetAt(0);

		Cell cell;
		Double firstVal;
		Double secondVal;

		int numRows = getNumRows(sheet);
		int row_index = 1;
		int col_index;
		int tob_col_index;

		Row r = sheet.getRow(0);
		computeAttributeIndexMap(r);

		while (row_index < numRows - 1) {

			ArrayList<PlanWarning> planWarnings = new ArrayList<PlanWarning>();
			col_index = 0;
			// System.out.println(row_index);

			int carrier_id = 0;
			String carrier_plan_id = "";
			String start_date = "";
			String end_date = "";
			String product_name = "";
			String plan_pdf_file_name = "";
			String deductible_indiv = "";
			String deductible_family = "";
			String oon_deductible_indiv = "";
			String oon_deductible_family = "";
			String coinsurance = "";
			String dr_visit_copay = "";
			String specialist_visit_copay = "";
			String er_copay = "";
			String urgent_care_copay = "";
			String rx_copay = "";
			String rx_mail_copay = "";
			String oop_max_indiv = "";
			String oop_max_family = "";
			String oon_oop_max_indiv = "";
			String oon_oop_max_family = "";
			String in_patient_hospital = "";
			String outpatient_diagnostic_lab = "";
			String outpatient_surgery = "";
			String outpatient_diagnostic_x_ray = "";
			String outpatient_complex_imaging = "";
			String physical_occupational_therapy = "";
			String plan_pdf_file_url = "";
			String state = "";
			String group_rating_area = "";
			String service_zones = "";
			Boolean hasTobaccoRates = false;
			HashMap<String, Double> non_tobacco_dict = new HashMap<String, Double>();
			HashMap<String, Double> non_tobacco_diff_dict = new HashMap<String, Double>();
			HashMap<String, Double> tobacco_dict = new HashMap<String, Double>();
			HashMap<String, Double> tobacco_diff_dict = new HashMap<String, Double>();

			r = sheet.getRow(row_index++);

			if (attributeIndexMap.containsKey("carrier")) {
				cell = r.getCell(attributeIndexMap.get("carrier"));
				String temp = getCellValue(cell);
				try {
					carrier = Carrier.valueOf(temp);
				} catch (IllegalArgumentException e) {
					if (Main.carrierAbbrevMap.containsKey(temp)) {
						carrier = Main.carrierAbbrevMap.get(temp);
					} else {
						carrier = Main.Carrier.NONE;
					}
				}
			}

			if (attributeIndexMap.containsKey("carrier_id")) {
				cell = r.getCell(attributeIndexMap.get("carrier_id"));
				carrier_id = Double.valueOf(getCellValue(cell)).intValue();
			}

			if (attributeIndexMap.containsKey("carrier_plan_id")) {
				cell = r.getCell(attributeIndexMap.get("carrier_plan_id"));
				carrier_plan_id = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("start_date")) {
				cell = r.getCell(attributeIndexMap.get("start_date"));
				start_date = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("end_date")) {
				cell = r.getCell(attributeIndexMap.get("end_date"));
				end_date = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("product_name")) {
				cell = r.getCell(attributeIndexMap.get("product_name"));
				product_name = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("plan_pdf_file_name")) {
				cell = r.getCell(attributeIndexMap.get("plan_pdf_file_name"));
				plan_pdf_file_name = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("deductible_indiv")) {
				cell = r.getCell(attributeIndexMap.get("deductible_indiv"));
				deductible_indiv = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("deductible_family")) {
				cell = r.getCell(attributeIndexMap.get("deductible_family"));
				deductible_family = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("oon_deductible_individual")) {
				cell = r.getCell(attributeIndexMap.get("oon_deductible_individual"));
				oon_deductible_indiv = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("oon_deductible_family")) {
				cell = r.getCell(attributeIndexMap.get("oon_deductible_family"));
				oon_deductible_family = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("coinsurance")) {
				cell = r.getCell(attributeIndexMap.get("coinsurance"));
				coinsurance = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("dr_visit_copay")) {
				cell = r.getCell(attributeIndexMap.get("dr_visit_copay"));
				dr_visit_copay = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("specialist_visits_copay")) {
				cell = r.getCell(attributeIndexMap.get("specialist_visits_copay"));
				specialist_visit_copay = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("er_copay")) {
				cell = r.getCell(attributeIndexMap.get("er_copay"));
				er_copay = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("urgent_care_copay")) {
				cell = r.getCell(attributeIndexMap.get("urgent_care_copay"));
				urgent_care_copay = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("rx_copay")) {
				cell = r.getCell(attributeIndexMap.get("rx_copay"));
				rx_copay = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("rx_mail_copay")) {
				cell = r.getCell(attributeIndexMap.get("rx_mail_copay"));
				rx_mail_copay = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("oop_max_indiv")) {
				cell = r.getCell(attributeIndexMap.get("oop_max_indiv"));
				oop_max_indiv = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("oop_max_family")) {
				cell = r.getCell(attributeIndexMap.get("oop_max_family"));
				oop_max_family = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("oon_oop_max_individual")) {
				cell = r.getCell(attributeIndexMap.get("oon_oop_max_individual"));
				oon_oop_max_indiv = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("oon_oop_max_family")) {
				cell = r.getCell(attributeIndexMap.get("oon_oop_max_family"));
				oon_oop_max_family = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("in_patient_hospital")) {
				cell = r.getCell(attributeIndexMap.get("in_patient_hospital"));
				in_patient_hospital = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("outpatient_diagnostic_lab")) {
				cell = r.getCell(attributeIndexMap.get("outpatient_diagnostic_lab"));
				outpatient_diagnostic_lab = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("outpatient_surgery")) {
				cell = r.getCell(attributeIndexMap.get("outpatient_surgery"));
				outpatient_surgery = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("outpatient_diagnostic_x_ray")) {
				cell = r.getCell(attributeIndexMap.get("outpatient_diagnostic_x_ray"));
				outpatient_diagnostic_x_ray = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("outpatient_complex_imaging")) {
				cell = r.getCell(attributeIndexMap.get("outpatient_complex_imaging"));
				outpatient_complex_imaging = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("physical_occupational_therapy")) {
				cell = r.getCell(attributeIndexMap.get("physical_occupational_therapy"));
				physical_occupational_therapy = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("state")) {
				cell = r.getCell(attributeIndexMap.get("state"));
				state = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("group_rating_areas")) {
				cell = r.getCell(attributeIndexMap.get("group_rating_areas"));
				group_rating_area = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("service_zones")) {
				cell = r.getCell(attributeIndexMap.get("service_zones"));
				service_zones = getCellValue(cell);
			}

			if (attributeIndexMap.containsKey("plan_pdf_file_url")) {
				cell = r.getCell(attributeIndexMap.get("plan_pdf_file_url"));
				plan_pdf_file_url = getCellValue(cell);
			}
			col_index = attributeIndexMap.get("0-18");

			cell = r.getCell(col_index++);
			firstVal = cell.getNumericCellValue();
			non_tobacco_dict.put(ageBands[0], firstVal);

			int base_index = col_index - 1;
			int temp_index = col_index + 46;

			while (col_index < temp_index) {
				cell = r.getCell(col_index - 1);
				firstVal = cell.getNumericCellValue();
				cell = r.getCell(col_index);
				secondVal = cell.getNumericCellValue();

				non_tobacco_dict.put(ageBands[col_index - base_index], secondVal);
				non_tobacco_diff_dict.put(ageBands[col_index - base_index], secondVal - firstVal);

				col_index++;
			}

			if (attributeIndexMap.get("t-0-18") != null) {
				col_index = attributeIndexMap.get("t-0-18");
				cell = r.getCell(col_index);
				if (cell != null) {
					hasTobaccoRates = true;
				}
			}
			if (hasTobaccoRates) {
				col_index = attributeIndexMap.get("t-0-18");
				cell = r.getCell(col_index);
				firstVal = cell.getNumericCellValue();
				tobacco_dict.put(ageBands[0], firstVal);
				base_index = col_index - 1;
				temp_index = col_index + 46;

				while (col_index < temp_index) {
					cell = r.getCell(col_index - 1);
					firstVal = cell.getNumericCellValue();
					cell = r.getCell(col_index);
					secondVal = cell.getNumericCellValue();

					tobacco_dict.put(ageBands[col_index - base_index], secondVal);
					tobacco_diff_dict.put(ageBands[col_index - base_index], secondVal - firstVal);

					col_index++;
				}
			}

			System.out.println(carrier.toString());

			MedicalPlan new_plan = new MedicalPlan(carrier, carrier_id, carrier_plan_id, start_date, end_date,
					product_name, plan_pdf_file_name, deductible_indiv, deductible_family, oon_deductible_indiv,
					oon_deductible_family, coinsurance, dr_visit_copay, specialist_visit_copay, er_copay,
					urgent_care_copay, rx_copay, rx_mail_copay, oop_max_indiv, oop_max_family, oon_oop_max_indiv,
					oon_oop_max_family, in_patient_hospital, outpatient_diagnostic_lab, outpatient_surgery,
					outpatient_diagnostic_x_ray, outpatient_complex_imaging, physical_occupational_therapy,
					plan_pdf_file_url, state, group_rating_area, service_zones, non_tobacco_dict, non_tobacco_diff_dict,
					tobacco_dict, tobacco_diff_dict);

			for (PlanWarning w : planWarnings) {
				new_plan.addWarning(w);
			}

			new_plan.computeStatistics();
			plans.add(new_plan);
		}
	}

	public void computeAttributeIndexMap(Row r) {
		HashMap<String, Integer> indexMap = new HashMap<String, Integer>();
		int index = 0;
		Cell cell = r.getCell(index);
		String header = getCellValue(cell);
		while (!header.equals("zero_eighteen")) {
			indexMap.put(header, index++);
			cell = r.getCell(index);
			header = getCellValue(cell);
		}
		indexMap.put(Attribute.ageReverseBandMap.get(header), index);
		index += 47;
		int temp_index = index + 5;
		while (index < temp_index) {
			cell = r.getCell(index++);
			header = getCellValue(cell);
			if (header.equals("zero_eighteen")) {
				indexMap.put("t-" + Attribute.ageReverseBandMap.get(header), index);
				break;
			}
		}
		attributeIndexMap = indexMap;
	}

	public HashMap<String, Integer> getAttributeIndexMap() {
		return attributeIndexMap;
	}

	public void verifyIndexMap(HashMap<String, Integer> attributeIndexMap) {
		PlanWarning new_warning;
		if (!attributeIndexMap.containsKey("carrier_id")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.CARRIER_ID, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("carrier_plan_id")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.CARRIER_PLAN_ID, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("start_date")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.START_DATE, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("end_date")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.END_DATE, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("product_name")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.PRODUCT_NAME, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("plan_pdf_file_name")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.PLAN_PDF_FILE_NAME, null, null,
					null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("deductible_indiv")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.DEDUCTIBLE_INDIV, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("deductible_family")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.DEDUCTIBLE_FAMILY, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("oon_deductible_individual")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.OON_DEDUCTIBLE_INDIVIDUAL, null,
					null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("oon_deductible_family")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.OON_DEDUCTIBLE_FAMILY, null, null,
					null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("coinsurance")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.COINSURANCE, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("dr_visit_copay")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.DR_VISIT_COPAY, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("specialist_visits_copay")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.SPECIALIST_VISITS_COPAY, null, null,
					null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("er_copay")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.ER_COPAY, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("urgent_care_copay")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.URGENT_CARE_COPAY, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("rx_copay")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.RX_COPAY, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("rx_mail_copay")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.RX_MAIL_COPAY, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("oop_max_indiv")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.OOP_MAX_INDIV, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("oop_max_family")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.OOP_MAX_FAMILY, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("oon_oop_max_individual")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.OON_OOP_MAX_INDIVIDUAL, null, null,
					null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("oon_oop_max_family")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.OON_OOP_MAX_FAMILY, null, null,
					null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("in_patient_hospital")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.IN_PATIENT_HOSPITAL, null, null,
					null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("outpatient_diagnostic_lab")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.OUTPATIENT_DIAGNOSTIC_LAB, null,
					null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("outpatient_surgery")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.OUTPATIENT_SURGERY, null, null,
					null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("outpatient_diagnostic_x_ray")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.OUTPATIENT_DIAGNOSTIC_X_RAY, null,
					null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("outpatient_complex_imaging")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.OUTPATIENT_COMPLEX_IMAGING, null,
					null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("physical_occupational_therapy")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.PHYSICAL_OCCUPATIONAL_THERAPY, null,
					null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("state")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.STATE, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("group_rating_areas")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.GROUP_RATING_AREAS, null, null,
					null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("service_zones")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.SERVICE_ZONES, null, null, null);
			warnings.add(new_warning);
		}

		if (!attributeIndexMap.containsKey("plan_pdf_file_url")) {
			new_warning = new PlanWarning(Warning.MISSING_ATTRIBUTE, AttributeType.PLAN_PDF_FILE_URL, null, null, null);
			warnings.add(new_warning);
		}
	}

	public ArrayList<PlanWarning> getWarnings() {
		return warnings;
	}

	public Parser getParser(Carrier c, String[] tokens) {
		switch (c) {
		case NEPA:
			return new PA_NEPA_Parser(tokens, carrier);
		case Aetna:
			break;
		case AmeriHealth:
			break;
		case Anthem:
			break;
		case CBC:
			break;
		case CPA:
			break;
		case Cigna:
			break;
		case Delta:
			break;
		case Geisinger:
			break;
		case Horizon:
			break;
		case IBC:
			break;
		case NONE:
			break;
		case Oxford:
			break;
		case UHC:
			break;
		case UPMC:
			break;
		case WPA:
			break;
		default:
			break;
		}
		return null;
	}

}
