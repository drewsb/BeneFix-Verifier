package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

// TODO: Auto-generated Javadoc
/**
 * The Class Attribute.
 */
public class Attribute {

	/** The age band map. */
	public static HashMap<String, String> ageBandMap = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("0-18", "zero_eighteen");
			put("19-20", "nineteen_twenty");
			put("21", "twenty_one");
			put("22", "twenty_two");
			put("23", "twenty_three");
			put("24", "twenty_four");
			put("25", "twenty_five");
			put("26", "twenty_six");
			put("27", "twenty_seven");
			put("28", "twenty_eight");
			put("29", "twenty_nine");
			put("30", "thirty");
			put("31", "thirty_one");
			put("32", "thirty_two");
			put("33", "thirty_three");
			put("34", "thirty_four");
			put("35", "thirty_five");
			put("36", "thirty_six");
			put("37", "thirty_seven");
			put("38", "thirty_eight");
			put("39", "thirty_nine");
			put("40", "forty");
			put("41", "forty_one");
			put("42", "forty_two");
			put("43", "forty_three");
			put("44", "forty_four");
			put("45", "forty_five");
			put("46", "forty_six");
			put("47", "forty_seven");
			put("48", "forty_eight");
			put("49", "forty_nine");
			put("50", "fifty");
			put("51", "fifty_one");
			put("52", "fifty_two");
			put("53", "fifty_three");
			put("54", "fifty_four");
			put("55", "fifty_five");
			put("56", "fifty_six");
			put("57", "fifty_seven");
			put("58", "fifty_eight");
			put("59", "fifty_nine");
			put("60", "sixty");
			put("61", "sixty_one");
			put("62", "sixty_two");
			put("63", "sixty_three");
			put("64", "sixty_four");
			put("65+", "sixty_five_plus");

		}
	};

	/** The age reverse band map. */
	public static HashMap<String, String> ageReverseBandMap = (HashMap<String, String>) invert(ageBandMap);

	/**
	 * Invert.
	 *
	 * @param <V> the value type
	 * @param <K> the key type
	 * @param map the map
	 * @return the map
	 */
	public static <V, K> Map<V, K> invert(HashMap<K, V> map) {
		return map.entrySet().stream().collect(Collectors.toMap(Entry::getValue, c -> c.getKey()));
	}

	/**
	 * The Enum AttributeType.
	 */
	public enum AttributeType {
		
		/** The carrier. */
		CARRIER, 
 /** The carrier id. */
 CARRIER_ID, 
 /** The carrier plan id. */
 CARRIER_PLAN_ID, 
 /** The start date. */
 START_DATE, 
 /** The end date. */
 END_DATE, 
 /** The product name. */
 PRODUCT_NAME, 
 /** The plan pdf file name. */
 PLAN_PDF_FILE_NAME, 
 /** The state. */
 STATE, 
 /** The group rating areas. */
 GROUP_RATING_AREAS, 
 /** The service zones. */
 SERVICE_ZONES, 
 /** The deductible indiv. */
 DEDUCTIBLE_INDIV, 
 /** The deductible family. */
 DEDUCTIBLE_FAMILY, 
 /** The oon deductible individual. */
 OON_DEDUCTIBLE_INDIVIDUAL, 
 /** The oon deductible family. */
 OON_DEDUCTIBLE_FAMILY, 
 /** The coinsurance. */
 COINSURANCE, 
 /** The dr visit copay. */
 DR_VISIT_COPAY, 
 /** The specialist visits copay. */
 SPECIALIST_VISITS_COPAY, 
 /** The er copay. */
 ER_COPAY, 
 /** The urgent care copay. */
 URGENT_CARE_COPAY, 
 /** The rx copay. */
 RX_COPAY, 
 /** The rx mail copay. */
 RX_MAIL_COPAY, 
 /** The oop max indiv. */
 OOP_MAX_INDIV, 
 /** The oop max family. */
 OOP_MAX_FAMILY, 
 /** The oon oop max individual. */
 OON_OOP_MAX_INDIVIDUAL, 
 /** The oon oop max family. */
 OON_OOP_MAX_FAMILY, 
 /** The in patient hospital. */
 IN_PATIENT_HOSPITAL, 
 /** The outpatient diagnostic lab. */
 OUTPATIENT_DIAGNOSTIC_LAB, 
 /** The outpatient surgery. */
 OUTPATIENT_SURGERY, 
 /** The outpatient diagnostic x ray. */
 OUTPATIENT_DIAGNOSTIC_X_RAY, 
 /** The outpatient complex imaging. */
 OUTPATIENT_COMPLEX_IMAGING, 
 /** The physical occupational therapy. */
 PHYSICAL_OCCUPATIONAL_THERAPY, 
 /** The plan pdf file url. */
 PLAN_PDF_FILE_URL, 
 /** The zero eighteen. */
 ZERO_EIGHTEEN, 
 /** The nineteen twenty. */
 NINETEEN_TWENTY, 
 /** The twenty one. */
 TWENTY_ONE, 
 /** The twenty two. */
 TWENTY_TWO, 
 /** The twenty three. */
 TWENTY_THREE, 
 /** The twenty four. */
 TWENTY_FOUR, 
 /** The twenty five. */
 TWENTY_FIVE, 
 /** The twenty six. */
 TWENTY_SIX, 
 /** The twenty seven. */
 TWENTY_SEVEN, 
 /** The twenty eight. */
 TWENTY_EIGHT, 
 /** The twenty nine. */
 TWENTY_NINE, 
 /** The thirty. */
 THIRTY, 
 /** The thirty one. */
 THIRTY_ONE, 
 /** The thirty two. */
 THIRTY_TWO, 
 /** The thirty three. */
 THIRTY_THREE, 
 /** The thirty four. */
 THIRTY_FOUR, 
 /** The thirty five. */
 THIRTY_FIVE, 
 /** The thirty six. */
 THIRTY_SIX, 
 /** The thirty seven. */
 THIRTY_SEVEN, 
 /** The thirty eight. */
 THIRTY_EIGHT, 
 /** The thirty nine. */
 THIRTY_NINE, 
 /** The forty. */
 FORTY, 
 /** The forty one. */
 FORTY_ONE, 
 /** The forty two. */
 FORTY_TWO, 
 /** The forty three. */
 FORTY_THREE, 
 /** The forty four. */
 FORTY_FOUR, 
 /** The forty five. */
 FORTY_FIVE, 
 /** The forty six. */
 FORTY_SIX, 
 /** The forty seven. */
 FORTY_SEVEN, 
 /** The forty eight. */
 FORTY_EIGHT, 
 /** The forty nine. */
 FORTY_NINE, 
 /** The fifty. */
 FIFTY, 
 /** The fifty one. */
 FIFTY_ONE, 
 /** The fifty two. */
 FIFTY_TWO, 
 /** The fifty three. */
 FIFTY_THREE, 
 /** The fifty four. */
 FIFTY_FOUR, 
 /** The fifty five. */
 FIFTY_FIVE, 
 /** The fifty six. */
 FIFTY_SIX, 
 /** The fifty seven. */
 FIFTY_SEVEN, 
 /** The fifty eight. */
 FIFTY_EIGHT, 
 /** The fifty nine. */
 FIFTY_NINE, 
 /** The sixty. */
 SIXTY, 
 /** The sixty one. */
 SIXTY_ONE, 
 /** The sixty two. */
 SIXTY_TWO, 
 /** The sixty three. */
 SIXTY_THREE, 
 /** The sixty four. */
 SIXTY_FOUR, 
 /** The sixty five plus. */
 SIXTY_FIVE_PLUS, 
 /** The none. */
 NONE
	};

	/** The attributes. */
	public static ArrayList<AttributeType> attributes = new ArrayList<AttributeType>(
			Arrays.asList(AttributeType.values()));

	/**
	 * Gets the full age.
	 *
	 * @param numeral the numeral
	 * @return the full age
	 */
	public static String getFullAge(String numeral) {
		return ageBandMap.get(numeral);
	}

}
