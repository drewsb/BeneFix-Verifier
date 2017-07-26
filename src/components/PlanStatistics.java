package components;

public class PlanStatistics {
	
	private final Double min;
	private final Double max;
	private final Double mean;
	private final Double median;
	private final Double std_dev;
	private final Double cv;
	
	public PlanStatistics(Double min, Double max, Double median, Double mean, Double std_dev) {
		super();
		this.min = 
		this.max = max;
		this.mean = mean;
		this.median = median;
		this.std_dev = std_dev;
		this.cv = std_dev/mean;
	}
	
	public Double getMin(){
		return min;
	}
	
	public Double getMax(){
		return max;
	}
	
	public Double getMedian(){
		return median;
	}
	
	public Double getMean(){
		return mean;
	}
	
	public Double getStdDev(){
		return std_dev;
	}
	
	public Double getCV(){
		return cv;
	}



}
