package components;

import java.io.FileNotFoundException;

import java.io.IOException;
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
import components.PlanError.RateType;

/*
 * Uses Apache Poi package found at https://www.apache.org. 
 */
public class MedicalExcelWriter implements ExcelWriter<MedicalPlan> {

	final JTextArea log;

	final ArrayList<Report<MedicalPlan>> reports;

	final HashMap<Report<MedicalPlan>, XSSFWorkbook> workbookMap;

	static XSSFColor xred = new XSSFColor(new java.awt.Color(240, 128, 128));

	static XSSFCellStyle highlighter;

	public String[] templateData = { "carrier_id", "carrier_plan_id", "start_date", "end_date", "product_name",
			"plan_pdf_file_name", "deductible_indiv", "deductible_family", "oon_deductible_individual",
			"oon_deductible_family", "coinsurance", "dr_visit_copay", "specialist_visits_copay", "er_copay",
			"urgent_care_copay", "rx_copay", "rx_mail_copay", "oop_max_indiv", "oop_max_family",
			"oon_oop_max_individual", "oon_oop_max_family", "in_patient_hospital", "outpatient_diagnostic_lab",
			"outpatient_surgery", "outpatient_diagnostic_x_ray", "outpatient_complex_imaging",
			"physical_occupational_therapy", "state", "group_rating_areas", "service_zones", "zero_eighteen",
			"nineteen_twenty", "twenty_one", "twenty_two", "twenty_three", "twenty_four", "twenty_five", "twenty_six",
			"twenty_seven", "twenty_eight", "twenty_nine", "thirty", "thirty_one", "thirty_two", "thirty_three",
			"thirty_four", "thirty_five", "thirty_six", "thirty_seven", "thirty_eight", "thirty_nine", "forty",
			"forty_one", "forty_two", "forty_three", "forty_four", "forty_five", "forty_six", "forty_seven",
			"forty_eight", "forty_nine", "fifty", "fifty_one", "fifty_two", "fifty_three", "fifty_four", "fifty_five",
			"fifty_six", "fifty_seven", "fifty_eight", "fifty_nine", "sixty", "sixty_one", "sixty_two", "sixty_three",
			"sixty_four", "sixty_five_plus" };

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

	public static void populateRow(XSSFWorkbook workbook, Sheet sheet, Row row, MedicalPlan p) {
		int colCount = 0;
		Cell cell = row.createCell(colCount++);
		cell.setCellValue(p.carrier_id);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.carrier_plan_id);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.start_date);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.end_date);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.product_name);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.plan_pdf_file_name);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.deductible_indiv);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.deductible_family);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.oon_deductible_indiv);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.oon_deductible_family);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.coinsurance);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.dr_visit_copay);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.specialist_visit_copay);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.er_copay);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.urgent_care_copay);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.rx_copay);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.rx_mail_copay);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.oop_max_indiv);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.oop_max_family);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.oon_oop_max_indiv);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.oon_oop_max_family);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.in_patient_hospital);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.outpatient_diagnostic_lab);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.outpatient_surgery);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.outpatient_diagnostic_x_ray);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.outpatient_complex_imaging);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.physical_occupational_therapy);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.state);
		cell = row.createCell(colCount++);
		cell.setCellValue((String) p.group_rating_area);
		cell = row.createCell(colCount++);
		cell.setCellValue(p.service_zones);
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

		XSSFCellStyle highlighter = workbook.createCellStyle();
		highlighter.setFillForegroundColor(xred);
		highlighter.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		for (PlanError error : p.getErrors()) {
			int location = 0;
			AttributeType att = error.attribute;
			RateType rateType = error.type;

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
			if (r.getTotalErrorSize() == 0) {
				log.append(String.format("All tests passed for %s.\nNo output file produced.", r.getFilename()));
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
			cell.setCellValue("Description");
			for (MedicalPlan p : r.getPlans()) {
				boolean alreadyFilledPlanName = false;
				if (!p.hasErrors()) {
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

					cell = row.createCell(col_index);
					cell.setCellValue(e.getErrorMessage());
					col_index = 0;
				}
			}

			for (int x = 0; x < row.getPhysicalNumberOfCells(); x++) {
				sheet.autoSizeColumn(x);
			}

			this.workbookMap.put(r, workbook);
		}
		log.append("Finished populating error log.\n");
	}

	@Override
	public void populateErrorSummary() throws IOException {
		for (Map.Entry<Report<MedicalPlan>, XSSFWorkbook> entry : workbookMap.entrySet()) {
			XSSFWorkbook workbook = entry.getValue();
			Sheet sheet = workbook.createSheet("Error Summary");

			int row_index = 0;
			int col_index = 0;
			Row row = sheet.createRow(0);
			row = sheet.createRow(row_index++);
			Cell cell;
			
			for(Attribute.AttributeType att : Attribute.attributes){
				if(att == AttributeType.NONE){
					continue;
				}
				cell = row.createCell(col_index++);
				cell.setCellValue(att.toString().toLowerCase());
			}
			
			for(MedicalPlan plan : entry.getKey().getPlans()){
				if(!plan.hasErrors()){
					continue;
				}
				row = sheet.createRow(row_index++);
				populateRow(workbook, sheet, row, plan);
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
			
			if(entry.getKey().hasTobbacoRates){
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
				cell.setCellValue(String.format("%.2f", 100*plan.non_tobacco_stats.getCV()) + "%");
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