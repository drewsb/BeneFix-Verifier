package components;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import plan.Plan;

public interface ExcelWriter<E extends Plan> {

	public void populateLog() throws FileNotFoundException, IOException;
	
	public void populateErrorSummary() throws IOException;
	
	public void populateStatisticsSummary() throws IOException;
	
	public HashMap<String,XSSFWorkbook> getWorkbooks();
	
}
