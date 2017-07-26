package components;

import java.util.ArrayList;

public interface Plan {

	public void printPlan();
	
	public void format();
	
	public void computeStatistics();
	
	public ArrayList<PlanError> getErrors();
	
	public boolean hasErrors();
	
	public void addError(PlanError e);
	
	public boolean hasTobaccoRates();
	
}
