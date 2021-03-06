package components;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextArea;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import plan.Plan;
import plan.VisionPlan;

// TODO: Auto-generated Javadoc
/**
 * The Class VisionExcelWriter.
 */
public class VisionExcelWriter implements ExcelWriter<VisionPlan> {

	/** The workbook. */
	final XSSFWorkbook workbook;

	/** The log. */
	final JTextArea log;

	/** The reports. */
	final ArrayList<Report<VisionPlan>> reports;

	/**
	 * Instantiates a new vision excel writer.
	 *
	 * @param log the log
	 * @param reports the reports
	 */
	@SuppressWarnings("unchecked")
	public VisionExcelWriter(JTextArea log, ArrayList<Report<? extends Plan>> reports) {
		super();
		this.workbook = new XSSFWorkbook();
		this.log = log;
		this.reports = new ArrayList<Report<VisionPlan>>();
		for (Report<? extends Plan> report : reports) {
			this.reports.add((Report<VisionPlan>) report);
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
