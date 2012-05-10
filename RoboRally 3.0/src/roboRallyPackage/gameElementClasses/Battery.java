
package roboRallyPackage.gameElementClasses;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of batteries. Extends Item and implements IEnergyHolder
 * 
 * @invar	The current energy level of each battery must be a valid energy level.
 * 			| this.canHaveAsEnergy(this.getCurrentEnergy())
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Battery extends Item implements IEnergyHolder
{
	/**
	 * Initialize this new battery with given the given amount of energy and the given weight.
	 * 
	 * @param 	energyAmount
	 * 			The amount of energy for this new battery in watt-seconds (Ws).
	 * @param 	weight
	 * 			The weight for this new battery in grams.
	 * @pre		The given initial amount of energy must be a valid amount of energy.
	 * 			| this.canHaveAsEnergy(energyAmount)
	 * @effect	...
	 * 			| this.setEnergy(energyAmount)
	 *
	 */
	public Battery(double energyAmount, int weight)
	{
		super(weight);
		this.setEnergy(energyAmount);
	}
	
	/**
	 * Initialize this new battery with a weight of 0 kg and an amount of energy of 0 watt-seconds.
	 */
	public Battery()
	{
		this(0,0);
	}

	/**
	 * Returns the variable representing the current amount if energy of this battery in the given energy unit.
	 */
	@Basic @Override
	public double getEnergy(EnergyUnit unit)
	{
		return energyAmount.toEnergyUnit(unit).getAmount();
	}

	
	/**
	 * Sets the amount of energy of this battery to the given amount of energy in Watt-second [Ws].
	 */
	@Override
	public void setEnergy(double energyAmount) throws IllegalStateException
	{
		assert this.canHaveAsEnergy(energyAmount): "The given amount of energy is not a valid amount of energy for this battery.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery cannot be altered.");
		}
		
		this.energyAmount = new EnergyAmount(energyAmount, EnergyUnit.WATTSECOND);
	}
	
	/**
	 * Checks whether the given amount of energy (expressed in watt-seconds) is a valid amount of energy for this battery.
	 * 
	 * @param	energy
	 * 			The amount of energy to be checked in watt-seconds.
	 */
	@Override
	public boolean canHaveAsEnergy(double energy)
	{
		return (EnergyAmount.isValidEnergyAmount(energy) && (energy <= this.getMaxEnergy()));
	}

	/**
	 * Variable representing the current amount if energy for this battery, expressed in watt-seconds (Ws).
	 */
	private EnergyAmount energyAmount = EnergyAmount.WATTSECOND_0;

	/**
	 * Returns the variable representing the maximum amount of energy this battery can have, expressed in watt-seconds (Ws).
	 */
	@Basic @Immutable @Override
	public double getMaxEnergy()
	{
		return maxBatteryEnergyAmount.getAmountInWattSecond();
	}

	/**
	 * Sets the maximum energy level of this battery to the given maximum energy level, expressed in watt-seconds (Ws).
	 * 
 	 * @param 	maxEnergyAmount
	 * 			The new maximum energy level for this battery in watt-seconds (Ws).
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#setMaxEnergy(double)
	 * @throws	UnsupportedOperationException
	 * 			Always throw this exception when invoked on a battery
	 * 			| this instance of Battery
	 */
	public void setMaxEnergy(double maxEnergyAmount) throws IllegalStateException,
															UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Check whether the given maximum energy is a valid maximum energy for this battery.
	 * 
	 * @return	False if the given energy is greater 5000 Ws.
	 * 			| result != (energy > 5000)
	 */
	@Override
	public boolean canHaveAsMaxEnergy(double maxEnergy)
	{
		return (EnergyAmount.isValidEnergyAmount(maxEnergy) && maxEnergy <= 5000);
	}
	
	/**
	 * Variable representing the maximum amount of energy this battery can have, expressed in watt-seconds (Ws).
	 */
	private final EnergyAmount maxBatteryEnergyAmount = EnergyAmount.WATTSECOND_5000;

	/**
	 * Returns a percentage of the current energy level relative to the maximum amount of energy this battery can have.
	 */
	@Override
	public double getEnergyFraction()
	{
		return (this.getEnergy(EnergyUnit.WATTSECOND) / this.getMaxEnergy()) * 100;
	}

	/**
	 * Recharges this battery with the given amount of energy, expressed in Watt-second.
	 */
	@Override
	public void recharge(double amount) throws IllegalStateException
	{
		assert this.canAcceptForRecharge(amount): "Pecondition for recharge(double) must be statisfied.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery can not be altered.");
		}
		this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) + amount);
	}

	/**
	 * Checks whether the given amount of energy, expressed in watt-second, can be added to the current energy level of this battery.
	 */
	public boolean canAcceptForRecharge(double amount)
	{
		return (EnergyAmount.isValidEnergyAmount(amount) 
				&& this.canHaveAsEnergy(this.getEnergy(EnergyUnit.WATTSECOND) + amount));
	}

	/**
	 * Energy is transferred from this battery to another IEnergyHolder.
	 */
	@Override
	public void transferEnergy(IEnergyHolder other, double amount) throws IllegalStateException
	{
		assert(amount <= this.getEnergy(EnergyUnit.WATTSECOND)): "This battery cannot transfer more energy than it currently has.";
		assert(other.canAcceptForRecharge(amount)): "The other IEnergyHolder cannot accept this amount of energy for reacharging.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery can not be altered.");
		}
		
		other.recharge(amount);
		this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - amount);
	}

	/**
	 * A robot uses this battery. As much energy as possible is transferred from this battery to the robot.
	 * 
	 * @param	robot
	 * 			The robot that uses this battery.
	 * @effect	...
	 * 			| if(robot.canAcceptForRecharge(this.getEnergy())
	 * 			|	then this.transferEnergy(robot, this.getEnergy())
	 * 			| else this.transferEnergy(robot, robot.getMaxEnergy() - robot.getEnergy())
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated() || robot.isTerminates()
	 */
	@Override
	public void use(Robot robot) throws IllegalStateException
	{
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery can not be altered.");
		}
		if(robot.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		// the robot has enough 'room' to store the total amount of energy this battery holds.
		if(robot.canAcceptForRecharge(this.getEnergy()))
		{
			this.transferEnergy(robot, this.getEnergy());
			robot.getPossessions().remove(this);
			this.terminate();
		}
		// the robot can only store part of the energy this battery holds.
		else
		{
			this.transferEnergy(robot, robot.getMaxEnergy() - robot.getEnergy());
		}
	}
	
	/**
	 * When an element is hit (e.g. it is shot by a robot) some of its properties are altered.
	 * A battery increases its energy with 500 Ws when hit if possible. Otherwise it is set to the maximum energy level.
	 * 
	 * @post	The energy level of this battery is increaesed with 500 Ws if possible, otherwise it is set to the maximum energy level.
	 * 			| if(this.canHaveAsEnergy(this.getEnergy() + 500))
	 * 			|  then this.setEnergy(this.getEnergy() + 500)
	 * 			| else this.setEnergy(this.getMaxEnergy())
	 * @throws	IllegalStateException
	 * 			When this battery is terminated.
	 * 			| this.isTerminated()
	 */
	@Override
	public void takeHit() throws IllegalStateException
	{
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery kit cannot be hit.");
		}
		
		// this battery has room for 500 Ws extra energy
		if(this.canHaveAsEnergy(this.getEnergy() + 500))
		{
			this.setEnergy(this.getEnergy() + 500);
		}
		// the energy level of this battery is almost at maximum; it cannot store another 500 Ws extra.
		// the energy level of this battery kit is set at its maximum.
		else
		{
			this.setEnergy(this.getMaxEnergy());
		}
	}
	
	/**
	 * Returns a string representation of this battery.
	 * 
	 * @return	...
	 * 			| result == "Battery with:" + "\n"
	 *			| 			+ super.toString() + "\n" 
	 *			| 			+ " energy level [Ws]: " + this.getEnergy() + "(" + getEnergyFraction() + "%)"
	 */
	@Override
	public java.lang.String toString()
	{
		return "Battery with:" + "\n"
				+ super.toString() +  ";  " + "\n"
				+ "Energy level [Ws]: " + this.getEnergy() + " (" + getEnergyFraction() + "%)";
	}

}
