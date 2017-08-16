package plan;

// TODO: Auto-generated Javadoc
/**
 * The Class PlanName.
 */
public class PlanName {
	
	/** The product name. */
	String product_name;
	
	/** The rating area. */
	int rating_area;
	
	/**
	 * Instantiates a new plan name.
	 *
	 * @param product_name the product name
	 * @param rating_area the rating area
	 */
	public PlanName(String product_name, int rating_area) {
		super();
		this.product_name = product_name;
		this.rating_area = rating_area;
	}
	
	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName(){
		return product_name;
	}
	
	/**
	 * Gets the rating area.
	 *
	 * @return the rating area
	 */
	public int getRatingArea(){
		return rating_area;
	}

}
