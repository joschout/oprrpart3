package roboRallyPackage.gameElementClasses;

import be.kuleuven.cs.som.annotate.*;

/**
 * @invar	...
 * 			| isValidEnergyAmount(this.getAmount())
 * @invar	...
 * 			| isValidEnergyUnit(this.getUnit())
 * 
 * 
 * @author Jonas
 *
 */
@Value
public class EnergyAmount implements Comparable<EnergyAmount> {

	/*
	 * 
	 */
	@Raw
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
	public int compareTo(EnergyAmount otherAmount) {
		assert otherAmount != null;
		assert isValidEnergyAmount(otherAmount.getAmount());
		assert isValidEnergyUnit(otherAmount.getUnit());
		if(this.getAmountInWattSecond() < otherAmount.getAmountInWattSecond())
			return -1;
		if(this.getAmountInWattSecond() == otherAmount.getAmountInWattSecond())
			return 0;
		if(this.getAmountInWattSecond() > otherAmount.getAmountInWattSecond())
			return 1;
		assert false;
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
	@Override
	public boolean equals(Object other){
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		EnergyAmount otherAmount = (EnergyAmount) other;
		return (this.getAmountInWattSecond() == otherAmount.getAmountInWattSecond() 
				&& this.getUnit().equals(otherAmount.getUnit()));
	}
	
	@Override
	public int hashCode(){
		return getAmountInWattSecond().hashCode()+getUnit().hashCode();
	}
	
	@Override
	public String toString(){
		return ""+getAmount() + getUnit();
	}
	
}
