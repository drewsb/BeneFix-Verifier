package plan;

import java.util.ArrayList;

public interface Plan {

	public void printPlan();
	
	public void format();
	
	public void computeStatistics();
	
	public ArrayList<PlanError> getErrors();
	
	public boolean hasErrors();
	
	public void addError(PlanError e);
	
	public ArrayList<PlanWarning> getWarnings();
	
	public boolean hasWarnings();
	
	public void addWarning(PlanWarning w);
	
	public boolean hasTobaccoRates();
	
	public boolean hasPDFUrl();
	
	public String getProductName();
	
}
