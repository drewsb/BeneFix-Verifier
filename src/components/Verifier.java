package components;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface Verifier<E extends Plan> {

	public void verifyMonotonicity();

	public void verifyCV();

	public void verifyPDFMapping() throws IOException;
	
	public ArrayList<E> getPlans();
	
	public void generatePlans() throws MalformedURLException;
	
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
