package components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Attribute {

	public static HashMap<String, String> ageBandMap = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("0-18", "zero_eigtheen");
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
	
	public static HashMap<String, String> ageReverseBandMap = (HashMap<String, String>) invert(ageBandMap);
	
	public static <V, K> Map<V, K> invert(HashMap<K, V> map) {
	    return map.entrySet()
	              .stream()
	              .collect(Collectors.toMap(Entry::getValue, c -> c.getKey()));
	}

	public enum AttributeType {
		CARRIER_ID, CARRIER_PLAN_ID, START_DATE, END_DATE, PRODUCT_NAME, PLAN_PDF_FILE_NAME, DEDUCTIBLE_INDIV, 
		DEDUCTIBLE_FAMILY, OON_DEDUCTIBLE_INDIVIDUAL, OON_DEDUCTIBLE_FAMILY, COINSURANCE, DR_VISIT_COPAY, SPECIALIST_VISITS_COPAY, 
		ER_COPAY, URGENT_CARE_COPAY, RX_COPAY, RX_MAIL_COPAY, OOP_MAX_INDIV, OOP_MAX_FAMILY, OON_OOP_MAX_INDIVIDUAL, OON_OOP_MAX_FAMILY, 
		IN_PATIENT_HOSPITAL, OUTPATIENT_DIAGNOSTIC_LAB, OUTPATIENT_SURGERY, OUTPATIENT_DIAGNOSTIC_X_RAY, OUTPATIENT_COMPLEX_IMAGING, 
		PHYSICAL_OCCUPATIONAL_THERAPY, STATE, GROUP_RATING_AREAS, SERVICE_ZONES, ZERO_EIGHTEEN, NINETEEN_TWENTY, TWENTY_ONE, TWENTY_TWO, 
		TWENTY_THREE, TWENTY_FOUR, TWENTY_FIVE, TWENTY_SIX, TWENTY_SEVEN, TWENTY_EIGHT, TWENTY_NINE, THIRTY, THIRTY_ONE, THIRTY_TWO, 
		THIRTY_THREE, THIRTY_FOUR, THIRTY_FIVE, THIRTY_SIX, THIRTY_SEVEN, THIRTY_EIGHT, THIRTY_NINE, FORTY, FORTY_ONE, FORTY_TWO, 
		FORTY_THREE, FORTY_FOUR, FORTY_FIVE, FORTY_SIX, FORTY_SEVEN, FORTY_EIGHT, FORTY_NINE, FIFTY, FIFTY_ONE, FIFTY_TWO, FIFTY_THREE,
		FIFTY_FOUR, FIFTY_FIVE, FIFTY_SIX, FIFTY_SEVEN, FIFTY_EIGHT, FIFTY_NINE, SIXTY, SIXTY_ONE, SIXTY_TWO, SIXTY_THREE, SIXTY_FOUR, SIXTY_FIVE_PLUS, NONE
	};
	
	public static ArrayList<AttributeType> attributes = new ArrayList<AttributeType>(Arrays.asList(AttributeType.values()));

	public Attribute() {
	}
	
	public static String getFullAge(String numeral){
		return ageBandMap.get(numeral);
	}
	


}
