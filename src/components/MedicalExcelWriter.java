package components;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import components.Attribute.AttributeType;
import components.Main.Carrier;
import plan.*;
import plan.PlanError.*;
import plan.PlanWarning.*;

/*
 * Uses Apache Poi package found at https://www.apache.org. 
 */
public class MedicalExcelWriter implements ExcelWriter<MedicalPlan> {

	final JTextArea log;

	final ArrayList<Report<MedicalPlan>> reports;

	final HashMap<Report<MedicalPlan>, XSSFWorkbook> workbookMap;

	static XSSFColor xred = new XSSFColor(new java.awt.Color(240, 128, 128));

	static XSSFColor xyellow = new XSSFColor(new java.awt.Color(244, 241, 66));

	@SuppressWarnings("unchecked")
	public MedicalExcelWriter(JTextArea log, ArrayList<Report<? extends Plan>> reports) {
		super();
		this.log = log;
		this.reports = new ArrayList<Report<MedicalPlan>>();
		for (Report<? extends Plan> report : reports) {
			this.reports.add((Report<MedicalPlan>) report);
		}
		this.workbookMap = new HashMap<Report<MedicalPlan>, XSSFWorkbook>();
	}

	public void generateErrorStyles() {

	}

	public static void populateRow(XSSFWorkbook workbook, HashMap<String, Integer> attributeIndexMap, Sheet sheet,
			Row row, MedicalPlan p) {
		int colCount = 0;
		Cell cell = row.createCell(colCount++);
		if (attributeIndexMap.containsKey("carrier")) {
			cell.setCellValue(p.carrier.toString());
			cell = row.createCell(colCount++);
		}
		if (attributeIndexMap.containsKey("carrier_id")) {
			cell.setCellValue(p.carrier_id);
			cell = row.createCell(colCount++);
		}
		if (attributeIndexMap.containsKey("carrier_plan_id")) {
			cell.setCellValue((String) p.carrier_plan_id);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("start_date")) {
			cell.setCellValue((String) p.start_date);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("end_date")) {
			cell.setCellValue((String) p.end_date);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("product_name")) {
			cell.setCellValue((String) p.product_name);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("plan_pdf_file_name")) {
			cell.setCellValue((String) p.plan_pdf_file_name);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("state")) {
			cell.setCellValue((String) p.state);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("group_rating_areas")) {
			cell.setCellValue((String) p.group_rating_area);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("service_zones")) {
			cell.setCellValue((String) p.service_zones);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("deductible_indiv")) {
			cell.setCellValue((String) p.deductible_indiv);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("deductible_family")) {
			cell.setCellValue((String) p.deductible_family);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("oon_deductible_individual")) {
			cell.setCellValue((String) p.oon_deductible_indiv);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("oon_deductible_family")) {
			cell.setCellValue((String) p.oon_deductible_family);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("coinsurance")) {
			cell.setCellValue((String) p.coinsurance);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("dr_visit_copay")) {
			cell.setCellValue((String) p.dr_visit_copay);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("specialist_visits_copay")) {
			cell.setCellValue((String) p.specialist_visit_copay);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("er_copay")) {
			cell.setCellValue((String) p.er_copay);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("urgent_care_copay")) {
			cell.setCellValue((String) p.urgent_care_copay);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("rx_copay")) {
			cell.setCellValue((String) p.rx_copay);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("rx_mail_copay")) {
			cell.setCellValue((String) p.rx_mail_copay);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("oop_max_indiv")) {
			cell.setCellValue((String) p.oop_max_indiv);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("oop_max_family")) {
			cell.setCellValue((String) p.oop_max_family);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("oon_oop_max_individual")) {
			cell.setCellValue((String) p.oon_oop_max_indiv);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("oon_oop_max_family")) {
			cell.setCellValue((String) p.oon_oop_max_family);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("in_patient_hospital")) {
			cell.setCellValue((String) p.in_patient_hospital);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("outpatient_diagnostic_lab")) {
			cell.setCellValue((String) p.outpatient_diagnostic_lab);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("outpatient_surgery")) {
			cell.setCellValue((String) p.outpatient_surgery);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("outpatient_diagnostic_x_ray")) {
			cell.setCellValue((String) p.outpatient_diagnostic_x_ray);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("outpatient_complex_imaging")) {
			cell.setCellValue((String) p.outpatient_complex_imaging);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("physical_occupational_therapy")) {
			cell.setCellValue((String) p.physical_occupational_therapy);
			cell = row.createCell(colCount++);
		}

		if (attributeIndexMap.containsKey("plan_pdf_file_url")) {
			cell.setCellValue(p.plan_pdf_url.toString());
			cell = row.createCell(colCount++);
		}
		cell.setCellValue(p.non_tobacco_dict.get("0-18"));
		cell = row.createCell(colCount++);
		cell.setCellValue(p.non_tobacco_dict.get("19-20"));
		for (int i = 0; i < 44; i++) {
			cell = row.createCell(colCount++);
			String index = String.format("%d", i + 21);
			cell.setCellValue(p.non_tobacco_dict.get(index));
		}

		cell = row.createCell(colCount++);
		String max_age_string = String.format("%d+", 65);
		cell.setCellValue(p.non_tobacco_dict.get(max_age_string));

		if (p.hasTobaccoRates()) {
			colCount++;
			cell = row.createCell(colCount++);
			cell.setCellValue(p.non_tobacco_dict.get("0-18"));
			cell = row.createCell(colCount++);
			cell.setCellValue(p.non_tobacco_dict.get("19-20"));
			for (int i = 0; i < 44; i++) {
				cell = row.createCell(colCount++);
				String index = String.format("%d", i + 21);
				cell.setCellValue(p.non_tobacco_dict.get(index));
			}

			cell = row.createCell(colCount++);
			cell.setCellValue(p.non_tobacco_dict.get(max_age_string));
		}

		XSSFCellStyle error_highlighter = workbook.createCellStyle();
		XSSFCellStyle warning_highlighter = workbook.createCellStyle();
		
		warning_highlighter.setFillForegroundColor(xyellow);
		warning_highlighter.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		error_highlighter.setFillForegroundColor(xred);
		error_highlighter.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		fillWarnings(p.getWarnings(), row, warning_highlighter);
		fillErrors(p.getErrors(), row, error_highlighter);
	}

