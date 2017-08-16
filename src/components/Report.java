package components;

import java.util.ArrayList;
import java.util.HashMap;

import plan.Plan;
import plan.PlanWarning;

// TODO: Auto-generated Javadoc
/**
 * The Class Report.
 *
 * @param <E> the element type
 */
public class Report<E extends Plan> {

	/** The filename. */
	final String filename;
	
	/** The plans. */
	final ArrayList<E> plans;
	
	/** The warnings. */
	final ArrayList<PlanWarning> warnings;
	
	/** The attribute index map. */
	final HashMap<String, Integer> attributeIndexMap;
	
	/** The has tobbaco rates. */
	public boolean hasTobbacoRates;

	/**
	 * Instantiates a new report.
	 *
	 * @param filename the filename
	 * @param plans the plans
	 * @param warnings the warnings
	 * @param attributeIndexMap the attribute index map
	 */
	public Report(String filename, ArrayList<E> plans, ArrayList<PlanWarning> warnings,
			HashMap<String, Integer> attributeIndexMap) {
		super();
		this.filename = filename;
		this.plans = plans;
		this.warnings = warnings;
		this.attributeIndexMap = attributeIndexMap;
	}

	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Gets the total error size.
	 *
	 * @return the total error size
	 */
	public int getTotalErrorSize() {
		int sum = 0;
		for (Plan p : plans) {
			if (p.hasErrors()) {
				sum += p.getErrors().size();
			}
		}
		return sum;
	}

	/**
	 * Gets the total warning size.
	 *
	 * @return the total warning size
	 */
	public int getTotalWarningSize() {
		int sum = 0;
		for (Plan p : plans) {
			if (p.hasWarnings()) {
				sum += p.getWarnings().size();
			}
		}
		return sum + warnings.size();
	}

	/**
	 * Gets the plans.
	 *
	 * @return the plans
	 */
	public ArrayList<E> getPlans() {
		return plans;
	}

	/**
	 * Checks for any errors.
	 *
	 * @return true, if successful
	 */
	public boolean hasAnyErrors() {
		for (Plan p : plans) {
			if (p.hasErrors()) {
				return false;
			}
		}
		return true;
	}

}
