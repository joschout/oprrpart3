package roboRallyPackage.gameElementClasses;

import be.kuleuven.cs.som.annotate.*;



/**
 * @version 1.0
 * @author Jonas
 *
 */
@Value
public enum EnergyUnit {

	WATTSECOND("W"), JOULE("J"), KILOJOULE("kJ");
	
	/**
	 * Initialize this energy-unit with the given symbol.
	 * @param symbol
	 */
	@Raw
	private EnergyUnit(String symbol){
		this.symbol=symbol;
	}
	
	
	
	
	/**
	 * Return the symbol for this energy-unit
	 */
	@Basic @Raw @Immutable
	public String getSymbol(){
		return this.symbol;
	}
	
	private final String symbol;
	
	
	/**
	 * 
	 */
	public Double toEnergyUnit(EnergyUnit other) throws IllegalArgumentException{
		if (other == null)
			throw new IllegalArgumentException("Non effective energy unit!");
		if(conversionRates[this.ordinal()][other.ordinal()] == null){
			conversionRates[this.ordinal()][other.ordinal()] = (double)(1/conversionRates[other.ordinal()][this.ordinal()]);
		}
		return conversionRates[this.ordinal()][other.ordinal()];
	}
	
	private static Double[][] conversionRates = new Double [3][3];
	
	static{
		conversionRates[WATTSECOND.ordinal()][WATTSECOND.ordinal()] = (double)1;
		conversionRates[WATTSECOND.ordinal()][JOULE.ordinal()] = (double)1;
		conversionRates[WATTSECOND.ordinal()][KILOJOULE.ordinal()] = (double)0.001;
		
		conversionRates[JOULE.ordinal()][WATTSECOND.ordinal()] = (double)1;
		conversionRates[JOULE.ordinal()][JOULE.ordinal()] = (double)1;		
		conversionRates[JOULE.ordinal()][KILOJOULE.ordinal()] = (double)0.001;				
		
		
		conversionRates[KILOJOULE.ordinal()][WATTSECOND.ordinal()] = (double) 1000;
		
	}
	
	
	
	
	
}
