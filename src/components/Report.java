package components;

import java.util.ArrayList;
import java.util.HashMap;

import plan.Plan;
import plan.PlanWarning;

public class Report<E extends Plan> {

	final String filename;
	final ArrayList<E> plans;
	final ArrayList<PlanWarning> warnings;
	final HashMap<String, Integer> attributeIndexMap;
	public boolean hasTobbacoRates;
	
	
	public Report(String filename, ArrayList<E> plans, ArrayList<PlanWarning> warnings, HashMap<String, Integer> attributeIndexMap) {
		super();
		this.filename = filename;
		this.plans = plans;
		this.warnings = warnings;
		this.attributeIndexMap = attributeIndexMap;
	}
	
	public String getFilename(){
		return filename;
	}
	
	public int getTotalErrorSize(){
		int sum = 0;
		for(Plan p : plans){
			if(p.hasErrors()){
				sum += p.getErrors().size();
			}
		}
		return sum;
	}
	
	public int getTotalWarningSize(){
		int sum = 0;
		for(Plan p : plans){
			if(p.hasWarnings()){
				sum += p.getWarnings().size();
			}
		}
		return sum+warnings.size();
	}
	
	public ArrayList<E> getPlans(){
		return plans;
	}
	
	

	public boolean hasAnyErrors(){
		for(Plan p : plans){
			if(p.hasErrors()){
				return false;
			}
		}
		return true;
	}
	
	
}
