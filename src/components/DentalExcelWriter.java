package components;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import plan.DentalPlan;
import plan.Plan;

// TODO: Auto-generated Javadoc
/**
 * The Class DentalExcelWriter.
 */
public class DentalExcelWriter implements ExcelWriter<DentalPlan> {

	/** The workbook. */
	final XSSFWorkbook workbook;

	/** The log. */
	final JTextArea log;

	/** The reports. */
	final ArrayList<Report<DentalPlan>> reports;

	/**
	 * Instantiates a new dental excel writer.
	 *
	 * @param log the log
	 * @param reports the reports
	 */
	@SuppressWarnings("unchecked")
	public DentalExcelWriter(JTextArea log, ArrayList<Report<? extends Plan>> reports) {
		super();
		this.workbook = new XSSFWorkbook();
		this.log = log;
		this.reports = new ArrayList<Report<DentalPlan>>();
		for (Report<? extends Plan> report : reports) {
			this.reports.add((Report<DentalPlan>) report);
		}
	}

	/* (non-Javadoc)
	 * @see components.ExcelWriter#populateLog()
	 */
	@Override
	public void populateLog() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see components.ExcelWriter#populateErrorSummary()
	 */
	@Override
	public void populateErrorSummary() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see components.ExcelWriter#populateStatisticsSummary()
	 */
	@Override
	public void populateStatisticsSummary() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see components.ExcelWriter#getWorkbooks()
	 */
	@Override
	public HashMap<String, XSSFWorkbook> getWorkbooks() {
		// TODO Auto-generated method stub
		return null;
	}

}
