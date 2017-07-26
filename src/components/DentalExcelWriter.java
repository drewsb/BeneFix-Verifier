package components;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DentalExcelWriter implements ExcelWriter<DentalPlan>{

	final XSSFWorkbook workbook;

	final JTextArea log;

	final ArrayList<Report<DentalPlan>> reports;

	@SuppressWarnings("unchecked")
	public DentalExcelWriter(JTextArea log, ArrayList<Report<? extends Plan>> reports) {
		super();
		this.workbook = new XSSFWorkbook();
		this.log = log;
		this.reports = new ArrayList<Report<DentalPlan>>();
		for(Report<? extends Plan> report : reports){
			this.reports.add((Report<DentalPlan>) report);
		}	}

	@Override
	public void populateLog() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void populateErrorSummary() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void populateStatisticsSummary() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, XSSFWorkbook> getWorkbooks() {
		// TODO Auto-generated method stub
		return null;
	}

}
