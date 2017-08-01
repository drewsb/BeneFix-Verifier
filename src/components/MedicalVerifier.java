package components;

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

import components.Attribute.AttributeType;

public class MedicalVerifier implements Verifier<MedicalPlan> {

	final XSSFWorkbook workbook;

	ArrayList<MedicalPlan> plans;
	ArrayList<PlanStatistics> planStats;

	String[] ageBands = { "0-18", "19-20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
			"34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51",
			"52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65+" };

	public MedicalVerifier(XSSFWorkbook workbook) {
		super();
		this.workbook = workbook;
		plans = new ArrayList<MedicalPlan>();
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

			if (plans.get(i).hasTobaccoRates()) {
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
	public void verifyPDFMapping() throws IOException {
		for (MedicalPlan plan : plans) {
			File file = new File("tempFile");
			URL plan_url = plan.plan_pdf_url;
			System.out.println(plan_url.toString());
			FileUtils.copyURLToFile(plan_url, file, 1000000, 1000000);
			PDFManager pdfManager = new PDFManager(file);
			String text = pdfManager.ToText();
			String[] tokens = text.split("[\\s\\r\\n]+");
			for(String s: tokens){
				System.out.println(s);
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
		HashMap<String, Integer> attributeIndexMap = getAttributeIndexMap(r);

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
			URL plan_pdf_file_url = null;
			String state = "";
			String group_rating_area = "";
			String service_zones = "";
			Boolean hasTobaccoRates = false;
			HashMap<String, Double> non_tobacco_dict = new HashMap<String, Double>();
			HashMap<String, Double> non_tobacco_diff_dict = new HashMap<String, Double>();
			HashMap<String, Double> tobacco_dict = new HashMap<String, Double>();
			HashMap<String, Double> tobacco_diff_dict = new HashMap<String, Double>();

			try {
				r = sheet.getRow(row_index++);
				cell = r.getCell(attributeIndexMap.get("carrier_id"));
				carrier_id = Double.valueOf(getCellValue(cell)).intValue();
				cell = r.getCell(attributeIndexMap.get("carrier_plan_id"));
				carrier_plan_id = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("start_date"));
				start_date = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("end_date"));
				end_date = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("product_name"));
				product_name = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("plan_pdf_file_name"));
				plan_pdf_file_name = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("deductible_indiv"));
				deductible_indiv = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("deductible_family"));
				deductible_family = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("oon_deductible_individual"));
				oon_deductible_indiv = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("oon_deductible_family"));
				oon_deductible_family = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("coinsurance"));
				coinsurance = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("dr_visit_copay"));
				dr_visit_copay = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("specialist_visits_copay"));
				specialist_visit_copay = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("er_copay"));
				er_copay = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("urgent_care_copay"));
				urgent_care_copay = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("rx_copay"));
				rx_copay = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("rx_mail_copay"));
				rx_mail_copay = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("oop_max_indiv"));
				oop_max_indiv = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("oop_max_family"));
				oop_max_family = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("oon_oop_max_individual"));
				oon_oop_max_indiv = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("oon_oop_max_family"));
				oon_oop_max_family = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("in_patient_hospital"));
				in_patient_hospital = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("outpatient_diagnostic_lab"));
				outpatient_diagnostic_lab = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("outpatient_surgery"));
				outpatient_surgery = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("outpatient_diagnostic_x_ray"));
				outpatient_diagnostic_x_ray = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("outpatient_complex_imaging"));
				outpatient_complex_imaging = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("physical_occupational_therapy"));
				physical_occupational_therapy = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("state"));
				state = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("group_rating_areas"));
				group_rating_area = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("service_zones"));
				service_zones = getCellValue(cell);
				cell = r.getCell(attributeIndexMap.get("plan_pdf_file_url"));
				plan_pdf_file_url = new URL(getCellValue(cell));
			} catch (NullPointerException e) {
				PlanError error = new PlanError(PlanError.Error.INVALID_FILE, Attribute.AttributeType.NONE,
						PlanError.RateType.NONE, "", "");
				MedicalPlan new_plan = new MedicalPlan();
				new_plan.addError(error);
				plans.add(new_plan);
				return;
			}
			col_index = attributeIndexMap.get("0-18");

			cell = r.getCell(col_index++);
			firstVal = cell.getNumericCellValue();
			non_tobacco_dict.put(ageBands[0], firstVal);

			int base_index = col_index - 1;
			int temp_index = col_index + 46;

			while (col_index < temp_index) {
				System.out.println(col_index);
				cell = r.getCell(col_index - 1);
				System.out.println(getCellValue(cell));
				firstVal = cell.getNumericCellValue();
				cell = r.getCell(col_index);
				System.out.println(getCellValue(cell));
				secondVal = cell.getNumericCellValue();

				non_tobacco_dict.put(ageBands[col_index - base_index], secondVal);
				non_tobacco_diff_dict.put(ageBands[col_index - base_index], secondVal - firstVal);

				col_index++;
			}

			col_index = attributeIndexMap.get("t-0-18");
			cell = r.getCell(col_index);
			if (cell != null) {
				hasTobaccoRates = true;
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

			MedicalPlan new_plan = new MedicalPlan(carrier_id, carrier_plan_id, start_date, end_date, product_name,
					plan_pdf_file_name, deductible_indiv, deductible_family, oon_deductible_indiv,
					oon_deductible_family, coinsurance, dr_visit_copay, specialist_visit_copay, er_copay,
					urgent_care_copay, rx_copay, rx_mail_copay, oop_max_indiv, oop_max_family, oon_oop_max_indiv,
					oon_oop_max_family, in_patient_hospital, outpatient_diagnostic_lab, outpatient_surgery,
					outpatient_diagnostic_x_ray, outpatient_complex_imaging, physical_occupational_therapy,
					plan_pdf_file_url, state, group_rating_area, service_zones, non_tobacco_dict, non_tobacco_diff_dict,
					tobacco_dict, tobacco_diff_dict);

			new_plan.computeStatistics();
			new_plan.printPlan();
			plans.add(new_plan);
		}
	}

	public HashMap<String, Integer> getAttributeIndexMap(Row r) {
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
		return indexMap;
	}

}
