package verifier;

import java.util.ArrayList;

import java.util.HashMap;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import plan.PlanWarning;
import plan.VisionPlan;

// TODO: Auto-generated Javadoc
/**
 * The Class VisionVerifier.
 */
public class VisionVerifier implements Verifier<VisionPlan> {
	
	/** The workbook. */
	final XSSFWorkbook workbook;

	/**
	 * Instantiates a new vision verifier.
	 *
	 * @param workbook the workbook
	 */
	public VisionVerifier(XSSFWorkbook workbook) {
		super();
		this.workbook = workbook;
	}

	/* (non-Javadoc)
	 * @see verifier.Verifier#verifyMonotonicity()
	 */
	@Override
	public void verifyMonotonicity() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see verifier.Verifier#verifyCV()
	 */
	@Override
	public void verifyCV() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see verifier.Verifier#verifyPDFMapping(plan.Plan)
	 */
	@Override
	public void verifyPDFMapping(VisionPlan plan) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see verifier.Verifier#getPlans()
	 */
	@Override
	public ArrayList<VisionPlan> getPlans() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see verifier.Verifier#generatePlans()
	 */
	@Override
	public void generatePlans() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see verifier.Verifier#getWarnings()
	 */
	@Override
	public ArrayList<PlanWarning> getWarnings() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see verifier.Verifier#getAttributeIndexMap()
	 */
	@Override
	public HashMap<String, Integer> getAttributeIndexMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see verifier.Verifier#verifyIncrements()
	 */
	@Override
	public void verifyIncrements() {
		// TODO Auto-generated method stub
		
	}

}
