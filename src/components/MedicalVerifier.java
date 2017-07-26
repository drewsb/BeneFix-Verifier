package components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import components.Attribute.AttributeType;

public class MedicalVerifier implements Verifier<MedicalPlan> {

	final XSSFWorkbook workbook;

	ArrayList<MedicalPlan> plans;
	ArrayList<PlanStatistics> planStats;
	Boolean hasTobaccoRates;

	String[] ageBands = { "0-18", "19-20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
			"34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51",
			"52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65+" };

	public MedicalVerifier(XSSFWorkbook workbook) {
		super();
		this.workbook = workbook;
		plans = new ArrayList<MedicalPlan>();
		hasTobaccoRates = false;
	}

	@Override
	public void verifyMonotonicity() {
		for (MedicalPlan plan : plans) {
			System.out.println(plan.product_name);
			for (Map.Entry<String, Double> rateEntry : plan.non_tobacco_diff_dict.entrySet()) {
				if (rateEntry.getValue() < 0) {
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
			if (hasTobaccoRates) {
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

	@Override
	public void verifyCV() {
		for (int i = 1; i < plans.size(); i++) {
			Double firstVal = plans.get(i - 1).non_tobacco_stats.getCV();
			Double secondVal = plans.get(i).non_tobacco_stats.getCV();
			if (Double.compare(firstVal, secondVal) != 0) {
				PlanError newError = new PlanError(PlanError.Error.CV, Attribute.AttributeType.NONE,
						PlanError.RateType.NON_TOBACCO, firstVal.toString(), secondVal.toString());
				plans.get(i).addError(newError);
			}

			if (hasTobaccoRates) {
				Double firstTobVal = plans.get(i - 1).tobacco_stats.getCV();
				Double secondTobVal = plans.get(i).tobacco_stats.getCV();
				if (Double.compare(firstVal, secondVal) != 0) {
					PlanError newError = new PlanError(PlanError.Error.CV, Attribute.AttributeType.NONE,
							PlanError.RateType.TOBACCO, firstTobVal.toString(), secondTobVal.toString());
					plans.get(i).addError(newError);
				}
			}

		}
		return;
	}

	@Override
	public void verifyPDFMapping() {
		return;
	}

	public ArrayList<MedicalPlan> getPlans() {
		return plans;
	}

	public void generatePlans() {
		Sheet sheet = workbook.getSheetAt(0);

		Cell cell;
		Double firstVal;
		Double secondVal;

		int numRows = getNumRows(sheet);
		int row_index = 1;
		int col_index;

		while (row_index < numRows - 1) {
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
			String state = "";
			String group_rating_area = "";
			String service_zones = "";
			HashMap<String, Double> non_tobacco_dict = new HashMap<String, Double>();
			HashMap<String, Double> non_tobacco_diff_dict = new HashMap<String, Double>();
			HashMap<String, Double> tobacco_dict = new HashMap<String, Double>();
			HashMap<String, Double> tobacco_diff_dict = new HashMap<String, Double>();

			Row r = sheet.getRow(row_index++);
			cell = r.getCell(col_index++);
			carrier_id = Double.valueOf(getCellValue(cell)).intValue();
			cell = r.getCell(col_index++);
			carrier_plan_id = getCellValue(cell);
			cell = r.getCell(col_index++);
			start_date = getCellValue(cell);
			cell = r.getCell(col_index++);
			end_date = getCellValue(cell);
			cell = r.getCell(col_index++);
			product_name = getCellValue(cell);
			cell = r.getCell(col_index++);
			plan_pdf_file_name = getCellValue(cell);
			cell = r.getCell(col_index++);
			deductible_indiv = getCellValue(cell);
			cell = r.getCell(col_index++);
			deductible_family = getCellValue(cell);
			cell = r.getCell(col_index++);
			oon_deductible_indiv = getCellValue(cell);
			cell = r.getCell(col_index++);
			oon_deductible_family = getCellValue(cell);
			cell = r.getCell(col_index++);
			coinsurance = getCellValue(cell);
			cell = r.getCell(col_index++);
			dr_visit_copay = getCellValue(cell);
			cell = r.getCell(col_index++);
			specialist_visit_copay = getCellValue(cell);
			cell = r.getCell(col_index++);
			er_copay = getCellValue(cell);
			cell = r.getCell(col_index++);
			urgent_care_copay = getCellValue(cell);
			cell = r.getCell(col_index++);
			rx_copay = getCellValue(cell);
			cell = r.getCell(col_index++);
			rx_mail_copay = getCellValue(cell);
			cell = r.getCell(col_index++);
			oop_max_indiv = getCellValue(cell);
			cell = r.getCell(col_index++);
			oop_max_family = getCellValue(cell);
			cell = r.getCell(col_index++);
			oon_oop_max_indiv = getCellValue(cell);
			cell = r.getCell(col_index++);
			oon_oop_max_family = getCellValue(cell);
			cell = r.getCell(col_index++);
			in_patient_hospital = getCellValue(cell);
			cell = r.getCell(col_index++);
			outpatient_diagnostic_lab = getCellValue(cell);
			cell = r.getCell(col_index++);
			outpatient_surgery = getCellValue(cell);
			cell = r.getCell(col_index++);
			outpatient_diagnostic_x_ray = getCellValue(cell);
			cell = r.getCell(col_index++);
			outpatient_complex_imaging = getCellValue(cell);
			cell = r.getCell(col_index++);
			physical_occupational_therapy = getCellValue(cell);
			cell = r.getCell(col_index++);
			state = getCellValue(cell);
			cell = r.getCell(col_index++);
			group_rating_area = getCellValue(cell);
			cell = r.getCell(col_index++);
			service_zones = getCellValue(cell);
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

			// if (getCellValue(r.getCell(col_index)) != null) {
			// temp_index = col_index + 45;
			// while (col_index < temp_index) {
			// System.out.println(col_index);
			// cell = r.getCell(col_index);
			// firstVal = cell.getNumericCellValue();
			// cell = r.getCell(col_index);
			// secondVal = cell.getNumericCellValue();
			//
			// tobacco_dict.put(ageBands[col_index - base_index], secondVal);
			// tobacco_diff_dict.put(ageBands[col_index - base_index], secondVal
			// - firstVal);
			// col_index++;
			// }
			// hasTobaccoRates = true;
			// }

			MedicalPlan new_plan = new MedicalPlan(carrier_id, carrier_plan_id, start_date, end_date, product_name,
					plan_pdf_file_name, deductible_indiv, deductible_family, oon_deductible_indiv,
					oon_deductible_family, coinsurance, dr_visit_copay, specialist_visit_copay, er_copay,
					urgent_care_copay, rx_copay, rx_mail_copay, oop_max_indiv, oop_max_family, oon_oop_max_indiv,
					oon_oop_max_family, in_patient_hospital, outpatient_diagnostic_lab, outpatient_surgery,
					outpatient_diagnostic_x_ray, outpatient_complex_imaging, physical_occupational_therapy, state,
					group_rating_area, service_zones, non_tobacco_dict, non_tobacco_diff_dict, tobacco_dict,
					tobacco_diff_dict);

			new_plan.computeStatistics();
			// new_plan.printPlan();
			plans.add(new_plan);
		}
	}

}
