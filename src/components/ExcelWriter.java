package components;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import plan.Plan;

// TODO: Auto-generated Javadoc
/**
 * <h1>ExcelWrter</h1>
 * Interface for populating excel sheets depending on the plan type (Medical, Dental, or Vision). 
 * <p>
 *
 * @author  Drew Boyette
 * @version 1.0
 * @param <E> the element type
 * @since   2017-08-16
 */
public interface ExcelWriter<E extends Plan> {

	 /**
 	 * Populates an error log consisting of all plan warnings and errors and descriptions for each. 
 	 *
 	 * @throws FileNotFoundException the file not found exception
 	 * @throws IOException Signals that an I/O exception has occurred.
 	 * @see IOException
 	 */
	public void populateLog() throws FileNotFoundException, IOException;

	/**
	 * Populates an error summary consisting of all plan data with cells containing warnings
	 * highlighted in yellow, and cells containing errors highlighted in red. 
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see IOException
	 */
	public void populateErrorSummary() throws IOException;

	/**
	 * Populates a statistics summary showing the min, max, median, mean, standard dev, and
	 * covariance for each plan.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @see IOException
	 */
	public void populateStatisticsSummary() throws IOException;

	/**
	 * Returns a mapping of report filenames and XSSF workbooks. 
	 *
	 * @return HashMap containing report filenames and their associated workbooks
	 * @see IOException
	 */
	public HashMap<String, XSSFWorkbook> getWorkbooks();

}
