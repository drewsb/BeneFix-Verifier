package verifier;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import plan.PlanWarning;
import plan.VisionPlan;

public class VisionVerifier implements Verifier<VisionPlan> {
	
	final XSSFWorkbook workbook;

	public VisionVerifier(XSSFWorkbook workbook) {
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
	public void verifyPDFMapping(VisionPlan plan) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<VisionPlan> getPlans() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void generatePlans() {
		// TODO Auto-generated method stub
	}

	@Override
	public ArrayList<PlanWarning> getWarnings() {
		// TODO Auto-generated method stub
		return null;
	}

}
