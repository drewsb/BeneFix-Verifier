package components;

import java.util.ArrayList;

public class Report<E extends Plan> {

	final String filename;
	final ArrayList<E> plans;
	public boolean hasTobbacoRates;
	
	
	public Report(String filename, ArrayList<E> plans) {
		super();
		this.filename = filename;
		this.plans = plans;
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
