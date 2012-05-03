/**
 * 
 */
package roboRallyPackage.gameElementClasses;
import be.kuleuven.cs.som.annotate.*;

/**
 * @author Nele
 *
 */
public class RepairKit extends Item implements IEnergyHolder
{
	/**
	 * Initialize this new repair kit with the given amount of energy and the given weight.
	 * 
	 * @param 	energyAmount
	 * 			The amount of energy for this new repair kit in watt-seconds (Ws).
	 * @param 	weight
	 * 			The weight for this new repair kit in grams.
	 * @pre		The given initial amount of energy must be a valid amount of energy.
	 * 			| this.canHaveAsEnergy(energyAmount)
	 * @effect	...
	 * 			| this.setEnergy(energyAmount)
	 */
	public RepairKit(double energyAmount, int weight)
	{
		super(weight);
		this.setEnergy(energyAmount);
	}
	
	/**
	 * Returns the variable representing the current amount if energy of this battery.
	 */
	@Basic
	public double getEnergy()
	{
		return energyAmount;
	}

	/**
	 * Sets the amount of energy of this repair kit to the given amount of energy.
	 * 
	 * @param 	energyAmount
	 * 			The new energy amount for this battery.
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#setEnergy(double)
	 */
	public void setEnergy(double energyAmount) throws IllegalStateException
	{
		assert this.canHaveAsEnergy(energyAmount): "The given amount of energy is not a valid amount of energy for a repair kit.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated repair kit cannot be altered.");
		}
		
		this.energyAmount = energyAmount;
		
	}
	
	/**
	 * Checks whether the given amount of energy (expressed in watt-seconds) is a valid amount of energy for this repair kit.
	 * 
	 * @param	energy
	 * 			The amount of energy to be checked in watt-seconds.
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#canHaveAsEnergy(double)
	 */
	public boolean canHaveAsEnergy(double energy)
	{
		return ((energy >= 0) && (energy <= this.getMaxEnergy()));
	}

	/**
	 * Variable representing the current amount if energy for this repair kit, expressed in watt-seconds (Ws).
	 */
	private double energyAmount = 0;
	

	/**
	 * Returns the variable representing the maximum amount of energy this repair kit can have, expressed in watt-seconds (Ws).
	 */
	@Basic @Immutable
	public final double getMaxEnergy()
	{
		return maxEnergyAmount;
	}

	/**
	 * Sets the maximum energy level of this repair kit to the given maximum energy level, expressed in watt-seconds (Ws).
	 * 
 	 * @param 	maxEnergyAmount
	 * 			The new maximum energy level for this repair kit in watt-seconds (Ws).
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#setMaxEnergy(double)
	 * @throws	UnsupportedOperationException
	 * 			Always throw this exception when invoked on a repair kit
	 * 			| this instance of RepairKit
	 */
	public void setMaxEnergy(double maxEnergyAmount) throws IllegalStateException,
															UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Check whether the given maximum energy is a valid maximum energy for this repair kit.
	 * 
	 * @param 	maxEnergy
	 * 			The maximum energy level to be checked.
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#canHaveAsMaxEnergy(double)
	 */
	public boolean canHaveAsMaxEnergy(double maxEnergy)
	{
		return (maxEnergy >= 0 && maxEnergy <= Double.MAX_VALUE);
	}

	/**
	 * Variable representing the maximum amount of energy this repair kit can have, expressed in watt-seconds (Ws).
	 */
	private static final double maxEnergyAmount = Integer.MAX_VALUE;

	/**
	 * Returns a percentage of the current energy level relative to the maximum amount of energy this repair kit can have.
	 * 
	 * @return	A percentage of the current energy level relative to the maximum amount of energy this repair kit can have.
	 * 			| result == (this.getEnergy() / this.getMaxEnergy()) * 100
	 */
	public double getEnergyFraction()
	{
		return (this.getEnergy() / this.getMaxEnergy()) * 100;
	}

	/**
	 * Recharges this repair kit with the given amount of energy.
	 * 
	 * @param	amount
	 * 			The amount of energy to be added to this repair kit.
	 * @pre		This repair kit can be recharged with the given amount of energy.
	 * 			| this.canAcceptForRecharge(amount)
	 * @effect	The given amount of energy is added to the current amount of energy this repair kit has.
	 * 			| this.setEnergy(this.getEnergy() + amount)
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated()
	 */
	public void recharge(double amount) throws IllegalStateException
	{
		assert this.canAcceptForRecharge(amount): "The given amount is not a valid amount for this repair kit to be recharged with.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery can not be altered.");
		}
		this.setEnergy(this.getEnergy() + amount);
	}

	/**
	 * Checks whether the given amount of energy can be added to the current energy level of this repair kit.
	 * 
	 * @param 	amount
	 * 			The amount to be checked.
	 * @return	True if and only if the given amount of energy is strictly positive.
	 * 			| result == (amount > 0)
	 * @return	True if this repair kit can have a total energy level equal to its the given amount of energy added to its current energy level.
	 * 			| result == this.canHaveAsEnergy(this.getEnergy() + amount))
	 */
	public boolean canAcceptForRecharge(double amount)
	{
		return ((amount > 0) && this.canHaveAsEnergy(this.getEnergy() + amount));
	}

	/**
	 * This repair kit repairs another IEnergyHolder bay increasing its maximum energy.
	 * When the other IEnergyHolder cannot alter its maximum position, nothing is changed.
	 * 
	 * @param	other
	 * 			The IEnergyHolder to be given energy.
	 * @param	amount
	 * 			The amount of energy to be transferred.
	 * @pre		The amount to be transferred can not be greater than the amount of energy this repair kit has.
	 * 			| amount <= this.getEnergy()
	 * @pre		The IEnergyHolder must be able to alter its maximum energy level with half of the given amount.
	 * 			| other.canHaveAsMaxEnergy(other.getMaxEnergy() + (amount/2))
	 * @effect	The maximum energy level of the other IEnergyHolder is increased with half of the given amount.
	 * 			| other.setMaxEnergy(other.getMaxEnergy() + (amount/2))
	 * @effect	The transferred amount of energy is subtracted from the amount of energy of this repair kit.
	 * 			| this.setEnergy(this.getEnergy() - amount)
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated() || other.isTerminated()
	 */
	public void repair(IEnergyHolder other, double amount) throws IllegalStateException
	{
		assert(amount <= this.getEnergy()):"This repair kit cannot transfer more energy than it has.";
		assert(other.canHaveAsMaxEnergy(other.getMaxEnergy() + (amount/2))):"The other IEnergyHolder cannot increase its maximum energy level the energy to be transferred.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated repair kit can not be altered.");
		}
		if(other.isTerminated())
		{
			throw new IllegalStateException("A terminated IEnergyHolder can not be altered.");
		}
		
		try
		{
			other.setMaxEnergy(other.getMaxEnergy() + (amount)/2);
			this.setEnergy(this.getEnergy() - amount);
		}
		// the other IEnergyHolder cannot alter its maximum energy
		catch(UnsupportedOperationException exc)
		{
			System.err.println("The maximum energy level of the given IEnergyHolder cannot be altered.");
		}
	}
	
	/**
	 * Energy is transferred from this robot to another IEnergyHolder.
	 * 
	 * @param	other
	 * 			The IEnergyHolder to be given energy.
	 * @param	amount
	 * 			The amount of energy to be transferred.
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#transferEnergy(double)
	 * @throws	UnsupportedOperationException
	 * 			Always throw this exception when invoked on a repair kit.
	 * 			| this instance of RepairKit
	 */
	public void transferEnergy(IEnergyHolder other, double amount) throws IllegalStateException, UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	
	/**
	 * A robot uses this repair kit. As much energy as possible is transferred from this repair kit to the maximum energy level of the robot.
	 * 
	 * @param	robot
	 * 			The robot that uses this repair kit.
	 * @effect	...
	 * 			| if(robot.canHaveAsMaxEnergy(robot.getMaxEnergy() + (this.getEnergy()/2)))
	 * 			|	then this.repair(robot, this.getEnergy())
	 * 			| else this.repair(robot, robot.getMaxEnergy() - robot.getEnergy())
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated() || robot.isTerminates()
	 */
	public void use(Robot robot)
	{
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery can not be altered.");
		}
		if(robot.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		
		// the robot has enough 'room' to store the total maximum energy level this repair kit holds.
		if(robot.canHaveAsMaxEnergy(robot.getMaxEnergy() + (this.getEnergy()/2)))
		{
			this.repair(robot, this.getEnergy());
			robot.getPossessions().remove(this);
			this.terminate();
		}
		
		// the robot can only store part of the energy this repair kit holds.
		else
		{
			this.repair(robot, robot.getMaxEnergy() - robot.getEnergy());
		}
	}
}
