
package roboRallyPackage.gameElementClasses;
import roboRallyPackage.Board;
import roboRallyPackage.Position;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of batteries. Extends Item and implements IEnergyHolder.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Battery extends Item implements IEnergyHolder
{
	/**
	 * Initialize this new battery with given the given board, position, amount of energy [Ws] and weight [g].
	 * 
	 * @param 	position
	 * 			The position for this new item.
	 * @param	board
	 * 			The board on which this new item will be placed.
	 * @param 	energyAmount
	 * 			The amount of energy for this new battery in watt-seconds [Ws].
	 * @param 	weight
	 * 			The weight for this new battery, expressed in grams [g].
	 * @effect	This new battery is initialized as an Item with the given position, board and weight.
	 * 			| super(position, board, weight)
	 * @effect	...
	 * 			| if this.canHaveAsEnergy(energyAmount) then this.setEnergy(energyAmount)
	 *
	 */
	public Battery(Position position, Board board, double energyAmount, int weight)
	{
		super(position, board, weight);
		if(this.canHaveAsEnergy(energyAmount))
		{
			this.setEnergy(energyAmount);
		}
	}
	
	/**
	 * Initialize this new battery with given the given amount of energy and weight.
	 * 
	 * @param 	energyAmount
	 * 			The amount of energy for this new battery in watt-seconds [Ws].
	 * @param 	weight
	 * 			The weight for this new battery in grams [g].
	 * @pre		The given initial amount of energy must be a valid amount of energy.
	 * 			| this.canHaveAsEnergy(energyAmount)
	 * @effect	...
	 * 			| this(null, null, energyAmount, weight)
	 *
	 */
	public Battery(double energyAmount, int weight)
	{
		this(null, null, energyAmount, weight);
	}
	
	/**
	 * Initialize this new battery without a position or a board and with an energy level of 0 Ws and a weight of 0 g.
	 */
	public Battery()
	{
		this(null, null, 0, 0);
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
	 * Checks whether the given amount of energy, expressed in Watt-second [Ws], is a valid amount of energy for this battery.
	 */
	@Override
	public boolean canHaveAsEnergy(double energy)
	{
		return ((energy <= this.getMaxEnergy()) && EnergyAmount.isValidEnergyAmount(energy));
	}

	/**
	 * Variable representing the current amount if energy for this battery, expressed in watt-seconds [Ws].
	 */
	private EnergyAmount energyAmount = EnergyAmount.WATTSECOND_0;

	/**
	 * Returns the variable representing the maximum amount of energy this battery can have, expressed in watt-seconds [Ws].
	 */
	@Basic @Immutable @Override
	public double getMaxEnergy()
	{
		return maxBatteryEnergyAmount.getAmountInWattSecond();
	}

	/**
	 * Sets the maximum energy level of this battery to the given maximum energy level, expressed in watt-seconds [Ws].
	 */
	@Override
	public void setMaxEnergy(double maxEnergyAmount) throws IllegalStateException,
															UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Check whether the given maximum energy, expressed in Watt-second [Ws], is a valid maximum energy for this battery.
	 * 
	 * @return	False if the given energy is greater 5000 Ws.
	 * 			| result != (energy > 5000)
	 */
	@Override
	public boolean canHaveAsMaxEnergy(double maxEnergy)
	{
		return (maxEnergy <= 5000 && EnergyAmount.isValidEnergyAmount(maxEnergy));
	}
	
	/**
	 * Variable representing the maximum amount of energy this battery can have, expressed in watt-seconds [Ws].
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
	 * Recharges this battery with the given amount of energy, expressed in watt-second [Ws].
	 */
	@Override
	public void recharge(double amount) throws IllegalStateException
	{
		assert this.canAcceptForRecharge(amount): "Pecondition for recharge(double) must be statisfied.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery cannot recharge.");
		}
		this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) + amount);
	}

	/**
	 * Checks whether the given amount of energy, expressed in watt-seconds [Ws], can be added to the current energy level of this battery.
	 */
	@Override
	public boolean canAcceptForRecharge(double amount)
	{
		return (this.canHaveAsEnergy(this.getEnergy(EnergyUnit.WATTSECOND) + amount)
				&& EnergyAmount.isValidEnergyAmount(amount));
	}

	/**
	 * Transfers the given amount of energy, expressed in watt-seconds [Ws], from this battery to another IEnergyHolder.
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
	 * The given robot uses this battery. As much energy as possible is transferred from this battery to the robot.
	 * 
	 * @effect	...
	 * 			| if(robot.canAcceptForRecharge(this.getEnergy(EnergyUnit.WATTSECOND))
	 * 			|	then this.transferEnergy(robot, this.getEnergy(EnergyUnit.WATTSECOND))
	 * 			| else this.transferEnergy(robot, robot.getMaxEnergy(EnergyUnit.WATTSECOND) - robot.getEnergy())
	 */
	@Override
	public void use(Robot robot) throws IllegalStateException
	{
		assert robot.getPossessions().contains(this): "The given robot does is not carrying this item.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("Either this battery or the robot is terminated. The robot cannot use the battery.");
		}
		if(robot.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		// the robot has enough 'room' to store the total amount of energy this battery holds.
		if(robot.canAcceptForRecharge(this.getEnergy(EnergyUnit.WATTSECOND)))
		{
			this.transferEnergy(robot, this.getEnergy(EnergyUnit.WATTSECOND));
			robot.getPossessions().remove(this);
			this.terminate();
		}
		// the robot can only store part of the energy this battery holds.
		else
		{
			this.transferEnergy(robot, robot.getMaxEnergy() - robot.getEnergy(EnergyUnit.WATTSECOND));
		}
	}
	
	/**
	 * When an element is hit (e.g. it is shot by a robot) some of its properties are altered.
	 * A battery increases its energy with 500 Ws when hit if possible. Otherwise its energy is set to the maximum energy level.
	 * 
	 * @post	The energy level of this battery is increased with 500 Ws if possible, otherwise it is set to the maximum energy level.
	 * 			| if(this.canHaveAsEnergy(this.getEnergy(EnergyUnit.WATTSECOND) + 500))
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
		if(this.canHaveAsEnergy(this.getEnergy(EnergyUnit.WATTSECOND) + 500))
		{
			this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) + 500);
		}
		// the energy level of this battery is almost at maximum; it cannot store another 500 Ws extra.
		// the energy level of this battery is set at its maximum.
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
	 *			| 			+ " Energy level [Ws]: " + this.getEnergy(EnergyUnit.WATTSECOND) + "(" + getEnergyFraction() + "%)"
	 */
	@Override
	public java.lang.String toString()
	{
		return "Battery with:" + "\n"
				+ super.toString() +  ";  " + "\n"
				+ "Energy level [Ws]: " + this.getEnergy(EnergyUnit.WATTSECOND) + " (" + getEnergyFraction() + "%)";
	}

}