	public static void fillErrors(ArrayList<PlanError> errors, Row row, XSSFCellStyle highlighter) {
		for (PlanError error : errors) {
			int location = 0;
			Cell cell;
			AttributeType att = error.getAttribute();
			AttributeType secondAtt = error.getSecondAttribute();
			RateType rateType = error.getType();

			if (att == AttributeType.NONE) {
				cell = row.getCell(location);
				cell.setCellStyle(highlighter);
			} else {
				location = Attribute.attributes.indexOf(att);
				if (rateType == RateType.TOBACCO) {
					location += 48;
				}

				cell = row.getCell(location);
				cell.setCellStyle(highlighter);
				
				if(secondAtt != null){
					location = Attribute.attributes.indexOf(secondAtt);
					cell = row.getCell(location);
					cell.setCellStyle(highlighter);
				}
				
				if(rateType == RateType.BOTH){
					location += 48;
					cell = row.getCell(location);
					cell.setCellStyle(highlighter);
				}
			}
		}
	}

	public static void fillWarnings(ArrayList<PlanWarning> warnings, Row row, XSSFCellStyle highlighter) {
		for (PlanWarning warning : warnings) {
			int location = 0;
			Cell cell;
			Warning warningType = warning.getWarningType();
			AttributeType att = warning.getAttributeType();

			if (warningType != Warning.PLAN_TYPE_NOT_FOUND) {
				location = Attribute.attributes.indexOf(att);
				cell = row.getCell(location);
				cell.setCellStyle(highlighter);
			}
		}
	}

