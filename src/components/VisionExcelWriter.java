package components;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class VisionExcelWriter implements ExcelWriter<VisionPlan> {

	final XSSFWorkbook workbook;

	final JTextArea log;

	final ArrayList<Report<VisionPlan>> reports;

	@SuppressWarnings("unchecked")
	public VisionExcelWriter(JTextArea log, ArrayList<Report<? extends Plan>> reports) {
		super();
		this.workbook = new XSSFWorkbook();
		this.log = log;
		this.reports = new ArrayList<Report<VisionPlan>>();
		for(Report<? extends Plan> report : reports){
			this.reports.add((Report<VisionPlan>) report);
		}
	}

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
