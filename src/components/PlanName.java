package components;

public class PlanName {
	
	String product_name;
	int rating_area;
	
	public PlanName(String product_name, int rating_area) {
		super();
		this.product_name = product_name;
		this.rating_area = rating_area;
	}
	
	public String getProductName(){
		return product_name;
	}
	
	public int getRatingArea(){
		return rating_area;
	}

}