	/*
	 * Input: Array of page objects. Creates a new workbook sheet every
	 * compilation. First populates the excel sheet with template data, then the
	 * necessary data from the array of pages. Output file is called
	 * "BenefixData.xlsx".
	 */

	@Override
	public void populateLog() throws FileNotFoundException, IOException {
		Sheet sheet;
		for (Report<MedicalPlan> r : reports) {
			XSSFWorkbook workbook = new XSSFWorkbook();
			if (r.getTotalErrorSize()==0 & r.getTotalWarningSize() == 0) {
				log.append(String.format("All tests passed for %s.\nNo output file produced.\n", r.getFilename()));
				continue;
			}
			sheet = workbook.createSheet("Error Log");
			Row row = sheet.getRow(0);
			int row_index = 0;
			int col_index = 0;
			row = sheet.createRow(row_index);
			Cell cell = row.createCell(col_index++);
			cell.setCellValue("Plan Name");
			cell = row.createCell(col_index++);
			cell.setCellValue("Error Type");
			cell = row.createCell(col_index++);
			cell.setCellValue("Warning Type");
			cell = row.createCell(col_index++);
			cell.setCellValue("Description");
			for (MedicalPlan p : r.getPlans()) {
				boolean alreadyFilledPlanName = false;
				if (!p.hasErrors() & !p.hasWarnings()) {
					continue;
				}
				col_index = 0;
				for (PlanError e : p.getErrors()) {
					row = sheet.createRow(++row_index);
					cell = row.createCell(col_index++);
					if (!alreadyFilledPlanName) {
						cell.setCellValue(p.product_name);
					}
					alreadyFilledPlanName = true;
					cell = row.createCell(col_index++);
					cell.setCellValue(e.getErrorType());

					cell = row.createCell(++col_index);
					cell.setCellValue(e.getErrorMessage());
					col_index = 0;
				}
				for (PlanWarning w : p.getWarnings()) {
					row = sheet.createRow(++row_index);
					cell = row.createCell(col_index++);
					if (!alreadyFilledPlanName) {
						cell.setCellValue(p.product_name);
					}
					alreadyFilledPlanName = true;
					cell = row.createCell(++col_index);
					cell.setCellValue(w.getWarningType().toString());

					cell = row.createCell(++col_index);
					cell.setCellValue(w.getWarningMessage());
					col_index = 0;
				}
			}

			for (int x = 0; x < 5; x++) {
				sheet.autoSizeColumn(x);
			}

			this.workbookMap.put(r, workbook);
		}
		log.append("Finished populating error log.\n");
	}

	@Override
	public void populateErrorSummary() throws IOException {
		for (Map.Entry<Report<MedicalPlan>, XSSFWorkbook> entry : workbookMap.entrySet()) {
			HashMap<String, Integer> attributeIndexMap = entry.getKey().attributeIndexMap;
			XSSFWorkbook workbook = entry.getValue();
			Sheet sheet = workbook.createSheet("Error Summary");

			int row_index = 0;
			int col_index = 0;
			Row row = sheet.createRow(0);
			row = sheet.createRow(row_index++);
			Cell cell;

			for (Attribute.AttributeType att : Attribute.attributes) {
				String attString = att.toString().toLowerCase();
				if (attributeIndexMap.containsKey(attString)) {
					cell = row.createCell(col_index++);
					cell.setCellValue(attString);
				}
			}

			for (int i = 32; i < Attribute.attributes.size() - 1; i++) {
				cell = row.createCell(col_index++);
				cell.setCellValue(Attribute.attributes.get(i).toString().toLowerCase());
			}
			
			if(entry.getKey().hasTobbacoRates){
				col_index++;
				for (int i = 32; i < Attribute.attributes.size() - 1; i++) {
					cell = row.createCell(col_index++);
					cell.setCellValue(Attribute.attributes.get(i).toString().toLowerCase());
				}
			}

			for (MedicalPlan plan : entry.getKey().getPlans()) {
				if (!plan.hasErrors() & !plan.hasWarnings()) {
					continue;
				}
				row = sheet.createRow(row_index++);
				populateRow(workbook, attributeIndexMap, sheet, row, plan);
			}

			for (int x = 0; x < row.getPhysicalNumberOfCells(); x++) {
				sheet.autoSizeColumn(x);
			}

		}
		log.append("Finished populating error summary.\n");
	}

