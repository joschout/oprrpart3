
package roboRallyPackage.gameElementClasses;


/**
 * An interface which classes, whose objects represent gamepieces with an internal energy level, can implement.
 * The methods in this interface concern the energy of these gamepieces in Ws.
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 *
 */
public interface IEnergyHolder
{
	public abstract boolean isTerminated();

	/**
	 * Returns the variable representing the current amount if energy of this IEnergyHolder in the given energy unit.
	 *
	 * @param	unit
	 * 			The energy unit in which the returned energy amount will be expressed.
	 */
	public abstract double getEnergy(EnergyUnit unit);

	/**
	 * Sets the amount of energy of this IEnergyHolder to the given amount of energy, expressed in watt-seconds (Ws).
	 * 
	 * @param 	energyAmount
	 * 			The new energy amount for this IEnergyHolder in watt-seconds (Ws).
	 * @pre		The given amount of energy (in watt-seconds (Ws)) is a valid amount of energy for this IEnergyHolder.
	 * 			| this.canHaveAsEnergy(energyAmount)
	 * @post	The new amount of energy is equal to the given amount of energy.
	 * 			| (new this).getEnergy(EnergyUnit.WATTSECOND) == energyAmount
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated()
	 */
	public abstract void setEnergy(double energyAmount) throws IllegalStateException;

	/**
	 * Checks whether the given amount of energy, expressed in Watt-second, is a valid amount of energy for this IEnergyHolder.
	 * 
	 * @param	energy
	 * 			The amount of energy to be checked, expressed in Watt-second.
	 * @return 	False if the given energy is not a valid energy amount.
	 * 			| result ==EnergyAmount.isValidEnergyAmount(energy)
	 * @return	False if the given energy is greater than or equal to the maximal amount of energy an IEnergyHolder can have.
	 * 			| result != (energy > this.getMaxEnergy())
	 */
	public abstract boolean canHaveAsEnergy(double energy);

	/**
	 * Returns the variable representing the maximum amount of energy this IEnergyHolder can have.
	 */
	public abstract double getMaxEnergy();
	
	/**
	 * Sets the maximum energy level of this IEnergyHolder to the given maximum energy level, expressed in watt-seconds (Ws).
	 * 
 	 * @param 	maxEnergyAmount
	 * 			The new maximum energy level for this IEnergyHolder in watt-seconds (Ws).
	 * @pre		The given maximum energy level (in watt-seconds (Ws)) is a valid maximum energy level for this IEnergyHolder.
	 * 			| this.canHaveAsMaxEnergy(energyAmount)
	 * @post	The new maximum energy level is equal to the given amount of energy.
	 * 			| (new this).getMaxEnergy() == maxEnergyAmount
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated()
	 * @throws	UnsupportedOperationException
	 * 			When the maximum energy level of this IEnergeHolder cannot be altered
	 * 			| ! (this instance of Robot)
	 */
	public abstract void setMaxEnergy(double maxEnergyAmount) throws IllegalStateException, UnsupportedOperationException;
	
	/**
	 * Checks whether the given maximum energy level, expressed in Watt-second [Ws], is a valid maximum energy level for this IEnergyHolder.
	 * 
	 * @param	maxEnergy
	 * 			The maximum energy level to be checked, expressed in Watt-second [Ws].
	 * @return 	False if the given energy isn't a valid energy amount
	 * 			| result == EnergyAmount.isValidEnergyAmount(maxEnergy)
	 */
	public abstract boolean canHaveAsMaxEnergy(double maxEnergy);

	/**
	 * Returns a percentage of the current energy level relative to the maximum amount of energy this IEnergyHolder can have.
	 * 
	 * @return	A percentage of the current energy level relative to the maximum amount of energy this IEnergyHolder can have.
	 * 			| result == (this.getEnergy(EnergyUnit.WATTSECOND)) / this.getMaxEnergy()) * 100
	 */
	public abstract double getEnergyFraction();

	/**
	 * Recharges this IEnergyHolder with the given amount of energy, expressed in watt-seconds (Ws).
	 * 
	 * @param	amount
	 * 			The amount of energy to be added to this IEnergyHolder, expressed in watt-seconds (Ws).
	 * @pre		This IEnergyHolder can be recharged with the given amount of energy.
	 * 			| this.canAcceptForRecharge(amount)
	 * @effect	The given amount of energy is added to the current amount of energy(expressed in Watt-second) this IEnergyHolder has.
	 * 			| this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) + amount)
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated()
	 */
	public abstract void recharge(double amount) throws IllegalStateException;

	/**
	 * Checks whether the given amount of energy (expressed in watt-seconds (Ws)) can be added to the current energy level of this IEnergyHolder.
	 * 
	 * @param 	amount
	 * 			The amount to be checked, expressed in watt-seconds (Ws).
	 * @return	False  if the given amount of energy is a not valid energy amount
	 * 			| result == EnergyAmount.isValidEnergyAmount(amount)
	 *@return	False if this IEnergyHolder cannot have a total energy level equal to  the given amount of energy added to its current energy level.
	 *			| result == (this.canHaveAsEnergy(this.getEnergy(EnergyUnit.WATTSECOND) + amount))
	 */
	public abstract boolean canAcceptForRecharge(double amount);

	/**
	 * Energy is transferred from this IEnergyHolder to another IEnergyHolder.
	 * 
	 * @param	other
	 * 			The IEnergyHolder to be given energy.
	 * @param	amount
	 * 			The amount of energy to be transferred, expressed in Watt-second [Ws].
	 * @pre		The IEnergyHolder must be able to accept the amount of energy to be transferred.
	 * 			| other.canAcceptForRecharge(amount)
	 * @pre		The amount to be transferred [Ws] can not be greater than the amount of energy this IEnergyHolder has [Ws].
	 * 			| amount <= this.getEnergy(EnergyUnit.WATTSECOND)
	 * @effect	The IEnergyHolder is recharged with amount of energy to be transferred.
	 * 			| other.recharge(amount)
	 * @effect	The transferred amount of energy [Ws] is subtracted from the amount of energy of this IEnergyHolder.
	 * 			| this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - amount);
	 * @throws	IllegalStateException
	 * 			When this IEnergyHolder or the other IEnergyHolder is terminated
	 * 			| this.isTerminated() || other.isTerminated()
	 * @throws	UnsupportedOperationException
	 * 			When this IEnergyHolder cannot transfer energy to another IEnergyHolder
	 * 			| ! this instanceof Battery
	 */
	public abstract void transferEnergy(IEnergyHolder other, double amount) throws IllegalStateException, UnsupportedOperationException;
}
