package roboRallyPackage.gameElementClasses;

import be.kuleuven.cs.som.annotate.*;

/**
 * 
 * @author Jonas
 *
 */
@Value
public class EnergyAmount implements Comparable<EnergyAmount> {

	/*
	 * 
	 */
	public EnergyAmount(Double amount, EnergyUnit unit){
		assert isValidEnergyAmount(amount);
		assert isValidEnergyUnit(unit);
		this.amount = amount;
		this.unit = unit;
	}
	
	public final static EnergyAmount WATTSECOND_0 =  new EnergyAmount((double)0, EnergyUnit.WATTSECOND);
	
	public final static EnergyAmount WATTSECOND_5000 =  new EnergyAmount((double)5000, EnergyUnit.WATTSECOND);
	
	public final static EnergyAmount WATTSECOND_DOUBLE_MAXVALUE =  new EnergyAmount(Double.MAX_VALUE, EnergyUnit.WATTSECOND);
	
	//public final static EnergyAmount WATTSECOND_ROBOT_MAXIMUM_ENERGY_LIMIT =  new EnergyAmount((double) 20000, EnergyUnit.WATTSECOND);
	
	/*
	 * 
	 */
	@Override
	public int compareTo(EnergyAmount arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * 
	 */
	private final EnergyUnit unit;
	
	public EnergyUnit getUnit() {
		return unit;
	}

	/*
	 * 
	 */
	public static Boolean isValidEnergyUnit(EnergyUnit unit){
		return unit != null;
	}
	
	
	
	
	
	
	/*
	 * 
	 */
	private final Double amount;

	
	
	/**
	 * 
	 * @return
	 */
	public static Boolean isValidEnergyAmount(Double amount){
		return (amount != null)&&(amount<=Double.MAX_VALUE)&&(amount >=0);
	}
	
	/*
	 * 
	 */
	@Basic
	public Double getAmount(){
		return amount;
	}
	
	public  Double getAmountInWattSecond(){
		if(getUnit() == EnergyUnit.WATTSECOND)
			return this.getAmount();
		return this.toEnergyUnit(EnergyUnit.WATTSECOND).getAmount();
	}
	
	public EnergyAmount toEnergyUnit(EnergyUnit unit){
		assert isValidEnergyUnit(unit);
		if(this.getUnit() == unit)
			return this;
		Double conversionRate = this.getUnit().toEnergyUnit(unit);
		Double newAmount = conversionRate*this.getAmount();
		assert isValidEnergyAmount(newAmount);
		return new EnergyAmount(newAmount, unit);	
	}
	
	
}