	@Override
	public void populateStatisticsSummary() throws IOException {
		for (Map.Entry<Report<MedicalPlan>, XSSFWorkbook> entry : workbookMap.entrySet()) {
			XSSFWorkbook workbook = entry.getValue();
			Sheet sheet = workbook.createSheet("Statistics Summary");

			int row_index = 0;
			int col_index = 0;
			Row row = sheet.createRow(0);
			row = sheet.createRow(row_index);
			Cell cell = row.createCell(col_index);
			cell.setCellValue("Non-Tobacco Rates");

			row_index = 1;
			row = sheet.createRow(row_index);
			cell = row.createCell(col_index++);
			cell.setCellValue("Plan Name");
			cell = row.createCell(col_index++);
			cell.setCellValue("Min");
			cell = row.createCell(col_index++);
			cell.setCellValue("Max");
			cell = row.createCell(col_index++);
			cell.setCellValue("Median");
			cell = row.createCell(col_index++);
			cell.setCellValue("Mean");
			cell = row.createCell(col_index++);
			cell.setCellValue("Std Dev");
			cell = row.createCell(col_index++);
			cell.setCellValue("CV");

			if (entry.getKey().hasTobbacoRates) {
				row_index = 0;
				row = sheet.getRow(row_index);
				cell = row.createCell(col_index);
				cell.setCellValue("Non-Tobacco Rates");

				row_index = 1;
				row = sheet.getRow(row_index);
				cell = row.createCell(col_index++);
				cell.setCellValue("Plan Name");
				cell = row.createCell(col_index++);
				cell.setCellValue("Min");
				cell = row.createCell(col_index++);
				cell.setCellValue("Max");
				cell = row.createCell(col_index++);
				cell.setCellValue("Median");
				cell = row.createCell(col_index++);
				cell.setCellValue("Mean");
				cell = row.createCell(col_index++);
				cell.setCellValue("Std Dev");
				cell = row.createCell(col_index++);
				cell.setCellValue("CV");
			}

			for (MedicalPlan plan : entry.getKey().getPlans()) {
				col_index = 0;
				row = sheet.createRow(++row_index);
				cell = row.createCell(col_index++);
				cell.setCellValue(plan.product_name);
				cell = row.createCell(col_index++);
				cell.setCellValue(plan.non_tobacco_stats.getMin());
				cell = row.createCell(col_index++);
				cell.setCellValue(plan.non_tobacco_stats.getMax());
				cell = row.createCell(col_index++);
				cell.setCellValue(plan.non_tobacco_stats.getMedian());
				cell = row.createCell(col_index++);
				cell.setCellValue(String.format("%.2f", plan.non_tobacco_stats.getMean()));
				cell = row.createCell(col_index++);
				cell.setCellValue(String.format("%.2f", plan.non_tobacco_stats.getStdDev()));
				cell = row.createCell(col_index++);
				cell.setCellValue(String.format("%.2f", 100 * plan.non_tobacco_stats.getCV()) + "%");
			}

			for (int x = 0; x < row.getPhysicalNumberOfCells(); x++) {
				sheet.autoSizeColumn(x);
			}
		}

		log.append("Finished populating statistics summary.\n");
	}

	@Override
	public HashMap<String, XSSFWorkbook> getWorkbooks() {
		HashMap<String, XSSFWorkbook> filenameWorkbookMap = new HashMap<String, XSSFWorkbook>();
		for (Map.Entry<Report<MedicalPlan>, XSSFWorkbook> entry : workbookMap.entrySet()) {
			filenameWorkbookMap.put(entry.getKey().filename, entry.getValue());
		}
		return filenameWorkbookMap;
	}
}