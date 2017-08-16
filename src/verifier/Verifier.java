package verifier;

import java.io.IOException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import plan.Plan;
import plan.PlanWarning;

// TODO: Auto-generated Javadoc
/**
 * The Interface Verifier.
 *
 * @param <E> the element type
 */
public interface Verifier<E extends Plan> {

	/**
	 * Verify monotonicity.
	 */
	public void verifyMonotonicity();
	
	/**
	 * Verify increments.
	 */
	public void verifyIncrements();

	/**
	 * Verify CV.
	 */
	public void verifyCV();

	/**
	 * Verify PDF mapping.
	 *
	 * @param plan the plan
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void verifyPDFMapping(E plan) throws IOException;
		
	/**
	 * Gets the plans.
	 *
	 * @return the plans
	 */
	public ArrayList<E> getPlans();
	
	/**
	 * Generate plans.
	 *
	 * @throws MalformedURLException the malformed URL exception
	 */
	public void generatePlans() throws MalformedURLException;
	
	/**
	 * Gets the warnings.
	 *
	 * @return the warnings
	 */
	public ArrayList<PlanWarning> getWarnings();
	
	/**
	 * Gets the attribute index map.
	 *
	 * @return the attribute index map
	 */
	public HashMap<String, Integer> getAttributeIndexMap();
	
	/**
	 * Gets the cell value.
	 *
	 * @param cell the cell
	 * @return the cell value
	 */
	/*
	 * Default method to retrieve the value of a cell from an Excel workbook.
	 * Note: This måethod assumes that the parser is parsing an Excel file, NOT
	 * a PDF
	 */
	default String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellTypeEnum()) {
		case NUMERIC:
			return Double.toString(cell.getNumericCellValue());
		case STRING:
			return cell.getStringCellValue();
		default:
			return null;
		}
	}

	/**
	 * Gets the num rows.
	 *
	 * @param sheet the sheet
	 * @return the num rows
	 */
	/*
	 * Default method to retrieve the number of rows which contain non-empty
	 * cells Note: This method assumes that the parser is parsing an Excel file,
	 * NOT a PDF
	 */
	default int getNumRows(Sheet sheet) {
		int row_index = 0;
		int col_index = 0;
		Row r;
		Cell cell;
		r = sheet.getRow(row_index++);
		while (r == null & row_index < 1000) {
			r = sheet.getRow(row_index++);
		}
		if (row_index == 1000) {
			return -1; // Return -1, invalid excel file
		}
		cell = r.getCell(col_index);
		while (getCellValue(cell).isEmpty()) {
			r = sheet.getRow(row_index++);
			cell = r.getCell(col_index);
		}
		while (getCellValue(cell) != null) {
			r = sheet.getRow(row_index++);
			if (r == null) {
				return row_index;
			}
			cell = r.getCell(col_index);
			if (cell == null) {
				return row_index;
			}
		}
		return row_index - 1;
	}

}
