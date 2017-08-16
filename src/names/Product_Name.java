package names;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;

import components.Main.Carrier;

// TODO: Auto-generated Javadoc
/**
 * The Class Product_Name.
 */
public class Product_Name {
	
	/** The original name. */
	public final String original_name;
	
	/** The name tokens. */
	public final String[] name_tokens;
	
	/** The state. */
	public State state;
	
	/** The metal. */
	public Metal metal;
	
	/** The plan. */
	public Plan plan;
	
	/** The carrier. */
	public Carrier carrier;
	
	/** The rx copay. */
	public String rx_copay;
	
	/** The deductible. */
	public String deductible;
	
	/** The coinsurance. */
	public String coinsurance;
	
	/** The is HSA plan. */
	public boolean isHSAPlan;
	
	/** The is plus plan. */
	public boolean isPlusPlan;
	
	/** The is advantage plan. */
	public boolean isAdvantagePlan;
	
	/** The is off exchange plan. */
	public boolean isOffExchangePlan;

	/** The Constant states. */
	public final static ArrayList<State> states = new ArrayList<State>(Arrays.asList(State.values()));
	
	/** The Constant plans. */
	public final static ArrayList<Plan> plans = new ArrayList<Plan>(Arrays.asList(Plan.values()));
	
	/** The Constant metals. */
	public final static ArrayList<Metal> metals = new ArrayList<Metal>(Arrays.asList(Metal.values()));
	
	/** The Constant carriers. */
	public final static ArrayList<Carrier> carriers = new ArrayList<Carrier>(Arrays.asList(Carrier.values()));
	
	
	/** The metal abbrev map. */
	public final HashMap<Metal, String[]> metalAbbrevMap = new HashMap<Metal, String[]>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put(Metal.Bronze, new String[] {"brz"});
			put(Metal.Silver, new String[] {"slv"});
			put(Metal.Gold, new String[] {"gld"});
			put(Metal.Platinum, new String[] {"plt"});
			put(Metal.None, new String[] {});
		}
	};
	
	
	/**
	 * Instantiates a new product name.
	 *
	 * @param original_name the original name
	 */
	public Product_Name(String original_name) {
		super();
		this.original_name = original_name;
		this.name_tokens = original_name.toLowerCase().split("\\s");
		this.metal = getMetal();
		this.plan = getPlan();
	}

	/**
	 * The Enum State.
	 */
	public enum State {
		
		/** The pa. */
		PA, 
 /** The oh. */
 OH, 
 /** The nj. */
 NJ, 
 /** The ca. */
 CA
	}
	
	/**
	 * The Enum Metal.
	 */
	public enum Metal {
		
		/** The Bronze. */
		Bronze, 
 /** The Silver. */
 Silver, 
 /** The Gold. */
 Gold, 
 /** The Platinum. */
 Platinum, 
 /** The None. */
 None
	}
	
	/**
	 * The Enum Plan.
	 */
	public enum Plan {
		
		/** The Choice plus. */
		Choice_Plus, 
 /** The epo. */
 EPO, 
 /** The dpos. */
 DPOS, 
 /** The ppo. */
 PPO, 
 /** The hmo. */
 HMO, 
 /** The pos. */
 POS, 
 /** The hsa. */
 HSA, 
 /** The qpos. */
 QPOS, 
 /** The Savings plus. */
 Savings_Plus, 
 /** The Wellspan HN option. */
 Wellspan_HNOption, 
 /** The LVH N HN option. */
 LVHN_HNOption, 
 /** The None. */
 None
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return original_name;
	}
	
	/**
	 * Gets the metal.
	 *
	 * @return the metal
	 */
	public Metal getMetal() {
		for(String t : name_tokens){
			for(Metal m : metals){
				if(t.contains(m.toString().toLowerCase())){
					return m;
				}
				for(String abbrev : metalAbbrevMap.get(m)){
					if(t.contains(abbrev)){
						return m;
					}
				}
			}
		}
		return Metal.None;
	}
	
	/**
	 * Gets the plan.
	 *
	 * @return the plan
	 */
	public Plan getPlan() {
		String[] strToks = original_name.toLowerCase().split("\\s");
		for(String str : strToks){
			for(Plan p : plans){
				if(p.toString().toLowerCase().equals(str)){
					return p;
				}
			}
		}
		return Plan.None;
	}
	
	/**
	 * Matches.
	 *
	 * @param p_name the p name
	 * @return true, if successful
	 */
	public boolean matches(String p_name){
		String[] temp_tokens = p_name.toLowerCase().split("\\s");
		int index = 0;
		for(String s : name_tokens){
			if(!s.equals(temp_tokens[index])){
				return false;
			}
			index++;
		}
		return true;
	}
	
	
	
}
