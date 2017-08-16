package plan;

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Interface Plan.
 */
public interface Plan {

	/**
	 * Prints the plan.
	 */
	public void printPlan();
	
	/**
	 * Format.
	 */
	public void format();
	
	/**
	 * Compute statistics.
	 */
	public void computeStatistics();
	
	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public ArrayList<PlanError> getErrors();
	
	/**
	 * Checks for errors.
	 *
	 * @return true, if successful
	 */
	public boolean hasErrors();
	
	/**
	 * Adds the error.
	 *
	 * @param e the e
	 */
	public void addError(PlanError e);
	
	/**
	 * Gets the warnings.
	 *
	 * @return the warnings
	 */
	public ArrayList<PlanWarning> getWarnings();
	
	/**
	 * Checks for warnings.
	 *
	 * @return true, if successful
	 */
	public boolean hasWarnings();
	
	/**
	 * Adds the warning.
	 *
	 * @param w the w
	 */
	public void addWarning(PlanWarning w);
	
	/**
	 * Checks for tobacco rates.
	 *
	 * @return true, if successful
	 */
	public boolean hasTobaccoRates();
	
	/**
	 * Checks for PDF url.
	 *
	 * @return true, if successful
	 */
	public boolean hasPDFUrl();
	
	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName();
	
}
