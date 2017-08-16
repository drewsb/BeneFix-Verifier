package plan;

// TODO: Auto-generated Javadoc
/**
 * The Class PlanStatistics.
 */
public class PlanStatistics {
	
	/** The min. */
	private final Double min;
	
	/** The max. */
	private final Double max;
	
	/** The mean. */
	private final Double mean;
	
	/** The median. */
	private final Double median;
	
	/** The std dev. */
	private final Double std_dev;
	
	/** The cv. */
	private final Double cv;
	
	/**
	 * Instantiates a new plan statistics.
	 *
	 * @param min the min
	 * @param max the max
	 * @param median the median
	 * @param mean the mean
	 * @param std_dev the std dev
	 */
	public PlanStatistics(Double min, Double max, Double median, Double mean, Double std_dev) {
		super();
		this.min = 
		this.max = max;
		this.mean = mean;
		this.median = median;
		this.std_dev = std_dev;
		this.cv = Double.parseDouble(String.format("%.2f",std_dev/mean));
	}
	
	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	public Double getMin(){
		return min;
	}
	
	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	public Double getMax(){
		return max;
	}
	
	/**
	 * Gets the median.
	 *
	 * @return the median
	 */
	public Double getMedian(){
		return median;
	}
	
	/**
	 * Gets the mean.
	 *
	 * @return the mean
	 */
	public Double getMean(){
		return mean;
	}
	
	/**
	 * Gets the std dev.
	 *
	 * @return the std dev
	 */
	public Double getStdDev(){
		return std_dev;
	}
	
	/**
	 * Gets the cv.
	 *
	 * @return the cv
	 */
	public Double getCV(){
		return cv;
	}



}
