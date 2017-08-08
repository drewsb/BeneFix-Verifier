package verifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import plan.DentalPlan;
import plan.Plan;

public class DentalVerifier implements Verifier<DentalPlan> {
	
	final XSSFWorkbook workbook;

	public DentalVerifier(XSSFWorkbook workbook) {
		super();
		this.workbook = workbook;
	}

	@Override
	public void verifyMonotonicity() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verifyCV() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verifyPDFMapping(DentalPlan plan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<DentalPlan> getPlans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generatePlans() {
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList getWarnings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, Integer> getAttributeIndexMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void verifyIncrements() {
		// TODO Auto-generated method stub
		
	}

}
